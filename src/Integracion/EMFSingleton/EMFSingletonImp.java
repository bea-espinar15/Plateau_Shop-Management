
package Integracion.EMFSingleton;

import javax.persistence.EntityManagerFactory;

public class EMFSingletonImp extends EMFSingleton{
	
	private EntityManagerFactory EMF;
	
	public EMFSingletonImp(EntityManagerFactory EMF) {
		this.EMF = EMF;
	}
	
	@Override
	public EntityManagerFactory getEMF() {
		return this.EMF;
	}

}
