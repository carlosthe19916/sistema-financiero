package org.ventural.util.logger;

import org.slf4j.Logger;

/**
 * 
 * @author blog.adam-bien.com
 */
public interface Log {

	public Logger getLogger();

	public void info(String msg);

	public void trace(String message);

	public void debug(String message);

	public void warn(String message);

	public void error(String message);

}
