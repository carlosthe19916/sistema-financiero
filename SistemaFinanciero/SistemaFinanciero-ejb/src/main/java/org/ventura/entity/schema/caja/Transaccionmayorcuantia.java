package org.ventura.entity.schema.caja;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * The persistent class for the transasccionmayorcuantia database table.
 * 
 */
@Entity
@Table(name = "transasccionmayorcuantia", schema = "caja")
@NamedQuery(name = "Transaccionmayorcuantia.findAll", query = "SELECT t FROM Transaccionmayorcuantia t")
public class Transaccionmayorcuantia implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private Integer idtransaccionmayorcuantia;

	@Column(name = "apellidos_nombres_solicitante", length = 150)
	private String apellidosNombresSolicitante;

	@Column(name = "apellidosnombres_razonsocial_beneficiario", length = 120)
	private String apellidosnombresRazonsocialBeneficiario;

	@Column(name = "apellidosnombres_razonsocial_ordenante", length = 120)
	private String apellidosnombresRazonsocialOrdenante;

	@Column(name = "direccion_beneficiario", length = 100)
	private String direccionBeneficiario;

	@Column(name = "direccion_ordenante", length = 100)
	private String direccionOrdenante;

	@Column(name = "direccion_solicitante", length = 100)
	private String direccionSolicitante;

	@Temporal(TemporalType.DATE)
	@Column(name = "fecha_nacimiento_solicitante")
	private Date fechaNacimientoSolicitante;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_transaccion")
	private Date fechaTransaccion;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "fechanacimiento_constitucion_beneficiario")
	private Date fechanacimientoConstitucionBeneficiario;

	@Temporal(TemporalType.DATE)
	@Column(name = "fechanacimiento_constitucion_ordenante")
	private Date fechanacimientoConstitucionOrdenante;

	@Column(name = "idtipodocumento_beneficiario")
	private Integer idtipodocumentoBeneficiario;

	@Column(name = "idtipodocumento_ordenante")
	private Integer idtipodocumentoOrdenante;

	@Column(name = "idtipodocumento_solicitante", nullable = false)
	private Integer idtipodocumentoSolicitante;

	@Column(nullable = false)
	private Integer idtipomoneda;

	@Column(nullable = false)
	private String tipotransaccion;

	@Column(nullable = false, precision = 18, scale = 2)
	private BigDecimal importe;

	@Column(name = "nacionalidad_beneficiario", length = 60)
	private String nacionalidadBeneficiario;

	@Column(name = "nacionalidad_ordenante", length = 80)
	private String nacionalidadOrdenante;

	@Column(name = "nacionalidad_solicitante", length = 60)
	private String nacionalidadSolicitante;

	@Column(nullable = false, length = 30)
	private String numerocuenta;

	@Column(name = "numerodocumento_beneficiario", length = 15)
	private String numerodocumentoBeneficiario;

	@Column(name = "numerodocumento_ordenante", length = 15)
	private String numerodocumentoOrdenante;

	@Column(name = "numerodocumento_solicitante", nullable = false, length = 15)
	private String numerodocumentoSolicitante;

	@Column(name = "ocupacion_actividadeconomica_beneficiario", length = 80)
	private String ocupacionActividadeconomicaBeneficiario;

	@Column(name = "ocupacion_actividadeconomica_ordenante", length = 80)
	private String ocupacionActividadeconomicaOrdenante;

	@Column(name = "ocupacion_solicitante", length = 80)
	private String ocupacionSolicitante;

	@Column(length = 3001)
	private String origenimporte;

	@Column(length = 300)
	private String propositoimporte;

	@Column(name = "telefono_beneficiario", length = 20)
	private String telefonoBeneficiario;

	@Column(name = "telefono_ordenante", length = 20)
	private String telefonoOrdenante;

	@Column(name = "telefono_solicitante", length = 20)
	private String telefonoSolicitante;

	@Column(name = "ubigeo_beneficiario", length = 6)
	private String ubigeoBeneficiario;

	@Column(name = "ubigeo_solicitante", length = 6)
	private String ubigeoSolicitante;

	@Column(name = "ugibeo_ordenante", length = 6)
	private String ugibeoOrdenante;

	public Transaccionmayorcuantia() {
	}

	public Integer getIdtransaccionmayorcuantia() {
		return this.idtransaccionmayorcuantia;
	}

	public void setIdtransaccionmayorcuantia(Integer idtransaccionmayorcuantia) {
		this.idtransaccionmayorcuantia = idtransaccionmayorcuantia;
	}

	public String getApellidosNombresSolicitante() {
		return this.apellidosNombresSolicitante;
	}

	public void setApellidosNombresSolicitante(
			String apellidosNombresSolicitante) {
		this.apellidosNombresSolicitante = apellidosNombresSolicitante;
	}

	public String getApellidosnombresRazonsocialBeneficiario() {
		return this.apellidosnombresRazonsocialBeneficiario;
	}

	public void setApellidosnombresRazonsocialBeneficiario(
			String apellidosnombresRazonsocialBeneficiario) {
		this.apellidosnombresRazonsocialBeneficiario = apellidosnombresRazonsocialBeneficiario;
	}

	public String getApellidosnombresRazonsocialOrdenante() {
		return this.apellidosnombresRazonsocialOrdenante;
	}

	public void setApellidosnombresRazonsocialOrdenante(
			String apellidosnombresRazonsocialOrdenante) {
		this.apellidosnombresRazonsocialOrdenante = apellidosnombresRazonsocialOrdenante;
	}

	public String getDireccionBeneficiario() {
		return this.direccionBeneficiario;
	}

	public void setDireccionBeneficiario(String direccionBeneficiario) {
		this.direccionBeneficiario = direccionBeneficiario;
	}

	public String getDireccionOrdenante() {
		return this.direccionOrdenante;
	}

	public void setDireccionOrdenante(String direccionOrdenante) {
		this.direccionOrdenante = direccionOrdenante;
	}

	public String getDireccionSolicitante() {
		return this.direccionSolicitante;
	}

	public void setDireccionSolicitante(String direccionSolicitante) {
		this.direccionSolicitante = direccionSolicitante;
	}

	public Date getFechaNacimientoSolicitante() {
		return this.fechaNacimientoSolicitante;
	}

	public void setFechaNacimientoSolicitante(Date fechaNacimientoSolicitante) {
		this.fechaNacimientoSolicitante = fechaNacimientoSolicitante;
	}

	public Date getFechanacimientoConstitucionBeneficiario() {
		return this.fechanacimientoConstitucionBeneficiario;
	}

	public void setFechanacimientoConstitucionBeneficiario(
			Date fechanacimientoConstitucionBeneficiario) {
		this.fechanacimientoConstitucionBeneficiario = fechanacimientoConstitucionBeneficiario;
	}

	public Date getFechanacimientoConstitucionOrdenante() {
		return this.fechanacimientoConstitucionOrdenante;
	}

	public void setFechanacimientoConstitucionOrdenante(
			Date fechanacimientoConstitucionOrdenante) {
		this.fechanacimientoConstitucionOrdenante = fechanacimientoConstitucionOrdenante;
	}

	public Integer getIdtipodocumentoBeneficiario() {
		return this.idtipodocumentoBeneficiario;
	}

	public void setIdtipodocumentoBeneficiario(
			Integer idtipodocumentoBeneficiario) {
		this.idtipodocumentoBeneficiario = idtipodocumentoBeneficiario;
	}

	public Integer getIdtipodocumentoOrdenante() {
		return this.idtipodocumentoOrdenante;
	}

	public void setIdtipodocumentoOrdenante(Integer idtipodocumentoOrdenante) {
		this.idtipodocumentoOrdenante = idtipodocumentoOrdenante;
	}

	public Integer getIdtipodocumentoSolicitante() {
		return this.idtipodocumentoSolicitante;
	}

	public void setIdtipodocumentoSolicitante(Integer idtipodocumentoSolicitante) {
		this.idtipodocumentoSolicitante = idtipodocumentoSolicitante;
	}

	public Integer getIdtipomoneda() {
		return this.idtipomoneda;
	}

	public void setIdtipomoneda(Integer idtipomoneda) {
		this.idtipomoneda = idtipomoneda;
	}

	public BigDecimal getImporte() {
		return this.importe;
	}

	public void setImporte(BigDecimal importe) {
		this.importe = importe;
	}

	public String getNacionalidadBeneficiario() {
		return this.nacionalidadBeneficiario;
	}

	public void setNacionalidadBeneficiario(String nacionalidadBeneficiario) {
		this.nacionalidadBeneficiario = nacionalidadBeneficiario;
	}

	public String getNacionalidadOrdenante() {
		return this.nacionalidadOrdenante;
	}

	public void setNacionalidadOrdenante(String nacionalidadOrdenante) {
		this.nacionalidadOrdenante = nacionalidadOrdenante;
	}

	public String getNacionalidadSolicitante() {
		return this.nacionalidadSolicitante;
	}

	public void setNacionalidadSolicitante(String nacionalidadSolicitante) {
		this.nacionalidadSolicitante = nacionalidadSolicitante;
	}

	public String getNumerocuenta() {
		return this.numerocuenta;
	}

	public void setNumerocuenta(String numerocuenta) {
		this.numerocuenta = numerocuenta;
	}

	public String getNumerodocumentoBeneficiario() {
		return this.numerodocumentoBeneficiario;
	}

	public void setNumerodocumentoBeneficiario(
			String numerodocumentoBeneficiario) {
		this.numerodocumentoBeneficiario = numerodocumentoBeneficiario;
	}

	public String getNumerodocumentoOrdenante() {
		return this.numerodocumentoOrdenante;
	}

	public void setNumerodocumentoOrdenante(String numerodocumentoOrdenante) {
		this.numerodocumentoOrdenante = numerodocumentoOrdenante;
	}

	public String getNumerodocumentoSolicitante() {
		return this.numerodocumentoSolicitante;
	}

	public void setNumerodocumentoSolicitante(String numerodocumentoSolicitante) {
		this.numerodocumentoSolicitante = numerodocumentoSolicitante;
	}

	public String getOcupacionActividadeconomicaBeneficiario() {
		return this.ocupacionActividadeconomicaBeneficiario;
	}

	public void setOcupacionActividadeconomicaBeneficiario(
			String ocupacionActividadeconomicaBeneficiario) {
		this.ocupacionActividadeconomicaBeneficiario = ocupacionActividadeconomicaBeneficiario;
	}

	public String getOcupacionActividadeconomicaOrdenante() {
		return this.ocupacionActividadeconomicaOrdenante;
	}

	public void setOcupacionActividadeconomicaOrdenante(
			String ocupacionActividadeconomicaOrdenante) {
		this.ocupacionActividadeconomicaOrdenante = ocupacionActividadeconomicaOrdenante;
	}

	public String getOcupacionSolicitante() {
		return this.ocupacionSolicitante;
	}

	public void setOcupacionSolicitante(String ocupacionSolicitante) {
		this.ocupacionSolicitante = ocupacionSolicitante;
	}

	public String getOrigenimporte() {
		return this.origenimporte;
	}

	public void setOrigenimporte(String origenimporte) {
		this.origenimporte = origenimporte;
	}

	public String getPropositoimporte() {
		return this.propositoimporte;
	}

	public void setPropositoimporte(String propositoimporte) {
		this.propositoimporte = propositoimporte;
	}

	public String getTelefonoBeneficiario() {
		return this.telefonoBeneficiario;
	}

	public void setTelefonoBeneficiario(String telefonoBeneficiario) {
		this.telefonoBeneficiario = telefonoBeneficiario;
	}

	public String getTelefonoOrdenante() {
		return this.telefonoOrdenante;
	}

	public void setTelefonoOrdenante(String telefonoOrdenante) {
		this.telefonoOrdenante = telefonoOrdenante;
	}

	public String getTelefonoSolicitante() {
		return this.telefonoSolicitante;
	}

	public void setTelefonoSolicitante(String telefonoSolicitante) {
		this.telefonoSolicitante = telefonoSolicitante;
	}

	public String getUbigeoBeneficiario() {
		return this.ubigeoBeneficiario;
	}

	public void setUbigeoBeneficiario(String ubigeoBeneficiario) {
		this.ubigeoBeneficiario = ubigeoBeneficiario;
	}

	public String getUbigeoSolicitante() {
		return this.ubigeoSolicitante;
	}

	public void setUbigeoSolicitante(String ubigeoSolicitante) {
		this.ubigeoSolicitante = ubigeoSolicitante;
	}

	public String getUgibeoOrdenante() {
		return this.ugibeoOrdenante;
	}

	public void setUgibeoOrdenante(String ugibeoOrdenante) {
		this.ugibeoOrdenante = ugibeoOrdenante;
	}

	public Date getFechaTransaccion() {
		return fechaTransaccion;
	}

	public void setFechaTransaccion(Date fechaTransaccion) {
		this.fechaTransaccion = fechaTransaccion;
	}

	public String getTipotransaccion() {
		return tipotransaccion;
	}

	public void setTipotransaccion(String tipotransaccion) {
		this.tipotransaccion = tipotransaccion;
	}

}