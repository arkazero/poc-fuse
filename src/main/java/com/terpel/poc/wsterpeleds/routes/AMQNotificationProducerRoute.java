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

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import javax.jms.JMSException;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.terpel.poc.wsterpeleds.properties.AmqNotificationProducer;
import com.terpel.poc.wsterpeleds.configurator.ConfigurationRoute;

@Component
public class AMQNotificationProducerRoute extends RouteBuilder {

	@Autowired
	private AmqNotificationProducer amqpProducerConfig;

	public void configure() throws Exception {
		//super.configure();
		
		onException(ConnectException.class)
		    .maximumRedeliveries(3)
		    .redeliveryDelay(2000)
		    .log(LoggingLevel.ERROR, "TRV-01 El host de destino no ha sido alcanzado presenta errores de conexi�n en la ruta ${routeId}")
		    .log(LoggingLevel.ERROR, "ExceptionClass: ${exchangeProperty.CamelExceptionCaught.class}")
		    .log(LoggingLevel.ERROR, "ExceptionClassName: ${exchangeProperty.CamelExceptionCaught.class.name}")
		    .log(LoggingLevel.ERROR, "StackTrace: ${exception.stacktrace}")
	    .end();
		
		onException(SocketTimeoutException.class)
		    .maximumRedeliveries(3)
		    .redeliveryDelay(2000)
		    .log(LoggingLevel.ERROR, "TRV-04 El tiempo de espera al host de destino se ha agotado presenta errores de conexi�n en la ruta ${routeId}")
		    .log(LoggingLevel.ERROR, "ExceptionClass: ${exchangeProperty.CamelExceptionCaught.class}")
		    .log(LoggingLevel.ERROR, "ExceptionClassName: ${exchangeProperty.CamelExceptionCaught.class.name}")
		    .log(LoggingLevel.ERROR, "StackTrace: ${exception.stacktrace}")
	    .end();
		
		onException(JMSException.class)
			.maximumRedeliveries(3)
		    .redeliveryDelay(2000)
		    .log(LoggingLevel.ERROR, "AMQ-01 El mensaje no ha sido almacenado en la cola presenta errores en la ruta ${routeId}")
		    .log(LoggingLevel.ERROR, "ExceptionClass: ${exchangeProperty.CamelExceptionCaught.class}")
		    .log(LoggingLevel.ERROR, "ExceptionClassName: ${exchangeProperty.CamelExceptionCaught.class.name}")
		    .log(LoggingLevel.ERROR, "StackTrace: ${exception.stacktrace}")
	    .end();
		// context().setStreamCaching(true);
		//from("direct:amqNotificationProducerRoute").routeId("amq_notification_producer")
		from("direct:amqNotificationProducerRoute").routeId("amq_notification_producer")
			.log("Sending to amq notification")
			//.inOnly("activemqnotification:" + amqpProducerConfig.getQueueName())
			//.to( "activemq:" + amqpProducerConfig.getQueueName())
			.log("Sending to amq notification")
		.end();

	}
}
