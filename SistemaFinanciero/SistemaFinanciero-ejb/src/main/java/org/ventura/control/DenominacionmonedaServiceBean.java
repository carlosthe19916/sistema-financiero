package org.ventura.control;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.inject.Named;

import org.ventura.boundary.local.DenominacionmonedaServiceLocal;
import org.ventura.boundary.remote.DenominacionmonedaServiceRemote;
import org.ventura.dao.impl.DenominacionmonedaDAO;
import org.ventura.entity.schema.caja.Denominacionmoneda;
import org.ventura.entity.schema.maestro.Tipomoneda;
import org.ventura.entity.schema.socio.Socio;
import org.ventura.util.exception.RollbackFailureException;
import org.ventura.util.logger.Log;

@Named
@Stateless
@Local(DenominacionmonedaServiceLocal.class)
@Remote(DenominacionmonedaServiceRemote.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class DenominacionmonedaServiceBean implements
		DenominacionmonedaServiceLocal {

	@Inject
	private Log log;
	
	@EJB
	private DenominacionmonedaDAO denominacionmonedaDAO;

	@Override
	public Socio create(Socio oSocio) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Socio find(Object id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Socio oSocio) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Socio oSocio) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Denominacionmoneda> getDenominacionmonedasActive(Tipomoneda tipomoneda) throws RollbackFailureException, Exception {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("idtipomoneda", tipomoneda.getIdtipomoneda());
		List<Denominacionmoneda> denominacionmonedas = denominacionmonedaDAO.findByNamedQuery(Denominacionmoneda.findAllByTipoMoneda,parameters);
		return denominacionmonedas;
	}

	

}
