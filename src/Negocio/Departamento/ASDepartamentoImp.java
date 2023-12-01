
package Negocio.Departamento;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.LockModeType;
import javax.persistence.TypedQuery;

import Integracion.EMFSingleton.EMFSingleton;
import Negocio.Empleado.Empleado;


/*  CÓDIGOS DE ERROR
 *  ----------------
 *  · -1: Base de datos
 *  · -2: Concurrencia optimista
 *  · -3: Ya existe dpto activo con nombre
 *  · -4: No existe dpto
 *  · -5: Dpto inactivo
 *  · -6: Ya existe dpto con nombre
 */

public class ASDepartamentoImp implements ASDepartamento {
	
	@Override
	public synchronized Integer alta(TDepartamento dpto) {

		EntityManagerFactory emf;
		EntityManager em = null;
		EntityTransaction t = null;
		
		try {
			
			// creamos entity manager y empezamos transacción
			emf = EMFSingleton.getInstance().getEMF();
			em = emf.createEntityManager();
			t = em.getTransaction();
			if (t == null)
				throw new Exception();
			t.begin();
			
			Departamento d;
			
			// comprobamos si existe un dpto con el mismo nombre
			TypedQuery<Departamento> query = em.createNamedQuery("Departamento.findByNombre", Departamento.class).setParameter("nombre", dpto.getNombre());
			List<Departamento> listDpto = query.getResultList();
			if (!listDpto.isEmpty()) {
				// existe, comprobamos que está inactivo
				d = listDpto.get(0);
				if (d.getActivo()) {
					t.rollback();
					em.close();
					return -3;
				}
				// reactivamos
				d.setActivo(true);
			}
			else {
				// no existe, damos de alta
				d = new Departamento();
				d.setNombre(dpto.getNombre());
				d.setNomina(0);
				d.setActivo(true);
				em.persist(d);
			}			
			
			try {
				t.commit();
				Integer id = d.getID();
				em.close();
				return id;
			} catch(Exception e) {
				em.close();
				return -2;
			}
			
		}
		catch (Exception e) {
			if (t != null && t.isActive())
				t.rollback();
			if (em != null)
				em.close();
			return -1;
		}
		
	}

	@Override
	public Integer baja(Integer id) {
		
		EntityManagerFactory emf;
		EntityManager em = null;
		EntityTransaction t = null;
		
		try {
			
			// creamos entity manager y empezamos transacción
			emf = EMFSingleton.getInstance().getEMF();
			em = emf.createEntityManager();
			t = em.getTransaction();
			if (t == null)
				throw new Exception();
			t.begin();
			
			// comprobamos que existe y está activo
			Departamento d = em.find(Departamento.class, id);
			if (d == null) {
				t.rollback();
				em.close();
				return -4;
			}
			if (!d.getActivo()) {
				t.rollback();
				em.close();
				return -5;
			}
			
			// damos de baja
			d.setActivo(false);
			d.setNomina(0);
			
			// damos de baja a sus empleados
			ArrayList<Empleado> emps = d.getEmpleados();
			for (Empleado e : emps) 
				e.setActivo(false);
			
			try {
				t.commit();
				em.close();
				return 0;
			} catch(Exception e) {
				em.close();
				return -2;
			}
			
		}
		catch (Exception e) {
			if (t != null && t.isActive()) 
				t.rollback();
			if (em != null)
				em.close();
			return -1;
		}
		
	}

	@Override
	public Integer modificar(TDepartamento dpto) {
		
		EntityManagerFactory emf;
		EntityManager  em = null;
		EntityTransaction t = null;
		
		try{
			emf = EMFSingleton.getInstance().getEMF();
			em = emf.createEntityManager();
			
			t = em.getTransaction();
			if (t == null) throw new Exception();
			t.begin();
			
			// comprobamos que existe y está activo
			Departamento departamentoAnterior = em.find(Departamento.class, dpto.getID());			
			if (departamentoAnterior == null){
				t.rollback();
				em.close();
				return -4;
			}			
			if (!departamentoAnterior.getActivo()){
				t.rollback();
				em.close();
				return -5;
			}
				
			// si se ha modificado el nombre, comprobamos que no coincide con uno existente
			if (!departamentoAnterior.getNombre().equals(dpto.getNombre())){
				List<Departamento> departamentoExistente = em.createNamedQuery("Departamento.findByNombre", Departamento.class).setParameter("nombre", dpto.getNombre()).getResultList();
				if (!departamentoExistente.isEmpty()){
					t.rollback();
					em.close();
					return -6;
				}
			}
			
			departamentoAnterior.setNombre(dpto.getNombre());
			
			try {
				t.commit();	
			}
			catch (Exception e) {
				em.close();
				return -2;
			}
			
			em.close();
			return 0;
			
		} catch(Exception e){
			if (t != null && t.isActive())
				t.rollback();
			if (em != null)
				em.close();
			return -1;
		}

	}

	@Override
	public TDepartamento mostrar(Integer id) {

		EntityManagerFactory emf;
		EntityManager em = null;
		EntityTransaction t = null;
		
		try {
			
			// creamos entity manager y empezamos transacción
			emf = EMFSingleton.getInstance().getEMF();
			em = emf.createEntityManager();
			t = em.getTransaction();
			if (t == null)
				throw new Exception();
			t.begin();
			
			// comprobamos que existe
			Departamento d = em.find(Departamento.class, id, LockModeType.OPTIMISTIC);
			if (d == null) {
				t.rollback();
				em.close();
				return new TDepartamento(-4);
			}
			
			// creamos transfer
			TDepartamento td = new TDepartamento(d.getID(), d.getNombre(), d.getNomina(), d.getActivo());
			
			try {
				t.commit();
				em.close();
				return td;
			} catch(Exception e) {
				em.close();
				return new TDepartamento(-2);
			}
			
		}
		catch (Exception e) {
			if (t != null && t.isActive())
				t.rollback();
			if (em != null)
				em.close();
			return new TDepartamento(-1);
		}
		
	}

	@Override
	public ArrayList<TDepartamento> listar() {
		
		EntityManagerFactory emf;
		EntityManager em = null;
		EntityTransaction t = null;
		ArrayList<TDepartamento> tdepartamentos = new ArrayList<TDepartamento>();

		try {
			emf = EMFSingleton.getInstance().getEMF();
			em = emf.createEntityManager();
			t = em.getTransaction();
			
			if (t == null) throw new Exception();
			t.begin();
			
			List<Departamento> departamentos = em.createNamedQuery("Departamento.readAll", Departamento.class).setLockMode(LockModeType.OPTIMISTIC).getResultList();
			for (Departamento dept : departamentos) {
				tdepartamentos.add(new TDepartamento(dept.getID(),dept.getNombre(), dept.getNomina(), dept.getActivo())); 
			}
			
			try {
				t.commit();
			} catch (Exception e) {
				em.close();
				ArrayList<TDepartamento> error = new ArrayList<TDepartamento>();
				error.add(new TDepartamento(-2));
				return error;
			}
			
			em.close();
			return tdepartamentos;
			
		} catch(Exception e) {
			if (t != null && t.isActive())
				t.rollback();
			if (em != null)
				em.close();
			ArrayList<TDepartamento> error = new ArrayList<TDepartamento>();
			error.add(new TDepartamento(-1));
			return error;
		}	
		
	}

}