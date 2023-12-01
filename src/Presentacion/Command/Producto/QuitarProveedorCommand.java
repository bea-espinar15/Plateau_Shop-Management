package Presentacion.Command.Producto;

import Negocio.ASFactory.ASFactory;
import Negocio.Producto.ASProducto;
import Presentacion.Command.Command;
import Presentacion.Command.EventEnum;
import Presentacion.Controller.Context;
import Presentacion.Command.ContextEnum;
import utilities.Pair;

public class QuitarProveedorCommand implements Command {

	@Override
	@SuppressWarnings("unchecked")
	public Context execute(Object input) {
		ASProducto asProducto = ASFactory.getInstance().GetASProducto();
		Integer idProducto = ((Pair<Integer, Integer>) input).getFirst();
		Integer idProveedor = ((Pair<Integer, Integer>) input).getSecond();
		Object output = asProducto.quitarProveedor(idProducto, idProveedor);
		return new Context(ContextEnum.QUITARPROVEEDOR, event(output));
	}

	public Object event(Object output) {
		switch((Integer) output){
		case -1:
			return EventEnum.BASEDEDATOS;
		case -2:
			return EventEnum.CONCURRENCIA;
		case -4:
			return EventEnum.ENTIDADINEXISTENTE;
		case -5:
			return EventEnum.ENTIDADINACTIVA;
		case -7:
			return EventEnum.PROVEEDORINEXISTENTE;
		case -8:
			return EventEnum.PROVEEDORINACTIVO;
		case -11:
			return EventEnum.ASOCIACIONINEXISTENTE;
		default: // no error
			return output;	
		}
	}
	
}