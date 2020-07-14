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
@PropertySource("file:///${CONFIG_LOCATION}/amq_notification.properties")
@ConfigurationProperties(prefix = "ws-terpel-eds.amq.notification")
public class AmqNotificationProducer {
	
	private String hostName;
	
	private String port;
	
	private String user;
	
	private String passwd;
	
	private String queueName;
	
	private String hostNameFailover;
	
	private String portFailover;
	
	public String getHostNameFailover() {
		return hostNameFailover;
	}

	public void setHostNameFailover(String hostNameFailover) {
		this.hostNameFailover = hostNameFailover;
	}

	public String getPortFailover() {
		return portFailover;
	}

	public void setPortFailover(String portFailover) {
		this.portFailover = portFailover;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getQueueName() {
		return queueName;
	}

	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}

}
