
package Presentacion.Controller;

public abstract class ApplicationController {
	
	private static ApplicationController instance;

	public synchronized static ApplicationController getInstance() {
		if(instance == null){
			instance = new ApplicationControllerImp();
		}
		return instance;
	}

	public abstract void manageRequest(Context context);
	
}