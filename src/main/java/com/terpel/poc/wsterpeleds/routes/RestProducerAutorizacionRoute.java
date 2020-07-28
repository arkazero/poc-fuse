package com.terpel.poc.wsterpeleds.routes;

import java.net.ConnectException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.TypeConversionException;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.bean.validator.BeanValidationException;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.http.common.HttpOperationFailedException;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.HttpHostConnectException;
import org.springframework.http.MediaType;
import org.springframework.jms.UncategorizedJmsException;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.terpel.poc.wsterpeleds.model.ArrayStation;
import com.terpel.poc.wsterpeleds.model.EDS;
import com.terpel.poc.wsterpeleds.model.Request;
import com.terpel.poc.wsterpeleds.model.Station;

@Component
public class RestProducerAutorizacionRoute extends RouteBuilder {
	
	
	@Override
	public void configure()  throws Exception {
		JacksonDataFormat format = new JacksonDataFormat(Station.class);
		//format.useList();
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'-05:00'");//dd/MM/yyyy
		Date now = new Date();
		String strDate = sdfDate.format(now);
		
		final String TARGET_WITH_AUTH = "https://f5.iot.devitech.com.co:8010/gopass/autenticacion";
		
		format.useList();
		
		onException(Exception.class)		
			.handled(true)
			.log("Proceso: ${exchangeProperty.procesoId} | Mensaje: Encontro una exception HttpException: ${exception}")
			.setHeader("error", simple("${exception.message}"))
			.process(x->{
				String e = x.getIn().getHeader("error", String.class);
				x.getIn().setHeader("error", e.replaceAll("\"", "'"));
				
			})
			.to("velocity:vm/responso.json")
			.setHeader(Exchange.HTTP_RESPONSE_CODE, constant(500))
			.setHeader(Exchange.CONTENT_TYPE, constant(MediaType.APPLICATION_JSON))
			.end();
				
		from("direct:restProducerAutorizacion").routeId("wsterpeleds_rest_producer_autorizacion")			
			.log("Cuerpo Mensaje ${headers.token}")
			.log("Body ${body}")
			.setHeader("aplicacion", constant("OPEN_SF"))				
			.setHeader("fecha",simple(strDate))
			.setHeader("Authorization",simple("Bearer ${headers.token}"))
			.removeHeader("cuerpoMensaje")			
			.setHeader(Exchange.HTTP_METHOD, constant("POST"))
			.setHeader("CamelHttpMethod", constant("POST"))
			.setHeader(Exchange.HTTP_URI, simple(TARGET_WITH_AUTH))
			.setHeader(Exchange.CONTENT_TYPE, constant("application/json"))			
			.to("http4://cualquiercosa")	
			.log("Resaultado final: ${headers}")
			.end();
	}

}
