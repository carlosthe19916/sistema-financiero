package org.ventura.entity.schema.caja;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Moneda implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "value")
	private BigDecimal value;

	private final static int SCALE = 2;

	public Moneda() {
		this.value = BigDecimal.ZERO;
	}

	public Moneda(double value) {
		this.value = new BigDecimal(value);
		if (this.value.scale() > SCALE)
			throw new IllegalArgumentException("Money can't have scale > 2");
		this.value.setScale(SCALE);
	}

	public Moneda(long value) {
		this.value = new BigDecimal(value);
	}

	public Moneda(long value, int scale) {
		this.value = new BigDecimal(value);
		this.value.setScale(scale);
	}

	public Moneda(String value) {
		this.value = new BigDecimal(value);
		if (this.value.scale() > SCALE)
			throw new IllegalArgumentException("Money can't have scale > 2");
		this.value.setScale(SCALE);
	}

	public Moneda(BigDecimal value) {
		this(value.toString());
	}

	public Moneda(Moneda moneda) {
		this(moneda.getValue().toString());
	}

	public Moneda add(Moneda moneda) {
		BigDecimal result = this.value.add(moneda.getValue());
		return new Moneda(result);
	}

	public Moneda subtract(Moneda moneda) {
		BigDecimal result = this.value.subtract(moneda.getValue());
		return new Moneda(result);
	}

	public Moneda multiply(double value) {		
		Moneda moneda = new Moneda(value);
		BigDecimal result = this.value.multiply(moneda.getValue());
		return new Moneda(result);
	}

	public Moneda multiply(long value) {
		Moneda moneda = new Moneda(value);
		BigDecimal result = this.value.multiply(moneda.getValue());
		return new Moneda(result);
	}

	public Moneda multiply(int value) {
		Moneda moneda = new Moneda(value);
		BigDecimal result = this.value.multiply(moneda.getValue());
		return new Moneda(result);
	}

	public BigDecimal divide(double value) {
		Moneda moneda = new Moneda(value);
		return this.value.divide(moneda.getValue());
	}

	public BigDecimal divide(long value) {
		Moneda moneda = new Moneda(value);
		return this.value.divide(moneda.getValue());
	}

	public BigDecimal negate() {
		return this.value.negate();
	}

	public BigDecimal abs() {
		return this.value.abs();
	}

	public boolean isZero() {
		return this.value.equals(BigDecimal.ZERO);
	}

	public boolean isPositive() {
		return true;
	}

	public boolean isNegative() {
		return true;
	}

	public boolean isEqual(Moneda moneda) {
		return value.compareTo(moneda.getValue()) == 0;
	}

	public boolean isLessThan(Moneda moneda) {
		return value.compareTo(moneda.getValue()) < 0;
	}

	public boolean isLessThanOrEqual(Moneda moneda) {
		return this.value.compareTo(moneda.getValue()) <= 0;
	}

	public boolean isGreaterThan(Moneda moneda) {
		return this.value.compareTo(moneda.getValue()) > 0;
	}

	public boolean isGreaterThanOrEqual(Moneda moneda) {
		return this.value.compareTo(moneda.getValue()) >= 0;
	}

	public BigDecimal getValue() {
		return this.value;
	}

	public int getIntValue() {
		return this.value.intValue();
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	@Override
	public int hashCode() {
		return this.value.hashCode();
	}

	@Override
	public String toString() {
		String s = NumberFormat.getCurrencyInstance().format(value).toString();
		s = s.substring(0, s.length() - 1);
		return s;
		/*
		 * if (value == null) return null; int realScale = value.scale(); if
		 * (realScale == 2) return value.toString(); else if (realScale == 1)
		 * return value.toString() + "0"; else if (realScale == 0) return
		 * value.toString() + ".00"; else throw new RuntimeException(
		 * "Scale of Money object is > 2, should never happen, Money object is faulty."
		 * );
		 */
	}

	public int compareTo(Moneda val) {
		return value.compareTo(val.getValue());
	}

	public int compareTo(Object o) {
		return compareTo((Moneda) o);
	}

	@Override
	public boolean equals(Object obj) {
		if ((obj == null) || !(obj instanceof Moneda)) {
			return false;
		}
		final Moneda other = (Moneda) obj;
		return (value.compareTo(other.getValue()) == 0);
	}
}
