
package Presentacion.Command.Venta;

import java.util.ArrayList;

import Negocio.ASFactory.ASFactory;
import Negocio.Venta.ASVenta;
import Negocio.Venta.TVenta;
import Presentacion.Command.Command;
import Presentacion.Command.ContextEnum;
import Presentacion.Command.EventEnum;
import Presentacion.Controller.Context;


public class ListarVentasPorProductoCommand implements Command {
	
	public Context execute(Object input) {
		ASVenta asventa = ASFactory.getInstance().GetASVenta();
		Object output = asventa.listarPorProducto((Integer)input);
		return new Context(ContextEnum.LISTARVENTASPORPRODUCTO, event(output));
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
			case -9:
				return EventEnum.PRODUCTOINEXISTENTE;
			case -10:
				return EventEnum.PRODUCTOINACTIVO;
			default: // no error
				return output;
			}
		}
	}
	
}