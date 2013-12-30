package org.venturabank.util;

import java.util.Calendar;
import java.util.Date;


public class DateUtil {

	public static String getMonthName(Date date) {
		String result;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int month = calendar.get(Calendar.MONTH);
		switch (month) {
		case 0:
			result = "ENERO";
			break;
		case 1:
			result = "FEBRERO";
			break;
		case 2:
			result = "MARZO";
			break;
		case 3:
			result = "ABRIL";
			break;
		case 4:
			result = "MAYO";
			break;
		case 5:
			result = "JUNIO";
			break;
		case 6:
			result = "JULIO";
			break;
		case 7:
			result = "AGOSTO";
			break;
		case 8:
			result = "SEPTIEMBRE";
			break;
		case 9:
			result = "OCTUBRE";
			break;
		case 10:
			result = "NOVIEMBRE";
			break;
		case 11:
			result = "DICIEMBRE";
			break;
		default:
			result = "";
			break;
		}
		return result;
	}
}
