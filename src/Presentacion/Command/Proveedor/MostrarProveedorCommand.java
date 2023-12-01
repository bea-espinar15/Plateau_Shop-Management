
package Presentacion.Command.Proveedor;

import Negocio.ASFactory.ASFactory;
import Negocio.Proveedor.ASProveedor;
import Negocio.Proveedor.TProveedor;
import Presentacion.Command.Command;
import Presentacion.Command.ContextEnum;
import Presentacion.Command.EventEnum;
import Presentacion.Controller.Context;

public class MostrarProveedorCommand implements Command {

	public Context execute(Object input) {
		ASProveedor asProveedor = ASFactory.getInstance().GetASProveedor();
		Object output = asProveedor.mostrar((Integer) input);
		return new Context(ContextEnum.MOSTRARPROVEEDOR, event(output));
	}

	private Object event(Object output) {
		Integer id = ((TProveedor) output).getID();
		switch (id) {
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