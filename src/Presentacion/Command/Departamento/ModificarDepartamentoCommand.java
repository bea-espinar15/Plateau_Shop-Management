
package Presentacion.Command.Departamento;

import Negocio.ASFactory.ASFactory;
import Negocio.Departamento.ASDepartamento;
import Negocio.Departamento.TDepartamento;
import Presentacion.Command.Command;
import Presentacion.Controller.Context;
import Presentacion.Command.ContextEnum;
import Presentacion.Command.EventEnum;

public class ModificarDepartamentoCommand implements Command {
	
	public Context execute(Object input) {
		ASDepartamento asDepartamento = ASFactory.getInstance().GetASDepartamento();
		Object output = asDepartamento.modificar((TDepartamento) input);
		return new Context(ContextEnum.MODIFICARDEPARTAMENTO, event(output));
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
		case -6: 
			return EventEnum.ENTIDADREPETIDA;
		default: // no error
			return output;
		}
	}

}