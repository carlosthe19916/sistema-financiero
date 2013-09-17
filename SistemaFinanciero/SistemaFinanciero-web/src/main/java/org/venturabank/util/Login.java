package org.venturabank.util;

import java.io.IOException;

import javax.ejb.EJB;
import javax.faces.bean.ManagedProperty;
import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.venturabank.managedbean.UsuarioMB;

//@WebFilter("/*")
public class Login implements Filter {

	@Override
	public void init(FilterConfig filterConfig) {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;

		HttpServletResponse resp = (HttpServletResponse) response;

		/*
		 * if (req.authenticate(resp)) { chain.doFilter(req, resp); }
		 */
		UsuarioMB usuario = (UsuarioMB) ((HttpServletRequest) request)
				.getSession().getAttribute("usuarioMB");

		
/*
		if (usuario == null) {
			RequestDispatcher dispatcher = req.getServletContext().getRequestDispatcher("/faces/login.xhtml");
			dispatcher.forward(request, response);
		} else {
			if (usuario.getUsuario().getNombreusuario() == "admin"
					&& usuario.getUsuario().getPassword() == "123") {

				System.out.println("etro");

				RequestDispatcher dispatcher = req
						.getServletContext()
						.getRequestDispatcher(
								"/faces/views/cuentaPersonal/apertura/cuentaAhorro.xhtml");
				dispatcher.forward(request, response);

			} else {

				System.out.println("et333ro");

				// resp.sendRedirect("/SistemaFinanciero-web/faces/login.xhtml");
				RequestDispatcher dispatcher = req.getServletContext()
						.getRequestDispatcher("/faces/login.xhtml");
				dispatcher.forward(request, response);
			}
		}
*/
	}

	@Override
	public void destroy() {
	}

}