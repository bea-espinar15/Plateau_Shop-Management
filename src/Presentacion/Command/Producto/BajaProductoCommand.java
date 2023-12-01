package Presentacion.Command.Producto;

import Negocio.ASFactory.ASFactory;
import Negocio.Producto.ASProducto;
import Presentacion.Command.Command;
import Presentacion.Command.EventEnum;
import Presentacion.Controller.Context;
import Presentacion.Command.ContextEnum;

public class BajaProductoCommand implements Command {

	@Override
	public Context execute(Object input) {
		ASProducto asProducto = ASFactory.getInstance().GetASProducto();
		Object output = asProducto.baja((Integer) input);
		return new Context(ContextEnum.BAJAPRODUCTO, event(output));
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
		default:
			return output;
		}
	}

}