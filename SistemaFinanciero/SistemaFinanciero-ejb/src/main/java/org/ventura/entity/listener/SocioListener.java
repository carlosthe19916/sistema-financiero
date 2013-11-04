package org.ventura.entity.listener;

import javax.persistence.PostPersist;
import javax.persistence.PrePersist;

import org.ventura.entity.schema.socio.Socio;
import org.ventura.util.exception.IllegalEntityException;

public class SocioListener {

	@PrePersist
	private void prePersist(Socio socio){
	}
	
	@PostPersist 
	private void postPersist(Socio socio) throws IllegalEntityException, Exception {
		String numeroCuentaaporte = "";

		String codigoAgencia = socio.getAgencia().getCodigoagencia();
		String codigoCuentaaporte = socio.getCuentaaporte().getIdcuentaaporte()
				.toString();
		String codigoTipomoneda = socio.getCuentaaporte().getTipomoneda()
				.getIdtipomoneda().toString();
		String codigoTipoCuenta = "10";

		for (int i = codigoCuentaaporte.length(); i < 8; i++) {
			codigoCuentaaporte = "0" + codigoCuentaaporte;
		}

		if (codigoAgencia != null && codigoCuentaaporte != null
				&& codigoTipomoneda != null && codigoTipoCuenta != null) {
			numeroCuentaaporte = codigoAgencia + codigoCuentaaporte
					+ codigoTipomoneda + codigoTipoCuenta;
		} else {
			throw new IllegalEntityException("Error: no se pudo generar el numero de cuenta");
		}

		socio.getCuentaaporte().setNumerocuentaaporte(numeroCuentaaporte);
	}
}
