
package Presentacion.Command.Departamento;

import java.util.ArrayList;
import Negocio.ASFactory.ASFactory;
import Negocio.Departamento.ASDepartamento;
import Negocio.Departamento.TDepartamento;
import Presentacion.Command.Command;
import Presentacion.Controller.Context;
import Presentacion.Command.ContextEnum;
import Presentacion.Command.EventEnum;

public class ListarDepartamentosCommand implements Command {

	public Context execute(Object input) {
		ASDepartamento asDepartamento = ASFactory.getInstance().GetASDepartamento();
		Object output = asDepartamento.listar();
		return new Context(ContextEnum.LISTARDEPARTAMENTOS, event(output));
	}

	@SuppressWarnings("unchecked")
	private Object event(Object output) {
		if (((ArrayList<TDepartamento>) output).size() == 0)
			return output;
		else {
			switch (((ArrayList<TDepartamento>) output).get(0).getID()) {
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