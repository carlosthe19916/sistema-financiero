package org.ventura.util.helper;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.ventura.tipodato.Moneda;


@Embeddable
public class TasaInteres implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "value")
	private BigDecimal value;

	private final static int SCALE = 3;

	public TasaInteres() {
		this.value = BigDecimal.ZERO;
	}

	public TasaInteres(double value) {
		this.value = new BigDecimal(value);
		if (this.value.scale() > SCALE)
			throw new IllegalArgumentException("Money can't have scale > 3");
		this.value.setScale(SCALE);
	}

	public TasaInteres(long value) {
		this.value = new BigDecimal(value);
	}

	public TasaInteres(long value, int scale) {
		this.value = new BigDecimal(value);
		this.value.setScale(scale);
	}

	public TasaInteres(String value) {
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
			throw new IllegalArgumentException("Money can't have scale > 3");
		this.value.setScale(SCALE);
	}

	public TasaInteres(BigDecimal value) {
		this(value.toString());
	}

	public TasaInteres(TasaInteres tasainterestipocambio) {
		this(tasainterestipocambio.getValue().toString());
	}

	public TasaInteres add(TasaInteres tasainterestipocambio) {
		BigDecimal result = this.value.add(tasainterestipocambio.getValue());
		return new TasaInteres(result);
	}

	public TasaInteres subtract(TasaInteres tasainterestipocambio) {
		BigDecimal result = this.value.subtract(tasainterestipocambio.getValue());
		return new TasaInteres(result);
	}

	public TasaInteres multiply(double value) {
		TasaInteres tasainterestipocambio = new TasaInteres(value);
		BigDecimal result = this.value.multiply(tasainterestipocambio.getValue());
		return new TasaInteres(result);
	}

	public TasaInteres multiply(long value) {
		TasaInteres tasainterestipocambio = new TasaInteres(value);
		BigDecimal result = this.value.multiply(tasainterestipocambio.getValue());
		return new TasaInteres(result);
	}

	public TasaInteres multiply(int value) {
		TasaInteres tasainterestipocambio = new TasaInteres(value);
		BigDecimal result = this.value.multiply(tasainterestipocambio.getValue());
		return new TasaInteres(result);
	}
	
	public Moneda multiply(Moneda value) {
		Moneda moneda = new Moneda(value);
		BigDecimal result = this.value.multiply(moneda.getValue());
		result = result.setScale(2, RoundingMode.HALF_UP);
		return new Moneda(result);
	}

	public TasaInteres divide(int value) {
		TasaInteres a = new TasaInteres(value);
		BigDecimal result = this.value.divide(a.getValue());
		return new TasaInteres(result);
	}
	
	public BigDecimal divide(double value) {
		TasaInteres tasainterestipocambio = new TasaInteres(value);
		return this.value.divide(tasainterestipocambio.getValue());
	}

	public BigDecimal divide(long value) {
		TasaInteres tasainterestipocambio = new TasaInteres(value);
		return this.value.divide(tasainterestipocambio.getValue());
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

	public boolean isEqual(TasaInteres tasainterestipocambio) {
		return value.compareTo(tasainterestipocambio.getValue()) == 0;
	}

	public boolean isLessThan(TasaInteres tasainterestipocambio) {
		return value.compareTo(tasainterestipocambio.getValue()) < 0;
	}

	public boolean isLessThanOrEqual(TasaInteres tasainterestipocambio) {
		return this.value.compareTo(tasainterestipocambio.getValue()) <= 0;
	}

	public boolean isGreaterThan(TasaInteres tasainterestipocambio) {
		return this.value.compareTo(tasainterestipocambio.getValue()) > 0;
	}

	public boolean isGreaterThanOrEqual(TasaInteres tasainterestipocambio) {
		return this.value.compareTo(tasainterestipocambio.getValue()) >= 0;
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
		
		if (realScale == 3) {
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
		}else {
			if (realScale == 2) {
				int doubleValue = getDecimalValue();

				if (doubleValue % 10 == 0) {
					stringValue = "." + doubleValue + stringValue + "0";
				} else {
					stringValue = "." + doubleValue + stringValue + "0";
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

					stringValue = "." + doubleValue + stringValue + "00";

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
						stringValue = "." + stringValue + "000";

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
								"Scale of Money object is > 3, should never happen, Money object is faulty.");
					}
				}
			}
		}
	}

	public int compareTo(TasaInteres val) {
		return value.compareTo(val.getValue());
	}

	public int compareTo(Object o) {
		return compareTo((TasaInteres) o);
	}

	@Override
	public boolean equals(Object obj) {
		if ((obj == null) || !(obj instanceof TasaInteres)) {
			return false;
		}
		final TasaInteres other = (TasaInteres) obj;
		return (value.compareTo(other.getValue()) == 0);
	}
}
