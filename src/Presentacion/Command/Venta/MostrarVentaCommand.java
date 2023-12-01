
package Presentacion.Command.Venta;

import Negocio.ASFactory.ASFactory;
import Negocio.Venta.ASVenta;
import Negocio.Venta.TVenta;
import Presentacion.Command.Command;
import Presentacion.Command.ContextEnum;
import Presentacion.Command.EventEnum;
import Presentacion.Controller.Context;


public class MostrarVentaCommand implements Command {

	public Context execute(Object input) {
		ASVenta asventa = ASFactory.getInstance().GetASVenta();
		Object output = asventa.mostrar((Integer)input);
		return new Context(ContextEnum.MOSTRARVENTA, event(output));
	}

	private Object event(Object output) {
		switch(((TVenta)output).getID()) {
		case -1:
			return EventEnum.BASEDEDATOS;
		case -2:
			return EventEnum.CONCURRENCIA;
		case -4:
			return EventEnum.ENTIDADINEXISTENTE;
		default: // no error
			return output;
		}
	}
	
}