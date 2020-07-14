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
package com.terpel.poc.wsterpeleds.configurator;
import javax.jms.ConnectionFactory;
import org.apache.activemq.RedeliveryPolicy;
import org.apache.activemq.camel.component.ActiveMQComponent;
import org.apache.activemq.spring.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.connection.JmsTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.terpel.poc.wsterpeleds.properties.AmqNotificationProducer;

@Configuration
public class AmqNotificationConfigurationProducer {

    @Autowired
    private AmqNotificationProducer producer;
    
    @Bean("mail-connection-factory")
    public ConnectionFactory connectionFactory(){
    	String brokerURL;
    	
    	if(producer.getHostNameFailover() == null || producer.getHostNameFailover().equals("")) {
    		brokerURL = "tcp://" + producer.getHostName() + ":" + producer.getPort(); 
    	} else {
    		brokerURL = "failover:(tcp://" + producer.getHostName() + ":" + producer.getPort() 
			+ ",tcp://" + producer.getHostNameFailover() + ":" + producer.getPortFailover() + ")?maxReconnectAttempts=3"; 
    	}    	
    	ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
		connectionFactory.setBrokerURL(brokerURL);
        connectionFactory.setUserName(producer.getUser());
        connectionFactory.setPassword(producer.getPasswd());        
        return connectionFactory;
    }
    
	@Bean(name = "activemqnotification-component")
	public ActiveMQComponent amqpComponent() {		
		ActiveMQComponent amqComp= new ActiveMQComponent();
		amqComp.setConnectionFactory(connectionFactory());
		return amqComp;
	}
}