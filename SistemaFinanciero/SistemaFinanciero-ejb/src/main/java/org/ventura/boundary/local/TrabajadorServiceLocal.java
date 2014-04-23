package org.ventura.boundary.local;

import java.util.List;

import javax.ejb.Local;

import org.ventura.boundary.remote.TrabajadorServiceRemote;
import org.ventura.entity.schema.persona.Tipodocumento;
import org.ventura.entity.schema.rrhh.Trabajador;
import org.ventura.entity.schema.sucursal.Agencia;

@Local
public interface TrabajadorServiceLocal extends TrabajadorServiceRemote{
	
	public void create(Trabajador trabajador) throws Exception;
	
	public void update(Trabajador trabajador) throws Exception;
	
	public void delete(Trabajador trabajador) throws Exception;
	
	public List<Trabajador> getTrabajadores(Agencia agencia) throws Exception;

	public List<Trabajador> find(Agencia agencia, Tipodocumento tipodocumento,String valorBusqueda) throws Exception;
	
	public Trabajador find(Object id) throws Exception;
	
	public Trabajador findByPersonanatural(Tipodocumento tipodocumento, String numerodocumento) throws Exception;
}
