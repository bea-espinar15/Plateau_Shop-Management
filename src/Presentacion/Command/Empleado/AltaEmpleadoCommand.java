
package Presentacion.Command.Empleado;

import Negocio.ASFactory.ASFactory;
import Negocio.Empleado.ASEmpleado;
import Negocio.Empleado.TEmpleado;
import Presentacion.Command.Command;
import Presentacion.Command.ContextEnum;
import Presentacion.Command.EventEnum;
import Presentacion.Controller.Context;


public class AltaEmpleadoCommand implements Command {
	
	public Context execute(Object input) {
		ASEmpleado asEmpleado = ASFactory.getInstance().GetASEmpleado();
		Object output = asEmpleado.alta((TEmpleado) input);
		return new Context(ContextEnum.ALTAEMPLEADO, event(output));
	}

	private Object event(Object output) {
		switch((Integer) output){
		case -1:
			return EventEnum.BASEDEDATOS;
		case -2:
			return EventEnum.CONCURRENCIA;
		case -3:
			return EventEnum.ENTIDADREPETIDAACTIVA;
		case -7:
			return EventEnum.DEPARTAMENTOINEXISTENTE;
		case -8:
			return EventEnum.DEPARTAMENTOINACTIVO;
		case -9:
			return EventEnum.TIPOMODIFICADO;
		default: // no error
			return output;
		}
	}
	
}