package Presentacion.Command.Empleado;

import java.util.ArrayList;

import Negocio.ASFactory.ASFactory;
import Negocio.Empleado.ASEmpleado;
import Negocio.Empleado.TEmpleadoCompleto;
import Presentacion.Command.Command;
import Presentacion.Command.ContextEnum;
import Presentacion.Command.EventEnum;
import Presentacion.Controller.Context;


public class ListarEmpleadosCompletoCommand implements Command {
	
	public Context execute(Object input) {
		ASEmpleado asEmpleado = ASFactory.getInstance().GetASEmpleado();
		Object output = asEmpleado.listarCompleto();
		return new Context(ContextEnum.LISTAREMPLEADOSCOMPLETO, event(output));
	}

	@SuppressWarnings("unchecked")
	private Object event(Object output) {
		if (((ArrayList<TEmpleadoCompleto>) output).size() == 0)
			return output;
		else {
			switch (((ArrayList<TEmpleadoCompleto>) output).get(0).getID()) {
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