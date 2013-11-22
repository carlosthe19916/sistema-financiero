package org.ventura.entity.schema.caja;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

/**
 * The persistent class for the detallehistorialboveda database table.
 * 
 */
@Entity
@Table(name = "detallehistorialboveda", schema = "caja")
@NamedQuery(name = "Detallehistorialboveda.findAll", query = "SELECT d FROM Detallehistorialboveda d")
public class Detallehistorialboveda implements Serializable {

	private static final long serialVersionUID = 1L;

	public final static String LAST_ACTIVE_FOR_BOVEDA = "org.ventura.entity.schema.caja.Detallehistorialboveda";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private Integer iddetallehistorialboveda;

	@Column(nullable = false)
	private BigDecimal total;

	@OneToMany(mappedBy = "detallehistorialboveda")
	private List<Detalleaperturacierreboveda> detalleaperturacierrebovedaList;

	public Detallehistorialboveda() {
	}

	public Integer getIddetallehistorialboveda() {
		return iddetallehistorialboveda;
	}

	public void setIddetallehistorialboveda(Integer iddetallehistorialboveda) {
		this.iddetallehistorialboveda = iddetallehistorialboveda;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public List<Detalleaperturacierreboveda> getDetalleaperturacierrebovedaList() {
		return detalleaperturacierrebovedaList;
	}

	public void setDetalleaperturacierrebovedaList(List<Detalleaperturacierreboveda> detalleaperturacierrebovedaList) {
		this.detalleaperturacierrebovedaList = detalleaperturacierrebovedaList;
		this.refreshTotal();
	}

	public Detalleaperturacierreboveda addDetalleaperturacierrebovedaList(Detalleaperturacierreboveda detalleaperturacierreboveda) {
		if (getDetalleaperturacierrebovedaList() == null) {
			setDetalleaperturacierrebovedaList(new ArrayList<Detalleaperturacierreboveda>());
		}
		this.getDetalleaperturacierrebovedaList().add(detalleaperturacierreboveda);
		this.refreshTotal();
		return detalleaperturacierreboveda;
	}

	public Detalleaperturacierreboveda removeDetalleaperturacierrebovedaList(Detalleaperturacierreboveda detalleaperturacierreboveda) {
		this.getDetalleaperturacierrebovedaList().remove(detalleaperturacierreboveda);
		this.refreshTotal();
		return detalleaperturacierreboveda;
	}

	public void refreshTotal() {
		/*List<Detalleaperturacierreboveda> detalleaperturacierrebovedas = getDetalleaperturacierrebovedaList();
		this.total = new Double(0);
		if (detalleaperturacierrebovedas != null) {
			for (Iterator<Detalleaperturacierreboveda> iterator = detalleaperturacierrebovedas.iterator(); iterator.hasNext();) {
				Detalleaperturacierreboveda detalleaperturacierreboveda = iterator.next();
				this.total = total + detalleaperturacierreboveda.getSubtotal();
			}
		}*/
	}

}