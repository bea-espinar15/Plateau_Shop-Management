package Presentacion.Command.Producto;

import Negocio.ASFactory.ASFactory;
import Negocio.Producto.ASProducto;
import Negocio.Producto.TProducto;
import Presentacion.Command.Command;
import Presentacion.Command.EventEnum;
import Presentacion.Controller.Context;
import Presentacion.Command.ContextEnum;

public class AltaProductoCommand implements Command {

	@Override
	public Context execute(Object input) {
		ASProducto asProducto = ASFactory.getInstance().GetASProducto();
		Object output = asProducto.alta((TProducto) input);
		return new Context(ContextEnum.ALTAPRODUCTO, event(output));
	}

	public Object event(Object output) {
		switch((Integer) output){
		case -1:
			return EventEnum.BASEDEDATOS;
		case -2:
			return EventEnum.CONCURRENCIA;
		case -3:
			return EventEnum.ENTIDADREPETIDAACTIVA;
		case -13:
			return EventEnum.TIPOMODIFICADO;
		case -99:
			return EventEnum.SINTAXIS;
		default: // no error
			return output;	
		}
	}

}