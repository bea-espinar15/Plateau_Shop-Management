
package Presentacion.Command.Departamento;

import Negocio.ASFactory.ASFactory;
import Negocio.Departamento.ASDepartamento;
import Negocio.Departamento.TDepartamento;
import Presentacion.Command.Command;
import Presentacion.Controller.Context;
import Presentacion.Command.ContextEnum;
import Presentacion.Command.EventEnum;


public class MostrarDepartamentoCommand implements Command {
	
	public Context execute(Object input) {
		ASDepartamento asDepartamento = ASFactory.getInstance().GetASDepartamento();
		Object output = asDepartamento.mostrar((Integer)input);
		return new Context(ContextEnum.MOSTRARDEPARTAMENTO, event(output));
	}

	private Object event(Object output) {
		switch (((TDepartamento)output).getID()) {
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