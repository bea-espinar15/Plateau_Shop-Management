/**
 * 
 */
package Presentacion.Command.Venta;

import java.util.ArrayList;

import Negocio.ASFactory.ASFactory;
import Negocio.Venta.ASVenta;
import Presentacion.Command.Command;
import Presentacion.Command.ContextEnum;
import Presentacion.Command.EventEnum;
import Presentacion.Controller.Context;


public class DevolverVentaCommand implements Command {

	@SuppressWarnings("unchecked")
	public Context execute(Object input) {
		ASVenta asventa = ASFactory.getInstance().GetASVenta();
		Object output = asventa.devolver(((ArrayList<Integer>)input).get(0), ((ArrayList<Integer>)input).get(1), ((ArrayList<Integer>)input).get(2));	
		return new Context(ContextEnum.DEVOLVERVENTA, event(output));
	}

	private Object event(Object output) {
		switch((Integer)output) {
		case -1:
			return EventEnum.BASEDEDATOS;
		case -2:
			return EventEnum.CONCURRENCIA;
		case -4:
			return EventEnum.ENTIDADINEXISTENTE;
		case -8:
			return EventEnum.PRODUCTONOINCLUIDO;
		case -9:
			return EventEnum.PRODUCTOINEXISTENTE;
		default: // no error
			return output;
		}
	}
	
}