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
package com.terpel.poc.wsterpeleds.transformations;
import org.springframework.stereotype.Component;

import com.terpel.poc.wsterpeleds.model.Response;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.camel.Exchange;
import org.apache.log4j.Logger;

@Component("transformationComponent")
public class TransformationComponent {

	static final Logger logger = Logger.getLogger("ws-terpel-edsLogger");
	
	public static String transformation(String body) {
		//You must write this method to process the body.
		logger.info("transformation component."+body);
	return body;
   }
	
	public void buildResponse(Exchange ex) {
		Response resp = new Response();
		
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'-05:00'");//dd/MM/yyyy
		Date now = new Date();
		String strDate = sdfDate.format(now);
		
		resp.setCodigo(ex.getIn().getHeader("codigoRespuesta").toString());
		resp.setMensaje(ex.getIn().getHeader("mensajeRespuesta").toString());
		resp.setFechaProceso(strDate);
		ex.getIn().setBody(resp);
	}
}
