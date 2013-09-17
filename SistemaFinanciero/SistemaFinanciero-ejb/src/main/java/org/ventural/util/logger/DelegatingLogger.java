package org.ventural.util.logger;

import javax.enterprise.inject.Alternative;

import org.slf4j.Logger;

/**
 * @author blog.adam-bien.com
 */
@Alternative
public class DelegatingLogger implements Log {

	private Logger logger;

	public DelegatingLogger(Logger logger) {
		this.logger = logger;
	}

	public void trace(String message, Object[] params) {
		this.logger.trace(message, params);
	}

	void debug(String message, Object[] params) {
		this.logger.debug(message, params);
	}

	void info(String message, Object[] params) {
		this.logger.info(message, params);
	}

	void warn(String message, Object[] params) {
		this.logger.warn(message, params);
	}

	void error(String message, Object[] params) {
		this.logger.error(message, params);
	}

	@Override
	public void info(String msg) {
		this.info(msg, new Object[] {});
	}

	@Override
	public Logger getLogger() {
		return logger;
	}
}
