
package Presentacion.Command.Proveedor;

import Negocio.ASFactory.ASFactory;
import Negocio.Proveedor.ASProveedor;
import Negocio.Proveedor.TProveedor;
import Presentacion.Command.Command;
import Presentacion.Command.ContextEnum;
import Presentacion.Command.EventEnum;
import Presentacion.Controller.Context;

public class ModificarProveedorCommand implements Command {

	public Context execute(Object input) {
		ASProveedor asProveedor = ASFactory.getInstance().GetASProveedor();
		Object output = asProveedor.modificar((TProveedor) input);
		return new Context(ContextEnum.MODIFICARPROVEEDOR, event(output));
	}

	private Object event(Object output) {
		switch ((Integer) output) {
		case -1:
			return EventEnum.BASEDEDATOS;
		case -2:
			return EventEnum.CONCURRENCIA;
		case -4:
			return EventEnum.ENTIDADINEXISTENTE;
		case -5:
			return EventEnum.ENTIDADINACTIVA;
		case -6:
			return EventEnum.ENTIDADREPETIDA;
		default:
			return output;
		}
	}
	
}