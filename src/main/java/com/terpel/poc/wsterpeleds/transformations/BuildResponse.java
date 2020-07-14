package com.terpel.poc.wsterpeleds.transformations;

import java.util.stream.Collectors;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.bean.validator.BeanValidationException;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.terpel.poc.wsterpeleds.model.Response;
import com.terpel.poc.wsterpeleds.routes.RestTokenRoute;

@Component("buildResponse")
public class BuildResponse implements Processor{
	private Logger log = LoggerFactory.getLogger(BuildResponse.class);
	
	
	@Override
	public void process(Exchange exchange) throws Exception {
		Exception exception = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);
		if (exception != null) {
			if (exception instanceof BeanValidationException) {
				BeanValidationException exc = (BeanValidationException) exception;
				exc.getConstraintViolations().stream().map(c -> {
					exchange.getIn().setHeader("mensajeRespuesta", c.getMessage().toString());
					return null;
				}).collect(Collectors.toList());
			} else {
				if (exception.getCause() != null) {
					String causa = exception.getCause().toString();
					exchange.getIn().setHeader("mensajeRespuesta", "Se ha producido un error en la autenticación");
				}
			}
		}
	}

}
