
package Presentacion.Command.Empleado;

import Negocio.ASFactory.ASFactory;
import Negocio.Empleado.ASEmpleado;
import Presentacion.Command.Command;
import Presentacion.Command.ContextEnum;
import Presentacion.Command.EventEnum;
import Presentacion.Controller.Context;


public class BajaEmpleadoCommand implements Command {
	
	public Context execute(Object input) {
		ASEmpleado asEmpleado = ASFactory.getInstance().GetASEmpleado();
		Object output = asEmpleado.baja((Integer) input);
		return new Context(ContextEnum.BAJAEMPLEADO, event(output));
	}

	private Object event(Object output) {
		switch ((Integer)output) {
		case -1:
			return EventEnum.BASEDEDATOS;
		case -2:
			return EventEnum.CONCURRENCIA;
		case -4:
			return EventEnum.ENTIDADINEXISTENTE;
		case -5: 
			return EventEnum.ENTIDADINACTIVA;
		default: // no error
			return output;
		}
	}
	
}