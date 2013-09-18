package org.ventural.util.logger;

import javax.enterprise.inject.Alternative;

import org.slf4j.Logger;

/**
 * @author blog.adam-bien.com
 */
@Alternative
public class DevNullLogger implements Log {


    @Override
    public Logger getLogger() {
        return null;
    }

    @Override
    public void info(String msg) {
    }

	@Override
	public void trace(String message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void debug(String message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void warn(String message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void error(String message) {
		// TODO Auto-generated method stub
		
	}

}
