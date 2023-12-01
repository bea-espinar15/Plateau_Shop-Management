package Presentacion.Command.Producto;

import java.util.ArrayList;

import Negocio.ASFactory.ASFactory;
import Negocio.Producto.ASProducto;
import Negocio.Producto.TProductoComida;
import Presentacion.Command.Command;
import Presentacion.Command.EventEnum;
import Presentacion.Controller.Context;
import Presentacion.Command.ContextEnum;

public class ListarComidasCommand implements Command {

	@Override
	public Context execute(Object input) {
		ASProducto asProducto = ASFactory.getInstance().GetASProducto();
		Object output = asProducto.listarComidas();
		return new Context(ContextEnum.LISTARCOMIDAS, event(output));
	}

	@SuppressWarnings("unchecked")
	public Object event(Object output) {
		if (((ArrayList<TProductoComida>) output).size() == 0)
			return output;
		else {
			switch (((ArrayList<TProductoComida>) output).get(0).getID()) {
			case -1:
				return EventEnum.BASEDEDATOS;
			case -2:
				return EventEnum.CONCURRENCIA;
			default: // no error
				return output;
			}
		}
	}
	
}