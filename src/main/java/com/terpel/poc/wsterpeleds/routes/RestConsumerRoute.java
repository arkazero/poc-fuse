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

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.terpel.poc.wsterpeleds.model.Order;
import com.terpel.poc.wsterpeleds.model.Request;
import com.terpel.poc.wsterpeleds.properties.RestConsumer;

@Component
public class RestConsumerRoute extends RouteBuilder{
    
//	@Autowired
//	private RestConsumer restConfig;
	private Logger log = LoggerFactory.getLogger(TransformationRoute.class);
	
	private String contextPath = "";
    @Override
    public void configure()  throws Exception {
    	log.error("::::::::::::::Ingresando a la ruta::::::::::::");
    	//super.configure();    	
        restConfiguration()
	       .component("servlet")
	       .port("8080")
	       .bindingMode(RestBindingMode.auto)	       
	       .contextPath(contextPath)
	       .apiContextPath("/api-doc")
	       .apiProperty("api.title", "ws-terpel-eds")
	       .apiProperty("api.version", "1.0")	       
	       .apiProperty("cors", "true");
    	
        rest()
        .post("/ServiceOrder")
        	.type(Request.class)        	        	
        	.to("direct:orquestador");
    }
}
