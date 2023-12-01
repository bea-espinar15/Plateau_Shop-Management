
package Presentacion.Command;

import java.util.HashMap;

public abstract class CommandFactory {

	
	private static CommandFactory instance;
	protected static HashMap<ContextEnum, Command> map;

	
	public synchronized static CommandFactory getInstance() {
		if (instance == null) {
			instance = new CommandFactoryImp();
			map = new HashMap<>();
		}
		return instance;
	}

	public abstract Command getCommand(ContextEnum contextEnum);

}