package org.ventura.tipodato;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

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
		if (value.contains(",")) {
			String r = "";
			for (int i = 0; i < value.length(); i++) {
				if (value.charAt(i) != ',')
					r += value.charAt(i);
			}
			value = r;
		}
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

	public Moneda multiply(BigDecimal value) {
		Moneda moneda = new Moneda(value);
		BigDecimal result = this.value.multiply(moneda.getValue());
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
	
	public Moneda multiply(TasaCambio value) {
		TasaCambio tasacambio = new TasaCambio(value);
		BigDecimal result = this.value.multiply(tasacambio.getValue());
		result = result.setScale(2, RoundingMode.HALF_UP);
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

	public int getDecimalValue() {
		BigDecimal d = value;
		BigDecimal result = d.subtract(d.setScale(0, RoundingMode.FLOOR))
				.movePointRight(d.scale());
		return result.intValue();
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
		/*
		 * String s =
		 * NumberFormat.getCurrencyInstance().format(value).toString(); s =
		 * s.substring(0, s.length() - 1); return s;
		 */

		String stringValue = "";

		if (value == null)
			return null;
		int realScale = value.scale();
		
		if (realScale == 2) {
			int doubleValue = getDecimalValue();

			if (doubleValue % 10 == 0) {
				stringValue = "." + doubleValue + stringValue;
			} else {
				stringValue = "." + doubleValue + stringValue;
			}

			int intValue = getIntValue();
			if (intValue == 0) {
				stringValue = "0" + stringValue;
			}

			int contador = 0;
			while (intValue != 0) {
				int q = intValue / 10;
				int r = intValue % 10;
				intValue = q;
				stringValue = r + stringValue;
				contador++;
				if (contador == 3 && intValue != 0) {
					stringValue = "," + stringValue;
					contador = 0;
				}
			}
			return stringValue;
		} else {
			if (realScale == 1) {
				int doubleValue = getDecimalValue();

				stringValue = "." + doubleValue + stringValue + "0";

				int intValue = getIntValue();
				if (intValue == 0) {
					stringValue = "0" + stringValue;
				}
				
				int contador = 0;
				while (intValue != 0) {
					int q = intValue / 10;
					int r = intValue % 10;
					intValue = q;
					stringValue = r + stringValue;
					contador++;
					if (contador == 3 && intValue != 0) {
						stringValue = "," + stringValue;
						contador = 0;
					}
				}
				return stringValue;
			} else {
				if (realScale == 0) {
					stringValue = "." + stringValue + "00";

					int intValue = getIntValue();
					if (intValue == 0) {
						stringValue = "0" + stringValue;
					}
					
					int contador = 0;
					while (intValue != 0) {
						int q = intValue / 10;
						int r = intValue % 10;
						intValue = q;
						stringValue = r + stringValue;
						contador++;
						if (contador == 3 && intValue != 0) {
							stringValue = "," + stringValue;
							contador = 0;
						}
					}
					return stringValue;
				} else {
					throw new RuntimeException(
							"Scale of Money object is > 2, should never happen, Money object is faulty.");
				}
			}
		}
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
