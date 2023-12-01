
package Presentacion.Command.Venta;

import java.util.HashMap;

import Negocio.ASFactory.ASFactory;
import Negocio.Venta.ASVenta;
import Negocio.Venta.TLineaVenta;
import Negocio.Venta.TVenta;
import Presentacion.Command.Command;
import Presentacion.Command.ContextEnum;
import Presentacion.Command.EventEnum;
import Presentacion.Controller.Context;
import utilities.Pair;

public class CerrarVentaCommand implements Command {

	@SuppressWarnings("unchecked")
	public Context execute(Object input) {
		ASVenta asventa = ASFactory.getInstance().GetASVenta();
		Object output = asventa.cerrar(((Pair<TVenta, HashMap<Integer, TLineaVenta>>) input).getFirst(), ((Pair<TVenta, HashMap<Integer, TLineaVenta>>) input).getSecond());	
		return new Context(ContextEnum.CERRARVENTA, event(output));
	}

	private Object event(Object output) {
		switch((Integer)output) {
		case -1:
			return EventEnum.BASEDEDATOS;
		case -2:
			return EventEnum.CONCURRENCIA;
		case -7:
			return EventEnum.STOCKINSUFICIENTE;
		case -9:
			return EventEnum.PRODUCTOINEXISTENTE;
		case -10:
			return EventEnum.PRODUCTOINACTIVO;
		case -11:
			return EventEnum.CLIENTEINEXISTENTE;
		case -12:
			return EventEnum.CLIENTEINACTIVO;
		case -13:
			return EventEnum.EMPLEADOINEXISTENTE;
		case -14:
			return EventEnum.EMPLEADOINACTIVO;
		default: // no error
			return output;
		}
	}
	
}