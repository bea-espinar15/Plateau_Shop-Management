
package Presentacion.Command.Producto;

import Negocio.ASFactory.ASFactory;
import Negocio.Producto.ASProducto;
import Negocio.Producto.TProducto;
import Presentacion.Command.Command;
import Presentacion.Command.EventEnum;
import Presentacion.Controller.Context;
import Presentacion.Command.ContextEnum;

public class ModificarProductoCommand implements Command {

	@Override
	public Context execute(Object input) {
		ASProducto asProducto = ASFactory.getInstance().GetASProducto();
		Object output = asProducto.modificar((TProducto) input);
		return new Context(ContextEnum.MODIFICARPRODUCTO, event(output));
	}

	public Object event(Object output) {
		switch ((Integer)output) {
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
		case -12:
			return EventEnum.STOCKPRODUCTOVENDIDO;
		case -13:
			return EventEnum.TIPOMODIFICADO;
		case -99:
			return EventEnum.SINTAXIS;
		default:
			return output;
		}
	}
	
}