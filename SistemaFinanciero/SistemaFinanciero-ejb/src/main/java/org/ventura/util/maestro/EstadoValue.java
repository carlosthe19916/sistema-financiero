package org.ventura.util.maestro;

public class EstadoValue {

	public static EstadoMovimientoType getEstadoType(Integer value)
			throws Exception {
		switch (value) {
		case 1:
			return EstadoMovimientoType.ABIERTO_CONGELADO;
		case 2:
			return EstadoMovimientoType.ABIERTO_DESCONGELADO;
		case 3:
			return EstadoMovimientoType.CERRADO;
		default:
			throw new Exception();
		}
	}

	public static Integer getEstadoMovimientoValue(
			EstadoMovimientoType estadoMovimientoType) throws Exception {
		switch (estadoMovimientoType) {
		case ABIERTO_CONGELADO:
			return 1;
		case ABIERTO_DESCONGELADO:
			return 2;
		case CERRADO:
			return 3;
		default:
			throw new Exception();
		}
	}

}
