
package Presentacion.Command.Producto;

import Negocio.ASFactory.ASFactory;
import Negocio.Producto.TProducto;
import Negocio.Producto.ASProducto;
import Presentacion.Command.Command;
import Presentacion.Command.EventEnum;
import Presentacion.Controller.Context;
import Presentacion.Command.ContextEnum;

public class MostrarProductoCommand implements Command {

	@Override
	public Context execute(Object input) {
		ASProducto asProducto = ASFactory.getInstance().GetASProducto();
		Object output = asProducto.mostrar((Integer)input);
		return new Context(ContextEnum.MOSTRARPRODUCTO, event(output));
	}

	public Object event(Object output) {
		switch(((TProducto)output).getID()){
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