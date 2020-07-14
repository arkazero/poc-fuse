package com.terpel.poc.wsterpeleds.routes;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.terpel.poc.wsterpeleds.configurator.ConfigurationRoute;
import com.terpel.poc.wsterpeleds.model.EDS;
import com.terpel.poc.wsterpeleds.model.Order;
import com.terpel.poc.wsterpeleds.model.Request;
import com.terpel.poc.wsterpeleds.properties.RestProducer;

@Component
public class RestProducerEDSRoute extends  ConfigurationRoute {

	@Autowired
	private RestProducer restConfig;

	@Override
	public void configure()  throws Exception {
		super.configure();
		JacksonDataFormat format = new JacksonDataFormat(Request.class);
		JacksonDataFormat format_eds = new JacksonDataFormat(EDS.class);
		format_eds.useList();
		from("direct:restProducerEDS").routeId("wsterpeleds_rest_producer_eds")
			.log("respuesta: ${body}")
			.unmarshal(format)			
			.to("bean-validator://x")			
			.setHeader("idEDS", simple("${body.idDS}"))	
			.setHeader("surtidor", simple("${body.idSurtidor}"))
			.setHeader("cara", simple("${body.idCara}"))					
			.log("Body  ${body.idDS}")			
			//.bean("transformationComponent", "transformation")
			.marshal(format)//.json(JsonLibrary.Jackson, Order.class)//			
			.setHeader(Exchange.HTTP_METHOD, simple(restConfig.getMethod()))
			.setHeader(Exchange.HTTP_URI, simple("https://" + restConfig.getHost() + "/" 
					+ restConfig.getContext() + "/"
					+ restConfig.getServiceName() + "/"					
					+ "${headers.idEDS}?bridgeEndpoint=true"))
			.setHeader(Exchange.CONTENT_TYPE, simple(restConfig.getContentType()))			
			.to("https4://cualquiercosa")
			.log("respuesta servicio EDS: ${body}")	
			.unmarshal(format_eds)			
			.to("bean-validator://x")
			.log("respuesta: ${body[0].ip}")
			.setHeader("CamelVelocityResourceUri",simple("{{TEMPLATE_EDS}}"))
			.to("velocity:dummy?loaderCache=false")
			.log("respuesta template: ${body}")
			.end();
	}

}
