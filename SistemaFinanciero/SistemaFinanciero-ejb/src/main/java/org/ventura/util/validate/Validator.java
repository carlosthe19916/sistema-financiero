package org.ventura.util.validate;

import java.util.Iterator;
import java.util.List;

import org.ventura.entity.schema.cuentapersonal.Beneficiario;
import org.ventura.entity.schema.cuentapersonal.Cuentaaporte;
import org.ventura.entity.schema.cuentapersonal.Titular;
import org.ventura.entity.schema.persona.Personanatural;
import org.ventura.entity.schema.socio.Socio;

public class Validator {
	
	/*public static boolean validateCuentacorriente(Cuentacorriente cuentacorriente){
		if(cuentacorriente == null){
			return false;
		}
		if(cuentacorriente.getIdestadocuenta() == null){
			return false;
		}
		if(cuentacorriente.getIdtipomoneda() == null){
			return false;
		}
		if(!validateCuentacorrientehistoriales(cuentacorriente.getCuentacorrientehistorials())){
			return false;
		}
		if(!validateSocio(cuentacorriente.getSocio())){
			return false;
		}
		if(!validateTitulares(cuentacorriente.getTitularcuentas())){
			return false;
		}
		if(!validateBeneficiarios(cuentacorriente.getBeneficiariocuentas())){
			return false;
		}
		return true;
	}
	*/
	/*public static boolean validateCuentaahorro(Cuentaahorro cuentaahorro){
		if(cuentaahorro == null){
			return false;
		}
		if(cuentaahorro.getIdestadocuenta() == null){
			return false;
		}
		if(cuentaahorro.getIdtipomoneda() == null){
			return false;
		}
		if(!validateCuentaahorrohistoriales(cuentaahorro.getCuentaahorrohistorials())){
			return false;
		}
		if(!validateSocio(cuentaahorro.getSocio())){
			return false;
		}
		if(!validateTitulares(cuentaahorro.getTitularcuentas())){
			return false;
		}
		if(!validateBeneficiarios(cuentaahorro.getBeneficiariocuentas())){
			return false;
		}
		return true;
	}
	*/
	/*public static boolean validateCuentaplazofijo(Cuentaplazofijo cuentaplazofijo){
		if(cuentaplazofijo == null){
			return false;
		}
		if(cuentaplazofijo.getIdestadocuenta() == null){
			return false;
		}
		if(cuentaplazofijo.getIdtipomoneda() == null){
			return false;
		}
		
		if(!validateSocio(cuentaplazofijo.getSocio())){
			return false;
		}
		if(!validateTitulares(cuentaplazofijo.getTitularcuentas())){
			return false;
		}
		if(!validateBeneficiarios(cuentaplazofijo.getBeneficiariocuentas())){
			return false;
		}
		return true;
	}
	*/
	/*public static boolean validateCuentaaporte(Cuentaaporte cuentaaporte){
		if(cuentaaporte == null){
			return false;
		}
		if(cuentaaporte.getIdestadocuenta() == null){
			return false;
		}
		if(cuentaaporte.getIdtipomoneda() == null){
			return false;
		}
		if(!validateBeneficiarios(cuentaaporte.getBeneficiarios())){
			return false;
		}
		return true;
	}
	
	public static boolean validateSocio(Socio socio){
		if(socio == null){
			return false;
		}
		if(socio.getDni() == null && socio.getRuc() == null){
			return false;
		}
		if(socio.getDni() != null && socio.getRuc() != null){
			return false;
		}
		return true;
	}
	
	
	
	public static boolean validateCuentaahorrohistoriales(List<Cuentaahorrohistorial> cuentaahorrohistoriales){
		if(cuentaahorrohistoriales == null){
			return false;
		}
		for (Iterator<Cuentaahorrohistorial> iterator = cuentaahorrohistoriales.iterator(); iterator.hasNext();) {
			Cuentaahorrohistorial cuentaahorrohistorial = iterator.next();
			boolean resutl = validateCuentaahorrohistorial(cuentaahorrohistorial);
			if(resutl == false){
				return false;
			}
		}	
		return true;
	}
	
	public static boolean validateCuentacorrientehistoriales(List<Cuentacorrientehistorial> cuentacorrientehistorials){
		if(cuentacorrientehistorials == null){
			return false;
		}
		for (Iterator<Cuentacorrientehistorial> iterator = cuentacorrientehistorials.iterator(); iterator.hasNext();) {
			Cuentacorrientehistorial cuentaahorrohistorial = iterator.next();
			boolean resutl = validateCuentacorrientehistorial(cuentaahorrohistorial);
			if(resutl == false){
				return false;
			}
		}	
		return true;
	}
	
	public static boolean validateCuentaahorrohistorial(Cuentaahorrohistorial cuentaahorrohistorial){
		if(cuentaahorrohistorial == null){
			return false;
		}
		if(cuentaahorrohistorial.getEstado() == null){
			return false;
		}
		if(cuentaahorrohistorial.getCantidadretirantes() == null){
			return false;
		}
		if(cuentaahorrohistorial.getTasainteres() == null) {
			return false;
		}
		if(cuentaahorrohistorial.getCuentaahorro() == null){
			return false;
		}
		return true;
	}
	
	public static boolean validateCuentacorrientehistorial(Cuentacorrientehistorial cuentacorrientehistorial){
		if(cuentacorrientehistorial == null){
			return false;
		}
		if(cuentacorrientehistorial.getEstado() == null){
			return false;
		}
		if(cuentacorrientehistorial.getCantidadretirantes() == null){
			return false;
		}
		if(cuentacorrientehistorial.getTasainteres() == null) {
			return false;
		}
		if(cuentacorrientehistorial.getCuentacorriente() == null){
			return false;
		}
		return true;
	}
	
	public static boolean validateBeneficiario(Beneficiario beneficiariocuenta) {
		if(beneficiariocuenta == null){
			return false;
		}		
		if (beneficiariocuenta.getApellidopaterno() == null
				|| beneficiariocuenta.getApellidopaterno().isEmpty()
				|| beneficiariocuenta.getApellidopaterno().trim().isEmpty()) {
			return false;
		}
		if (beneficiariocuenta.getApellidomaterno() == null
				|| beneficiariocuenta.getApellidomaterno().isEmpty()
				|| beneficiariocuenta.getApellidomaterno().trim().isEmpty()) {
			return false;
		}
		if (beneficiariocuenta.getNombres() == null
				|| beneficiariocuenta.getNombres().isEmpty()
				|| beneficiariocuenta.getNombres().trim().isEmpty()) {
			return false;
		}
		if (beneficiariocuenta.getPorcentajebeneficio() == null) {
			return false;
		}
		return true;
	}

	public static boolean validateTitular(Titular titularcuenta) {
		if(titularcuenta == null) {
			return false;
		}
		if(titularcuenta.getPersonanatural() == null){
			return false;
		}
		if(titularcuenta.getCuentaahorro() == null && titularcuenta.getCuentacorriente() == null && titularcuenta.getCuentaplazofijo() == null){
			return false;
		}
		//validar cantidad de cuentas en el titular
		int numeroCuentasactivas = 0;
		numeroCuentasactivas = titularcuenta.getCuentaahorro() != null ? numeroCuentasactivas + 1 : numeroCuentasactivas;
		numeroCuentasactivas = titularcuenta.getCuentacorriente() != null ? numeroCuentasactivas + 1 : numeroCuentasactivas;
		numeroCuentasactivas = titularcuenta.getCuentaplazofijo() != null ? numeroCuentasactivas + 1 : numeroCuentasactivas;
		if(numeroCuentasactivas > 1){
			return false;
		}
		
		//validar personanatural
		Personanatural personanatural = titularcuenta.getPersonanatural();
		if (!personanatural.isValid()) {
			return false;
		}
	
		//validar historiales del titular
		List<Titularcuentahistorial> titularHistorial = titularcuenta.getTitularhitorial();
		if (validateTitularhistoriales(titularHistorial)) {
			if(titularHistorial.size() == 0){
				return false;
			} 
		} else {
			return false;
		}
	
		return true;
	}
	
	public static boolean validateTitularhistorial(Titularcuentahistorial titularcuentahistorial){
		if(titularcuentahistorial.getTitularcuenta() == null){
			return false;
		}
		if(titularcuentahistorial.getEstado() == null){
			return false;
		}
		if(titularcuentahistorial.getFechaactiva() == null){
			return false;
		}
		return true;
	}
	
	public static boolean validateTitularhistoriales(List<Titularcuentahistorial> titularcuentahistoriales){
		if(titularcuentahistoriales == null){
			return false;
		}	
		for (Iterator<Titularcuentahistorial> iterator = titularcuentahistoriales.iterator(); iterator.hasNext();) {
			Titularcuentahistorial titularcuentahistorial = iterator.next();
			boolean resutl = validateTitularhistorial(titularcuentahistorial);
			if(resutl == false){
				return false;
			}
		}	
		return true;
	}
	
	public static boolean validateBeneficiarios(List<Beneficiario> beneficiarios) {
		if(beneficiarios == null) {
			return false;
		} 	
		for (Iterator<Beneficiario> iterator = beneficiarios.iterator(); iterator.hasNext();) {
			Beneficiario beneficiariocuenta = iterator.next();
			boolean result = validateBeneficiario(beneficiariocuenta);
			if (result == false) {
				return false;
			}
		}	
		return true;
	}
	
	public static boolean validateTitulares(List<Titular> titulares) {
		if(titulares == null){
			return false;
		}
		for (Iterator<Titular> iterator = titulares.iterator(); iterator.hasNext();) {
			Titular titular = iterator.next();
			boolean result = validateTitular(titular);
			if (result == false) {
				return false;
			}
		}	
		return true;
	}*/
}
