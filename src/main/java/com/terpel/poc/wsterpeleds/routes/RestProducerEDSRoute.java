package com.terpel.poc.wsterpeleds.routes;


import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.terpel.poc.wsterpeleds.model.EDS;
import com.terpel.poc.wsterpeleds.model.Request;
import com.terpel.poc.wsterpeleds.properties.RestProducer;

@Component
public class RestProducerEDSRoute extends RouteBuilder{
	
	
	@Autowired
    private Environment env;

	@Override
	public void configure()  throws Exception {
		//super.configure();		
		JacksonDataFormat format = new JacksonDataFormat(Request.class);
		JacksonDataFormat format_eds = new JacksonDataFormat(EDS.class);		
				
				
		format_eds.useList();
		from("direct:restProducerEDS").routeId("wsterpeleds_rest_producer_eds")

		.log("Entrada a RestProducerEDSRoute: ${body}")
		.log("Entrada a RestProducerEDSRoute: ${body.idEDS}")		
			.setHeader("idEDS", simple("${body.idEDS}"))	
			.setHeader("surtidor", simple("${body.idSurtidor}"))
			.setHeader("cara", simple("${body.idCara}"))							
			.marshal(format)//.json(JsonLibrary.Jackson, Order.class)//				
			.setHeader(Exchange.HTTP_METHOD, simple(env.getProperty("METHOD_PRODUCER_REST")))			
			.setHeader(Exchange.HTTP_URI, simple(env.getProperty("HOST_PRODUCER_REST") + "" 
					+ env.getProperty("CONTEXT_PRODUCER_REST")+ "" 
					+ env.getProperty("SERVICE_PRODUCER_REST") + ""					
					+ "/${headers.idEDS}"))
			
						
			.to("https4://cualquiercosa")
			.convertBodyTo(String.class)
			.log("respuesta servicio EDS: ${body}")	
			
			.unmarshal(format_eds)			
			.to("bean-validator://x")
			.log("respuesta: ${body[0].ip}")
//			.setHeader("CamelVelocityResourceUri",simple("{{TEMPLATE_EDS}}"))
//			.to("velocity:dummy?loaderCache=false")
			.to("velocity:vm/eds.vm")
			.log("respuesta template: ${body}")
			.end();
	}

}
