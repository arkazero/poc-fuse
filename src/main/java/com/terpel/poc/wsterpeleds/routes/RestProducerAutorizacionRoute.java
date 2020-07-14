package com.terpel.poc.wsterpeleds.routes;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.stereotype.Component;

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
		
		final String TARGET_WITH_AUTH = "http://servicios.devitech.com.co:8010/gopass/notificacionTransaccion";
		
		format.useList();
		from("direct:restProducerAutorizacion").routeId("wsterpeleds_rest_producer_autorizacion")
			.log("Cuerpo Mensaje ${headers.token}")
			.log("Body ${body}")
			.setHeader("aplicacion", constant("GOPASS"))				
			.setHeader("fecha",simple(strDate))
			.setHeader("Authorization",simple("Bearer ${headers.token}"))
			.removeHeader("cuerpoMensaje")
			.log("==============================================")
			.log("${headers}")
			.log("==============================================")
			.setHeader(Exchange.HTTP_METHOD, constant("POST"))
			.setHeader("CamelHttpMethod", constant("POST"))
			.setHeader(Exchange.HTTP_URI, simple(TARGET_WITH_AUTH))
			.setHeader(Exchange.CONTENT_TYPE, constant("application/json"))			
			.to("http4://cualquiercosa")	
			.log("${body}")
			.end();
	}

}
