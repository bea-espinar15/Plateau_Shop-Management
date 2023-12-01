
package Presentacion.Command.Proveedor;

import Negocio.ASFactory.ASFactory;
import Negocio.Proveedor.ASProveedor;
import Negocio.Proveedor.TProveedor;
import Presentacion.Command.Command;
import Presentacion.Command.ContextEnum;
import Presentacion.Command.EventEnum;
import Presentacion.Controller.Context;

public class AltaProveedorCommand implements Command {

	public Context execute(Object input) {
		ASProveedor asProveedor = ASFactory.getInstance().GetASProveedor();
		Object output = asProveedor.alta((TProveedor) input);
		return new Context(ContextEnum.ALTAPROVEEDOR, event(output));
	}

	private Object event(Object output) {
		switch ((Integer) output) {
		case -1:
			return EventEnum.BASEDEDATOS;
		case -2:
			return EventEnum.CONCURRENCIA;
		case -3:
			return EventEnum.ENTIDADREPETIDAACTIVA;
		default: // no error
			return output;
		}
	}
	
}