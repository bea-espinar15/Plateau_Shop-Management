
package Presentacion.Command.Venta;

import java.util.ArrayList;

import Negocio.ASFactory.ASFactory;
import Negocio.Venta.ASVenta;
import Negocio.Venta.TVenta;
import Presentacion.Command.Command;
import Presentacion.Command.ContextEnum;
import Presentacion.Command.EventEnum;
import Presentacion.Controller.Context;

public class ListarVentasCommand implements Command {

	public Context execute(Object input) {
		ASVenta asventa = ASFactory.getInstance().GetASVenta();
		Object output = asventa.listar();
		return new Context(ContextEnum.LISTARVENTAS, event(output));
	}

	@SuppressWarnings("unchecked")
	private Object event(Object output) {
		if (((ArrayList<TVenta>) output).size() == 0)
			return output;
		else {
			switch (((ArrayList<TVenta>) output).get(0).getID()) {
			case -1:
				return EventEnum.BASEDEDATOS;
			case -2:
				return EventEnum.CONCURRENCIA;
			default: // no error
				return output;
			}
		}
	}
	
}