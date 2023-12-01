
package Presentacion.Command.Venta;

import Negocio.ASFactory.ASFactory;
import Negocio.Venta.ASVenta;
import Negocio.Venta.TVenta;
import Negocio.Venta.TVentaEnDetalle;
import Presentacion.Command.Command;
import Presentacion.Command.ContextEnum;
import Presentacion.Command.EventEnum;
import Presentacion.Controller.Context;


public class MostrarVentaEnDetalleCommand implements Command {
	
	public Context execute(Object input) {
		ASVenta asventa = ASFactory.getInstance().GetASVenta();
		Object output = asventa.mostrarEnDetalle((Integer)input);
		return new Context(ContextEnum.MOSTRARVENTAENDETALLE, event(output));
	}

	private Object event(Object output) {
		switch(((TVentaEnDetalle)output).getTVenta().getID()) {
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