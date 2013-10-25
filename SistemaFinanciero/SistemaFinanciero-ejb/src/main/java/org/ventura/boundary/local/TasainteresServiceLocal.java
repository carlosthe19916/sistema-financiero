package org.ventura.boundary.local;

import java.util.List;

import javax.ejb.Local;

import org.ventura.boundary.remote.TasainteresServiceRemote;
import org.ventura.entity.TiposervicioType;
import org.ventura.entity.TipotasaActivaType;
import org.ventura.entity.TipotasaPasivaType;
import org.ventura.entity.tasas.Tasainteres;
import org.ventura.entity.tasas.Tiposervicio;

@Local
public interface TasainteresServiceLocal extends TasainteresServiceRemote{
	
	public Double getTasainteres(TiposervicioType tiposervicioType, TipotasaActivaType tipotasa, Double monto) throws Exception;
	
	public Double getTasainteres(TiposervicioType tiposervicioType, TipotasaPasivaType tipotasa, Double monto) throws Exception;
	
}
