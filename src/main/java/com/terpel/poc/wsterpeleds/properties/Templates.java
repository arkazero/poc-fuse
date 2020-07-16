package com.terpel.poc.wsterpeleds.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
//@PropertySource("file:///${CONFIG_LOCATION}/rest_prod_base.properties")
@ConfigurationProperties(prefix = "ws-terpel-eds.rest.prod")
public class Templates {
	

}
