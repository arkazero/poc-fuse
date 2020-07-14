package com.terpel.poc.wsterpeleds.transformations;

import java.util.HashMap;
import java.util.Map;
import org.apache.camel.Exchange;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.terpel.poc.wsterpeleds.properties.MailProperties;

@Component("createMail")
public class CreateMail {
	static final Logger logger = Logger.getLogger(CreateMail.class);

	@Autowired
	private MailProperties mailConfig;

	public void crearMail(Exchange ex) throws Exception {

		Exception e = ex.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);
		String exceptionMessage = "";
		Throwable causa;

		logger.info("\n\n::prepareMailingNotificationProcessor::\nArmando mensaje\n\n");
		Map<String, Object> map = new HashMap<>();

		map.put("from", mailConfig.getFrom());
		map.put("to", mailConfig.getTo());
		map.put("cc", mailConfig.getCc());
		map.put("bcc", mailConfig.getBcc());
		map.put("subject", mailConfig.getSubject());
		map.put("template", mailConfig.getTemplate());
		map.put("NombreServicio", mailConfig.getService());
		map.put("TipoServicio", mailConfig.getServiceType());

		if (e != null && e.getMessage() != null && !e.getMessage().isEmpty()) {
			exceptionMessage = e.getMessage();
			map.put("DescripcionError",
					ex.getIn().getHeader("mailErrorDescription") + "\nException: " + exceptionMessage);
			if (e.getCause() != null) {
				causa = e.getCause();
				map.put("DescripcionError", ex.getIn().getHeader("mailErrorDescription") + "\nException: "
						+ exceptionMessage + "\nCausa: " + causa);
			}
		} else {
			map.put("DescripcionError", ex.getIn().getHeader("mailErrorDescription"));
		}
		logger.info("ERROR: " + ex.getIn().getHeader("mailErrorDescription"));

		ex.getIn().setBody(map, Map.class);
	}
}