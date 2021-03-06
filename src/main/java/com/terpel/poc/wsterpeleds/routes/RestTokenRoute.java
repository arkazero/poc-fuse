package com.terpel.poc.wsterpeleds.routes;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.http.conn.HttpHostConnectException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.terpel.poc.wsterpeleds.model.EDS;
import com.terpel.poc.wsterpeleds.model.Request;
import com.terpel.poc.wsterpeleds.model.Response;
import com.terpel.poc.wsterpeleds.model.Station;	
import com.terpel.poc.wsterpeleds.model.Token;
import com.terpel.poc.wsterpeleds.transformations.BuildResponse;

@Component
public class RestTokenRoute extends RouteBuilder{
	
	private static final String AUTH_ERROR_MSG = "Se ha producido un error en la autenticación";
	private Logger log = LoggerFactory.getLogger(RestTokenRoute.class);
	
	@Autowired
	private Environment env;

	@Override
	public void configure()  throws Exception {
		
//		super.configure();
		JacksonDataFormat format = new JacksonDataFormat(Token.class);
		JacksonDataFormat format_station = new JacksonDataFormat(EDS.class);
		JacksonDataFormat response = new JacksonDataFormat(Response.class);
		
		format_station.useList();
		
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'-05:00'");//dd/MM/yyyy
		Date now = new Date();
		String strDate = sdfDate.format(now);
		
		String user = env.getProperty("auth_user");
		String pass = env.getProperty("auth_pass");
		String token_auth = env.getProperty("token_auth");
		
		final String TARGET_WITH_AUTH = "https://f5.iot.devitech.com.co:8010/gopass/autenticacion";
		
		onException(HttpHostConnectException.class)
			.handled(true)
			.log(LoggingLevel.ERROR, log, "Ha ocurrido una excepcion HttpHostConnectException: " + exceptionMessage())
	        .setHeader("CamelHttpResponseCode", constant("400"))
	        .setHeader("codigoRespuesta", constant("FAILED"))
	        .setHeader("mensajeRespuesta",exceptionMessage())
	        .setBody(simple("${headers.CamelHttpResponseCode} , ${headers.codigoRespuesta} , ${headers.mensajeRespuesta}"))
	        //.bean(BuildResponse.class)
	        //.process("buildResponse")
	        .marshal(response)
		.end();
		
		from("direct:definirTokenProducer").routeId("wsterpeleds_rest_token")
			.log("Datos de autenticacion: "+ user)
			.log("Ingresando al token: ${body}")
			.setHeader("cuerpoMensaje", simple("${body}"))
			.setHeader("aplicacion", constant("OPEN_SF"))
			.setHeader("identificadorDispositivo", constant("127.0.0.1"))	
			.setHeader("fecha",simple(strDate))
			.setHeader("Authorization",simple(token_auth))
			.setHeader("authUsername",simple(user))
			.setHeader("authPassword",simple(pass))
			.setHeader(Exchange.HTTP_METHOD, constant("GET"))
			.setHeader(Exchange.HTTP_URI, simple(TARGET_WITH_AUTH))
			.setHeader(Exchange.CONTENT_TYPE, constant("application/json"))			
			.log("Peticion Token ${headers.HTTP_URI}")
			.setBody(constant(""))
			.log("Token ${body}")
			.to("https4://ConsumoToken")	
			.convertBodyTo(String.class)
			.log("Respuesta Token ${body}")
			.unmarshal(format)	
			.to("bean-validator://x")
			
			.setHeader("token",simple("${body.data.autorizacion}"))
			.log("Respuesta Token ${body.data.autorizacion}")
			.setBody(simple("${headers.cuerpoMensaje}"))				
			.log("Respuesta Token body original ${body}")
			//.convertBodyTo(String.class)
			
			.unmarshal(format_station)		
			.to("bean-validator://x")		
//			.log("Cuerpo RESTTOKEN: ${body}")
//			.setHeader("CamelVelocityResourceUri",simple("{{TEMPLATE_AUTORIZACION}}"))			
			//.to("velocity:dummy?loaderCache=false")
			.to("velocity:vm/autorizacion.vm")
			.log("Autorizacion ${body}")
		.end();
		
	}

}
