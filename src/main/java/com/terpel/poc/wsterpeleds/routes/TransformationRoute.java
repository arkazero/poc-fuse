package com.terpel.poc.wsterpeleds.routes;

import java.net.ConnectException;

import org.apache.camel.LoggingLevel;
import org.apache.camel.TypeConversionException;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.bean.validator.BeanValidationException;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.terpel.poc.wsterpeleds.configurator.ConfigurationRoute;
import com.terpel.poc.wsterpeleds.model.CustomException;
import com.terpel.poc.wsterpeleds.model.Request;
import com.terpel.poc.wsterpeleds.model.Response;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.UncategorizedJmsException;


@Component
public class TransformationRoute extends RouteBuilder {
	
//	@Value("${headersValidationService}")
//	private String headersValidationService;
	
	private static final String SUCCESS_RESPONSE = "direct:buildSuccessResponse";
	private static final String AUTH_ERROR_MSG = "Se ha producido un error en la autenticación";
	private Logger log = LoggerFactory.getLogger(TransformationRoute.class);
		
	public void configure()  throws Exception {
		
		//super.configure();
		
		JacksonDataFormat request = new JacksonDataFormat(Request.class);
		JacksonDataFormat response = new JacksonDataFormat(Response.class);
		
		onException(TypeConversionException.class)
		.handled(true)
		.log(LoggingLevel.ERROR, log, "Ha ocurrido una excepcion TypeConversionException: " + exceptionMessage())
        .setHeader("CamelHttpResponseCode", simple("400"))
        .setHeader("codigoRespuesta", constant("FAILED"))
        .setHeader("mensajeRespuesta", constant(AUTH_ERROR_MSG))
//        .bean("transformations", "buildResponse")
//        .marshal(response)
        ;
	
		onException(ConnectException.class)
			.handled(true)
			.log(LoggingLevel.ERROR, log, "Ha ocurrido una excepcion ConnectException: " + exceptionMessage())
	        .setHeader("CamelHttpResponseCode", simple("400"))
	        .setHeader("codigoRespuesta", constant("FAILED"))
	        .setHeader("mensajeRespuesta", constant(AUTH_ERROR_MSG))
//	        .bean("transformations", "buildResponse")
//	        .marshal(response)
	        ;
		
		onException(CustomException.class)
			.handled(true)
			.log(LoggingLevel.ERROR, log, "Ha ocurrido un error de validacion - Error: " + exceptionMessage())
	        .setHeader("CamelHttpResponseCode", simple("200"))
	        .setHeader("codigoRespuesta", constant("OK"))
	        .setHeader("mensajeRespuesta", exceptionMessage())
//	        .bean("transformations", "buildResponse")	        
//	        .marshal(response)
	        ;

		onException(JsonParseException.class, InvalidFormatException.class, UncategorizedJmsException.class)
	        .handled(true)
	        .log(LoggingLevel.ERROR, log, "Ha ocurrido una excepcion: " + exceptionMessage())
	        .setHeader("CamelHttpResponseCode", simple("400"))
	        .setHeader("codigoRespuesta", constant("FAILED"))
	        .setHeader("mensajeRespuesta", constant(AUTH_ERROR_MSG))
//	        .bean("transformations", "buildResponse")
//	        .marshal(response)
	        ;
	
		onException(BeanValidationException.class)
			.handled(true)
			.log(LoggingLevel.ERROR, log, "Ha ocurrido una excepcion (BeanValidationException): " + exceptionMessage())
			.setHeader("CamelHttpResponseCode", simple("200"))
	        .setHeader("codigoRespuesta", constant("OK"))
	        .setHeader("mensajeRespuesta", constant(AUTH_ERROR_MSG))
//	        .bean("transformations", "buildResponse")	        
//	        .marshal(response)
	        ;
						
		from("direct:orquestador").routeId("wsterpeleds_orquestador")
			.to("direct:restProducerEDS")     

			.to("direct:definirTokenProducer")			

	    	.to("direct:restProducerAutorizacion")
		.end();
	}
}