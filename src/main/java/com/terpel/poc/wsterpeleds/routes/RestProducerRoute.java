/*
 * Copyright 2005-2016 Red Hat, Inc.
 *
 * Red Hat licenses this file to you under the Apache License, version
 * 2.0 (the "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.  See the License for the specific language governing
 * permissions and limitations under the License.
 */
package com.terpel.poc.wsterpeleds.routes;

import com.ctc.wstx.exc.WstxParsingException;
import com.ctc.wstx.exc.WstxEOFException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.NoRouteToHostException;
import java.net.UnknownHostException;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.wsdl.WSDLException;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.Exchange;
import org.apache.camel.processor.validation.SchemaValidationException;
import org.apache.http.HttpException;
import org.apache.http.conn.HttpHostConnectException;
import org.xml.sax.SAXParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.terpel.poc.wsterpeleds.properties.RestProducer;
import com.terpel.poc.wsterpeleds.configurator.ConfigurationRoute;
import com.terpel.poc.wsterpeleds.properties.MailProperties;

@Component
public class RestProducerRoute extends ConfigurationRoute {
    
	@Autowired
	private RestProducer restConfig;
	
	@Override
	public void configure()  throws Exception {
	   super.configure();
	   
	   onException(SocketTimeoutException.class).handled(true)
		    .maximumRedeliveries(3)
		    .redeliveryDelay(2000)
		    .log(LoggingLevel.ERROR, "TRV-04 El tiempo de espera al host de destino se ha agotado presenta errores de conexi�n en la ruta ${routeId}")
		    .log(LoggingLevel.ERROR, "ExceptionClass: ${exchangeProperty.CamelExceptionCaught.class}")
		    .log(LoggingLevel.ERROR, "ExceptionClassName: ${exchangeProperty.CamelExceptionCaught.class.name}")
		    .log(LoggingLevel.ERROR, "StackTrace: ${exception.stacktrace}")
//		    .setHeader("mailErrorDescription", simple(mailConfig.getErrorConexion().toString()))
//		    .to("direct:mailNotification")
	   .end();
	   
	   onException(NoRouteToHostException.class).handled(true)
	       .maximumRedeliveries(3)
	       .redeliveryDelay(2000)
	       .log(LoggingLevel.ERROR, "WS-01 El host de destino no esta disponible presenta errores de comunicaci�n en la ruta ${routeId}")
	       .log(LoggingLevel.ERROR, "ExceptionClass: ${exchangeProperty.CamelExceptionCaught.class}")
	       .log(LoggingLevel.ERROR, "ExceptionClassName: ${exchangeProperty.CamelExceptionCaught.class.name}")
	       .log(LoggingLevel.ERROR, "StackTrace: ${exception.stacktrace}")
//	       .setHeader("mailErrorDescription", simple(mailConfig.getErrorConexion().toString()))
//	       .to("direct:mailNotification")
       .end();
	   
	   onException(HttpException.class).handled(true)
	       .maximumRedeliveries(3)
	       .redeliveryDelay(2000)
	       .log(LoggingLevel.ERROR, "WS-04 La comunicaci�n por protocolo HTTP al destino ha fallado presenta errores en la ruta ${routeId}")
	       .log(LoggingLevel.ERROR, "ExceptionClass: ${exchangeProperty.CamelExceptionCaught.class}")
	       .log(LoggingLevel.ERROR, "ExceptionClassName: ${exchangeProperty.CamelExceptionCaught.class.name}")
	       .log(LoggingLevel.ERROR, "StackTrace: ${exception.stacktrace}")
//	       .setHeader("mailErrorDescription", simple(mailConfig.getErrorConexion().toString()))
//	       .to("direct:mailNotification")
       .end();
	   
	   onException(WSDLException.class).handled(true)
	       .log(LoggingLevel.ERROR, "WS-05 La estrucutura del archivo wsdl presenta errores en la ruta ${routeId}")
	       .log(LoggingLevel.ERROR, "ExceptionClass: ${exchangeProperty.CamelExceptionCaught.class}")
	       .log(LoggingLevel.ERROR, "ExceptionClassName: ${exchangeProperty.CamelExceptionCaught.class.name}")
	       .log(LoggingLevel.ERROR, "StackTrace: ${exception.stacktrace}")
//	       .setHeader("mailErrorDescription", simple(mailConfig.getErrorEstructura().toString()))
//	       .to("direct:mailNotification")
       .end();
	   
	   onException(UnknownHostException.class).handled(true)
	       .maximumRedeliveries(3)
	       .redeliveryDelay(2000)
	       .log(LoggingLevel.ERROR, "WS-07 El host de destino es desconocido presenta errores de comunicaci�n en la ruta ${routeId}")
	       .log(LoggingLevel.ERROR, "ExceptionClass: ${exchangeProperty.CamelExceptionCaught.class}")
	       .log(LoggingLevel.ERROR, "ExceptionClassName: ${exchangeProperty.CamelExceptionCaught.class.name}")
	       .log(LoggingLevel.ERROR, "StackTrace: ${exception.stacktrace}")
//	       .setHeader("mailErrorDescription", simple(mailConfig.getErrorConexion().toString()))
//	       .to("direct:mailNotification")
       .end();
	   
	   onException(HttpHostConnectException.class).handled(true)
	       .maximumRedeliveries(3)
	       .redeliveryDelay(2000)
	       .log(LoggingLevel.ERROR, "WS-09 Durante la comunicaci�n por protocolo HTTP al host destino se presentan errores en la ruta ${routeId}")
	       .log(LoggingLevel.ERROR, "ExceptionClass: ${exchangeProperty.CamelExceptionCaught.class}")
	       .log(LoggingLevel.ERROR, "ExceptionClassName: ${exchangeProperty.CamelExceptionCaught.class.name}")
	       .log(LoggingLevel.ERROR, "StackTrace: ${exception.stacktrace}")
//	       .setHeader("mailErrorDescription", simple(mailConfig.getErrorConexion().toString()))
//	       .to("direct:mailNotification")
       .end();
	   
	   onException(WstxEOFException.class).handled(true)
	       .log(LoggingLevel.ERROR, "WS-11 La estructura del mensaje XML a procesar presenta errores en las etiquetas en la ruta ${routeId}")
	       .log(LoggingLevel.ERROR, "ExceptionClass: ${exchangeProperty.CamelExceptionCaught.class}")
	       .log(LoggingLevel.ERROR, "ExceptionClassName: ${exchangeProperty.CamelExceptionCaught.class.name}")
	       .log(LoggingLevel.ERROR, "StackTrace: ${exception.stacktrace}")
//	       .setHeader("mailErrorDescription", simple(mailConfig.getErrorEstructura().toString()))
//	       .to("direct:mailNotification")
       .end();
	   
	   onException(SAXParseException.class).handled(true)
	       .log(LoggingLevel.ERROR, "WS-12 El mensaje xml recibido presenta errores de codificaci�n en la ruta ${routeId}")
	       .log(LoggingLevel.ERROR, "ExceptionClass: ${exchangeProperty.CamelExceptionCaught.class}")
	       .log(LoggingLevel.ERROR, "ExceptionClassName: ${exchangeProperty.CamelExceptionCaught.class.name}")
	       .log(LoggingLevel.ERROR, "StackTrace: ${exception.stacktrace}")
//	       .setHeader(Exchange.HTTP_RESPONSE_CODE, constant("Bad request 400"))
//	       .setHeader(Exchange.CONTENT_TYPE, constant("application/xml"))
//	       .setHeader("mailErrorDescription", simple(mailConfig.getErrorEstructura().toString()))
//	       .to("direct:mailNotification")
       .end();
	   
	   onException(WstxParsingException.class).handled(true)
	       .log(LoggingLevel.ERROR, "WS-13 La estructura del mensaje XML a procesar tiene etiquetas sin cerrar en la ruta ${routeId}")
	       .log(LoggingLevel.ERROR, "ExceptionClass: ${exchangeProperty.CamelExceptionCaught.class}")
	       .log(LoggingLevel.ERROR, "ExceptionClassName: ${exchangeProperty.CamelExceptionCaught.class.name}")
	       .log(LoggingLevel.ERROR, "StackTrace: ${exception.stacktrace}")
//	       .setHeader("mailErrorDescription", simple(mailConfig.getErrorEstructura().toString()))
//	       .to("direct:mailNotification")
       .end();
	   
	   onException(SchemaValidationException.class).handled(true)
	       .log(LoggingLevel.ERROR, "WS-14 El esquema del mensaje xml recibido presenta errores en la ruta ${routeId}")
	       .log(LoggingLevel.ERROR, "ExceptionClass: ${exchangeProperty.CamelExceptionCaught.class}")
	       .log(LoggingLevel.ERROR, "ExceptionClassName: ${exchangeProperty.CamelExceptionCaught.class.name}")
	       .log(LoggingLevel.ERROR, "StackTrace: ${exception.stacktrace}")
//	       .setHeader(Exchange.HTTP_RESPONSE_CODE, constant("Bad request 400"))
//	       .setHeader(Exchange.CONTENT_TYPE, constant("application/xml"))
//	       .setHeader("mailErrorDescription", simple(mailConfig.getErrorEstructura().toString()))
//	       .to("direct:mailNotification")
       .end();
	   
	   onException(SSLPeerUnverifiedException.class).handled(true)
	       .log(LoggingLevel.ERROR, "WS-15 El mensaje a procesar requiere del certificado provisto por SAP, presenta errores en la ruta ${routeId}")
	       .log(LoggingLevel.ERROR, "ExceptionClass: ${exchangeProperty.CamelExceptionCaught.class}")
	       .log(LoggingLevel.ERROR, "ExceptionClassName: ${exchangeProperty.CamelExceptionCaught.class.name}")
	       .log(LoggingLevel.ERROR, "StackTrace: ${exception.stacktrace}")
//	       .setHeader("mailErrorDescription", simple(mailConfig.getErrorEstructura().toString()))
//	       .to("direct:mailNotification")
       .end();
	   
	   onException(ConnectException.class).handled(true)
			.maximumRedeliveries(3)
			.redeliveryDelay(2000)
			.log(LoggingLevel.ERROR, "TRV-01 El host de destino no ha sido alcanzado presenta errores de conexi�n en la ruta ${routeId}")
			.log(LoggingLevel.ERROR, "ExceptionClass: ${exchangeProperty.CamelExceptionCaught.class}")
			.log(LoggingLevel.ERROR, "ExceptionClassName: ${exchangeProperty.CamelExceptionCaught.class.name}")
			.log(LoggingLevel.ERROR, "StackTrace: ${exception.stacktrace}")
//			.setHeader("mailErrorDescription", simple(mailConfig.getErrorConexion().toString()))
//			.to("direct:mailNotification")
		.end();
   
       from("direct:restProducerRoute").routeId("wsterpeleds_rest_producer")
	       .setHeader(Exchange.HTTP_METHOD, simple(restConfig.getMethod()))
	       .setHeader(Exchange.CONTENT_TYPE, simple(restConfig.getContentType()))
	       .to("https4://"+restConfig.getHost()+"/" + restConfig.getContext() +"/" + restConfig.getServiceName() + "?bridgeEndpoint=true")
	       .log("respuesta: ${body}")
       .end();
    }
   
}
