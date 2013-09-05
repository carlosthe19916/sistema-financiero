package org.ventura.facade.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.ventura.dao.impl.PersonajuridicaclienteDAO;
import org.ventura.facade.PersonajuridicaclienteFacadeLocal;
import org.ventura.model.Personajuridicacliente;

@Stateless
public class PersonajuridicaclienteFacadeLocalImpl implements PersonajuridicaclienteFacadeLocal {

	@EJB
	private PersonajuridicaclienteDAO oPersonajuridicaclienteDAO;

	@Override
	public Personajuridicacliente create(Personajuridicacliente oPersonajuridicacliente) {
		oPersonajuridicaclienteDAO.create(oPersonajuridicacliente);
		return oPersonajuridicacliente;
	}

	@Override
	public Personajuridicacliente find(Integer id) {		
		return oPersonajuridicaclienteDAO.find(id);
	}

	@Override
	public void delete(Personajuridicacliente oPersonajuridicacliente) {
		oPersonajuridicaclienteDAO.delete(oPersonajuridicacliente);
	}

	@Override
	public Personajuridicacliente update(Personajuridicacliente oPersonajuridicacliente) {		
		return oPersonajuridicaclienteDAO.update(oPersonajuridicacliente);
	}

	@Override
	public Collection<Personajuridicacliente> findByNamedQuery(String queryName) {		
		return oPersonajuridicaclienteDAO.findByNamedQuery(queryName);
	}

	@Override
	public Collection<Personajuridicacliente> findByNamedQuery(String queryName, int resultLimit) {
		return oPersonajuridicaclienteDAO.findByNamedQuery(queryName, resultLimit);
	}

	@Override
	public List<Personajuridicacliente> findByNamedQuery(String Personajuridicacliente, Map<String, Object> parameters) {
		return oPersonajuridicaclienteDAO.findByNamedQuery(Personajuridicacliente, parameters);
	}

	@Override
	public List<Personajuridicacliente> findByNamedQuery(String namedQueryName, Map<String, Object> parameters, int resultLimit) {
		return oPersonajuridicaclienteDAO.findByNamedQuery(namedQueryName, parameters);
	}

}
