package org.ventura.util.maestro;

public class TipoTransaccionValue {

	public static TipoTransaccionType getTransaccionType(Integer value)
			throws Exception {
		switch (value) {
		case 1:
			return TipoTransaccionType.DEPOSITO;
		case 2:
			return TipoTransaccionType.RETIRO;
		case 3:
			return TipoTransaccionType.TRANSFERENCIA;
		default:
			throw new Exception();
		}
	}

	public static Integer getTipoTransaccionValue(
			TipoTransaccionType tipoTransaccionType) throws Exception {
		switch (tipoTransaccionType) {
		case DEPOSITO:
			return 1;
		case RETIRO:
			return 2;
		case TRANSFERENCIA:
			return 3;
		default:
			throw new Exception();
		}
	}
}
