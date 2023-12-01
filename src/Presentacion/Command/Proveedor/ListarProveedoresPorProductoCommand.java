
package Presentacion.Command.Proveedor;

import java.util.ArrayList;

import Negocio.ASFactory.ASFactory;
import Negocio.Proveedor.ASProveedor;
import Negocio.Proveedor.TProveedor;
import Presentacion.Command.Command;
import Presentacion.Command.ContextEnum;
import Presentacion.Command.EventEnum;
import Presentacion.Controller.Context;

public class ListarProveedoresPorProductoCommand implements Command {

	public Context execute(Object input) {
		ASProveedor asProveedor = ASFactory.getInstance().GetASProveedor();
		Object output = asProveedor.listarPorProducto((Integer) input);
		return new Context(ContextEnum.LISTARPROVEEDORESPORPRODUCTO, event(output));
	}

	@SuppressWarnings("unchecked")
	private Object event(Object output) {
		if (((ArrayList<TProveedor>) output).size() == 0)
			return output;
		else {
			switch (((ArrayList<TProveedor>) output).get(0).getID()) {
			case -1:
				return EventEnum.BASEDEDATOS;
			case -2:
				return EventEnum.CONCURRENCIA;
			case -7:
				return EventEnum.PRODUCTOINEXISTENTE;
			case -8:
				return EventEnum.PRODUCTOINACTIVO;
			default: // no error
				return output;
			}
		}
	}
	
}