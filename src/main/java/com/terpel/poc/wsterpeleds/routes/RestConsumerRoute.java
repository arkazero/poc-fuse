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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.terpel.poc.wsterpeleds.model.Order;
import com.terpel.poc.wsterpeleds.model.Request;
import com.terpel.poc.wsterpeleds.properties.RestConsumer;
import com.terpel.poc.wsterpeleds.configurator.ConfigurationRoute;


@Component
public class RestConsumerRoute extends RouteBuilder{
    
//	@Autowired
//	private RestConsumer restConfig;
	
    @Override
    public void configure()  throws Exception {

    	//super.configure();
    	
        restConfiguration()
	       .component("servlet")
	       .apiContextPath("/api-doc")
	       .apiProperty("api.title", "ws-terpel-eds-api")
	       .apiProperty("api.version", "1.0")
	       .apiProperty("base.path", "/api-doc")
	       .apiProperty("cors", "true");

        rest("/ServiceOrder")
        .post()
        	.type(Request.class)        	        	
        	.to("direct:orquestador");
    }
}
