package org.ventura.control;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.inject.Named;

import org.ventura.boundary.local.VariableSistemaServiceLocal;
import org.ventura.boundary.remote.VariableSistemaServiceRemote;
import org.ventura.dao.impl.VariableSistemaDAO;
import org.ventura.entity.schema.maestro.VariableSistema;
import org.ventura.util.logger.Log;
import org.ventura.util.maestro.ProduceObjectVariableSistema;
import org.ventura.util.maestro.VariableSistemaType;

@Named
@Stateless
@Local(VariableSistemaServiceLocal.class)
@Remote(VariableSistemaServiceRemote.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class VariableSistemaServiceBean implements VariableSistemaServiceLocal {

	@Inject
	private Log log;
	
	@EJB private VariableSistemaDAO variableSistemaDAO;
	
	@Override
	public VariableSistema getVariableSistema(VariableSistemaType variableSistemaType) throws Exception {
		VariableSistema variableSistema = ProduceObjectVariableSistema.getVariableSistema(variableSistemaType);
		variableSistema = variableSistemaDAO.find(variableSistema.getIdvariablesistema());
		return variableSistema;
	}

	@Override
	public void updateVariableSistema(VariableSistema variableSistema) throws Exception {
		try {
			variableSistemaDAO.update(variableSistema);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw e;
		}
	}
	
}
