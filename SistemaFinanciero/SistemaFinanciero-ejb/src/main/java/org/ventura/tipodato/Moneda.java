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
		if(value.contains(" ")){
			String r = "";
			for (int i = 0; i < value.length(); i++) {
				if (value.charAt(i) != ' ')
					r += value.charAt(i);
			}
			value = r;
		}
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

		String result = "";
		String resultIntValue="";
		String resultDecimalValue="";
		int intValue = value.intValue();	
		int decimalValue =  getDecimalValue();
		
		
		if(intValue == 0){
			resultIntValue = "0";
		}
		
		//string para parte entera
		int contador = 0; //cuenta 3 digitos a la derecha
		while(intValue != 0){
			contador++;
			resultIntValue = ( intValue % 10 ) + resultIntValue;
			intValue = intValue / 10;
			
			if(contador == 3 && intValue != 0){
				contador = 0;
				resultIntValue = " " + resultIntValue;
			}
		}
		
		
		//string para parte decimal
		if(value.scale() == 2){
			if(decimalValue>10){
				resultDecimalValue = decimalValue+"";
			} else {
				resultDecimalValue = "0"+decimalValue;
			}
		}
		if(value.scale() == 1){
			resultDecimalValue = decimalValue+"0";
		}
		if(value.scale() == 0){
			resultDecimalValue = "00";
		}
			
		return resultIntValue+"."+resultDecimalValue;
		
	}
	
	public static String getMonedaFormat(BigDecimal number){
		String result = "";
		String resultIntValue="";
		String resultDecimalValue="";
		int intValue = number.intValue();	
		int decimalValue =  number.subtract(number.setScale(0, RoundingMode.FLOOR)).movePointRight(number.scale()).intValue();
		
		if(intValue == 0){
			resultIntValue = "0";
		}
		
		//string para parte entera
		int contador = 0; //cuenta 3 digitos a la derecha
		while(intValue != 0){
			contador++;
			resultIntValue = ( intValue % 10 ) + resultIntValue;
			intValue = intValue / 10;
			
			if(contador == 3 && intValue != 0){
				contador = 0;
				resultIntValue = " " + resultIntValue;
			}
		}
		
		
		//string para parte decimal
		if(number.scale() == 2){
			if(decimalValue>10){
				resultDecimalValue = decimalValue+"";
			} else {
				resultDecimalValue = "0"+decimalValue;
			}
		}
		if(number.scale() == 1){
			resultDecimalValue = decimalValue+"0";
		}
		if(number.scale() == 0){
			resultDecimalValue = "00";
		}
			
		return resultIntValue+"."+resultDecimalValue;
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
