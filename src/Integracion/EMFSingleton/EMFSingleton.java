
package Integracion.EMFSingleton;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public abstract class EMFSingleton {
	
	private static EMFSingleton instance;
	
	public synchronized static EMFSingleton getInstance() {
		if(instance == null)
			instance = new EMFSingletonImp(Persistence.createEntityManagerFactory("PlateauJPA"));
		return instance;
	}
	
	public abstract EntityManagerFactory getEMF();
	
}