
package Presentacion.Controller;

public abstract class Dispatcher {
	
	private static Dispatcher instance;

	public synchronized static Dispatcher getInstance() {
		if (instance == null)
			instance = new DispatcherImp();
		return instance;
	}

	public abstract void Dispatch(Context response);
	
}
