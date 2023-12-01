
package Presentacion.Command.Venta;

import java.util.ArrayList;

import Negocio.ASFactory.ASFactory;
import Negocio.Venta.ASVenta;
import Negocio.Venta.TVenta;
import Presentacion.Command.Command;
import Presentacion.Command.ContextEnum;
import Presentacion.Command.EventEnum;
import Presentacion.Controller.Context;


public class ListarVentasPorClienteCommand implements Command {

	public Context execute(Object input) {
		ASVenta asventa = ASFactory.getInstance().GetASVenta();
		Object output = asventa.listarPorCliente((Integer)input);
		return new Context(ContextEnum.LISTARVENTASPORCLIENTE, event(output));
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
			case -11:
				return EventEnum.CLIENTEINEXISTENTE;
			case -12:
				return EventEnum.CLIENTEINACTIVO;
			default: // no error
				return output;
			}
		}
	}
	
}