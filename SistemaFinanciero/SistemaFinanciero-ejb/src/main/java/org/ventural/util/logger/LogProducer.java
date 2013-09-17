package org.ventural.util.logger;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author blog.adam-bien.com
 */
public class LogProducer {

	@Produces
	public Log getLogger(InjectionPoint ip) {

		Class<?> aClass = ip.getMember().getDeclaringClass();
		Logger logger = LoggerFactory.getLogger(aClass.getName());

		return new DelegatingLogger(logger);

	}
}
