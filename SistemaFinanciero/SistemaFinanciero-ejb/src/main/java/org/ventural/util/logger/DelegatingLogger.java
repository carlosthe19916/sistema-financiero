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

	public void debug(String message, Object[] params) {
		this.logger.debug(message, params);
	}

	public void info(String message, Object[] params) {
		this.logger.info(message, params);
	}

	public void warn(String message, Object[] params) {
		this.logger.warn(message, params);
	}

	public void error(String message, Object[] params) {
		this.logger.error(message, params);
	}

	@Override
	public void info(String msg) {
		this.info(msg, new Object[] {});
	}

	@Override
	public void trace(String msg) {
		this.trace(msg, new Object[] {});
	}

	@Override
	public void debug(String msg) {
		this.debug(msg, new Object[] {});
	}

	@Override
	public void warn(String msg) {
		this.warn(msg, new Object[] {});
	}

	@Override
	public void error(String msg) {
		this.error(msg, new Object[] {});
	}

	@Override
	public Logger getLogger() {
		return logger;
	}

}
