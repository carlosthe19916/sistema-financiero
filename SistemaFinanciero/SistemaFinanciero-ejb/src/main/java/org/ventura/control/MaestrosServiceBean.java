package org.ventura.control;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.ventura.boundary.local.MaestrosServiceLocal;
import org.ventura.boundary.remote.MaestrosServiceRemote;
import org.ventura.dao.CrudService;
import org.ventura.entity.schema.persona.Tipodocumento;
import org.ventura.util.logger.Log;

@Stateless
@Local(MaestrosServiceLocal.class)
@Remote(MaestrosServiceRemote.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class MaestrosServiceBean implements MaestrosServiceLocal {

	@Inject
	private Log log;

	@EJB
	private CrudService crudService;

	@Override
	public List<Tipodocumento> getTipodocumentoForPersonaNatural() throws Exception {
		List<Tipodocumento> list = null;
		try{
			list = crudService.findWithNamedQuery(Tipodocumento.AllForPersonaNatural);
		} catch(Exception e){
			
		}
		return list;
	}

	@Override
	public List<Tipodocumento> getTipodocumentoForPersonaJuridica()
			throws Exception {
		List<Tipodocumento> list = null;
		try{
			list = crudService.findWithNamedQuery(Tipodocumento.AllForPersonaJuridica);
		} catch(Exception e){
			
		}
		return list;
	}

}
