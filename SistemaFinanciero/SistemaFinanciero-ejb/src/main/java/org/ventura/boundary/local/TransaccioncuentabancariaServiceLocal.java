package org.ventura.boundary.local;

import javax.ejb.Local;

import org.ventura.boundary.remote.TasainteresServiceRemote;
import org.ventura.boundary.remote.TransaccioncuentabancariaServiceRemote;
import org.ventura.entity.schema.caja.Caja;
import org.ventura.entity.schema.caja.Transaccioncuentabancaria;
import org.ventura.entity.tasas.Tiposervicio;
import org.ventura.entity.tasas.Tipotasa;

@Local
public interface TransaccioncuentabancariaServiceLocal extends TransaccioncuentabancariaServiceRemote{
	
	public Transaccioncuentabancaria create(Caja caja, Transaccioncuentabancaria transaccioncuentabancaria) throws Exception;
}
