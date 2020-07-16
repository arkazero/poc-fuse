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
package com.terpel.poc.wsterpeleds.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Configuration;

@Configuration
//@PropertySource("file:///${CONFIG_LOCATION}/rest_base.properties")
@ConfigurationProperties(prefix = "ws-terpel-eds.rest")
public class RestConsumer {
	
	private String serviceName;
	private String apiPath;
	private String apiTitle;
	private String apiVersion;
	private String apiBasePath;
	
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getApiPath() {
		return apiPath;
	}
	public void setApiPath(String apiPath) {
		this.apiPath = apiPath;
	}
	public String getApiTitle() {
		return apiTitle;
	}
	public void setApiTitle(String apiTitle) {
		this.apiTitle = apiTitle;
	}
	public String getApiVersion() {
		return apiVersion;
	}
	public void setApiVersion(String apiVersion) {
		this.apiVersion = apiVersion;
	}
	public String getApiBasePath() {
		return apiBasePath;
	}
	public void setApiBasePath(String apiBasePath) {
		this.apiBasePath = apiBasePath;
	}
	
}
