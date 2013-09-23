package org.ventura.control;

import java.util.Properties;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.ventura.boundary.local.CuentaahorroServiceLocal;
import org.ventura.boundary.remote.CuentaahorroServiceRemote;

public class CuentaahorroServiceBeanTest {

	static Context context;

	CuentaahorroServiceBean bean;

	@BeforeClass
	public static void setUpBeforeAllTest() throws NamingException {
		Properties props = new Properties();
		props.setProperty(Context.INITIAL_CONTEXT_FACTORY,
				"org.apache.openejb.client.LocalInitialContextFactory");

		context = new InitialContext(props);

	}

	@org.junit.AfterClass
	public static void tearDownAfterAllTest() throws NamingException {
		context.close();
	}

	/**
	 * Invocación a través del interfaz local
	 * 
	 * @throws NamingException
	 */
	@Test
	public void local() throws NamingException {
		context.lookup("CuentaahorroServiceBean");
		//System.out.println(local);
	}

	/**
	 * Invocación a través del interfaz remoto
	 * 
	 * @throws NamingException
	 */
	@org.junit.Test
	public void dummyGreeterBean_sayHi_remote() throws NamingException {
		/*CuentaahorroServiceRemote remote = (CuentaahorroServiceRemote) context.lookup("CuentaahorroServiceRemote");
		System.out.println(remote);*/
	}

}
