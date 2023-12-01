package Negocio.Empleado;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.LockModeType;
import javax.persistence.TypedQuery;

import Integracion.EMFSingleton.EMFSingleton;
import Negocio.Departamento.Departamento;

/*
 *  -1: Error con la Base de Datos
 *  -2: Concurrencia
 *  -3: Empleado repetido ya activo
 *  -4: Empleado no existente
 *  -5: Empleado inactivo
 *  -6: Empleado repetido
 *  -7: Departamento no existe
 *  -8: Departamento inactivo
 *  -9: Tipo modificado
 */

public class ASEmpleadoImp implements ASEmpleado {

	public synchronized Integer alta(TEmpleado empleado) {
		
		EntityManagerFactory emf;
		EntityManager em = null;
		EntityTransaction t = null; 
		
		try{
			emf = EMFSingleton.getInstance().getEMF();
			em = emf.createEntityManager();
			t = em.getTransaction();
			if( t == null)
				throw new Exception();
			t.begin();
			
			Departamento departamento = em.find(Departamento.class, empleado.getIDDpto());
			
			// comprobamos que existe el departamento y que está activo
			if (departamento == null) {
				t.rollback();
				em.close();
				return -7;
			}
			if (!departamento.getActivo()) {
				t.rollback();
				em.close();
				return -8;
			}
			
			TypedQuery<Empleado> query = em.createNamedQuery("Empleado.findBydni",Empleado.class).setParameter("dni", empleado.getDNI());
			List<Empleado> list = query.getResultList();
			Empleado entityEmpleado;
			
			if (list.isEmpty()) { // damos de alta
				if (empleado instanceof TEmpleadoCompleto){
					TEmpleadoCompleto empleadoCompleto = (TEmpleadoCompleto)empleado;
					entityEmpleado = new EmpleadoCompleto(empleadoCompleto.getNombre(),empleadoCompleto.getDNI(),null,empleadoCompleto.getTelefono(),true,empleadoCompleto.getEurosPM(),empleadoCompleto.getHorasExtra());
				}
				else{
					TEmpleadoParcial empleadoParcial = (TEmpleadoParcial)empleado;
					entityEmpleado = new EmpleadoParcial(empleadoParcial.getNombre(),empleadoParcial.getDNI(),null,empleadoParcial.getTelefono(),true,empleadoParcial.getHoras(),empleadoParcial.getEurosPH());
				}
				entityEmpleado.setDepartamento(departamento);
				entityEmpleado.calcularSueldo();
				departamento.setNomina(departamento.getNomina() + entityEmpleado.getSueldo());		
				em.persist(entityEmpleado);
				
				try {
					t.commit();
					Integer id = entityEmpleado.getID();
					em.close();
					return id;
				} catch(Exception e) {
					em.close();
					return -2;
				}
			}
			else { // reactivación
				entityEmpleado = list.get(0);
				if (entityEmpleado.getActivo()){
					t.rollback();
					em.close();
					return -3;
				}
				else {
					if (entityEmpleado instanceof EmpleadoCompleto && empleado instanceof TEmpleadoCompleto) {
						((EmpleadoCompleto)entityEmpleado).setEurosPM(((TEmpleadoCompleto)empleado).getEurosPM());
						((EmpleadoCompleto)entityEmpleado).setHorasExtra(((TEmpleadoCompleto)empleado).getHorasExtra());
					}
					else if (entityEmpleado instanceof EmpleadoParcial && empleado instanceof TEmpleadoParcial) {
						((EmpleadoParcial)entityEmpleado).setHoras(((TEmpleadoParcial)empleado).getHoras());
						((EmpleadoParcial)entityEmpleado).setEurosPH(((TEmpleadoParcial)empleado).getEurosPH());
					}
					else {
						t.rollback();
						em.close();
						return -9;
					}
					entityEmpleado.setActivo(true);
					entityEmpleado.setNombre(empleado.getNombre());
					entityEmpleado.setTelefono(empleado.getTelefono());
					
					// actualizamos nóminas
					Departamento depEmpleado = entityEmpleado.getDepartamento();
					entityEmpleado.calcularSueldo();
					if (depEmpleado.getID() == departamento.getID()) // si no se ha modificado el departamento al que pertenece
						depEmpleado.setNomina(depEmpleado.getNomina() + entityEmpleado.getSueldo());
					else {
						departamento.setNomina(departamento.getNomina() + entityEmpleado.getSueldo());
						entityEmpleado.setDepartamento(departamento);
					}
				}
				
				try {
					t.commit();
					Integer id = entityEmpleado.getID();
					em.close();
					return id;
				} catch(Exception e) {
					em.close();
					return -2;
				}
			}
			
		} catch (Exception e) {
			if (t != null && t.isActive())
				t.rollback();
			if (em != null)
				em.close();
			return -1;
		}
		
	}
	
	public Integer baja(Integer id) {
		
		EntityManagerFactory emf;
		EntityManager em = null;
		EntityTransaction t = null;
		
		try {
			emf = EMFSingleton.getInstance().getEMF();
			em = emf.createEntityManager();
			t = em.getTransaction();
			if (t == null)
				throw new Exception();
			t.begin();
			
			Empleado e = em.find(Empleado.class, id);
			if (e == null){
				t.rollback();
				em.close();
				return -4;
			}
			
			if(!e.getActivo()){
				t.rollback();
				em.close();
				return -5;
			}
			
			e.setActivo(false);
			// restamos el sueldo del empleado a la nomina de su departamento
			Departamento dept = e.getDepartamento();
			dept.setNomina(dept.getNomina() - e.getSueldo());
			
			try {
				t.commit();
				em.close();
				return 0;
			} catch(Exception ex) {
				em.close();
				return -2;
			}
			
		} catch (Exception ex) {
			if (t != null && t.isActive()) 
				t.rollback();
			if (em != null)
				em.close();
			return -1;
		}
		
	}

	public Integer modificar(TEmpleado empleado) {

		EntityManagerFactory emf = null;
		EntityManager  em = null;
		EntityTransaction t = null;
		
		try {
			
			emf = EMFSingleton.getInstance().getEMF();
			em = emf.createEntityManager();			
			t = em.getTransaction();
			if (t == null)
				throw new Exception();
			t.begin();
			
			// comprobamos que el departamento existe y está activo
			Departamento dept = em.find(Departamento.class, empleado.getIDDpto());
			if (dept == null) {
				t.rollback();
				em.close();
				return -7;
			}
			if (!dept.getActivo()) {
				t.rollback();
				em.close();
				return -8;
			}
			
			// comprobamos que el empleado existía y estaba activo
			Empleado empleadoAnterior = em.find(Empleado.class, empleado.getID());
			if (empleadoAnterior == null) {
				t.rollback();
				em.close();
				return -4;
			}
			if (!empleadoAnterior.getActivo()) {
				t.rollback();
				em.close();
				return -5;
			}
			
			// si se ha modificado el DNI, comprobamos que no coincide con uno existente
			if (!empleadoAnterior.getDNI().equals(empleado.getDNI())) {
				List<Empleado> empleadoExistente = em.createNamedQuery("Empleado.findBydni", Empleado.class).setParameter("dni", empleado.getDNI()).getResultList();
				if (!empleadoExistente.isEmpty()){
					t.rollback();
					em.close();
					return -6;
				}
			}
			empleadoAnterior.setDNI(empleado.getDNI());
			empleadoAnterior.setNombre(empleado.getNombre());
			empleadoAnterior.setTelefono(empleado.getTelefono());
			if(empleado instanceof TEmpleadoCompleto ){
				((EmpleadoCompleto)empleadoAnterior).setEurosPM(((TEmpleadoCompleto) empleado).getEurosPM());
				((EmpleadoCompleto)empleadoAnterior).setHorasExtra(((TEmpleadoCompleto) empleado).getHorasExtra());		
			}
			else{
				((EmpleadoParcial)empleadoAnterior).setHoras(((TEmpleadoParcial)empleado).getHoras());
				((EmpleadoParcial)empleadoAnterior).setEurosPH(((TEmpleadoParcial)empleado).getEurosPH());
			}
			// actualizamos nóminas
			Departamento depEmpleado = empleadoAnterior.getDepartamento();
			depEmpleado.setNomina(depEmpleado.getNomina() - empleadoAnterior.getSueldo());
			empleadoAnterior.calcularSueldo();
			if (depEmpleado.getID() == dept.getID()) // si no se ha modificado el departamento al que pertenece
				depEmpleado.setNomina(depEmpleado.getNomina() + empleadoAnterior.getSueldo());
			else {
				dept.setNomina(dept.getNomina() + empleadoAnterior.getSueldo());
				empleadoAnterior.setDepartamento(dept);
			}
			
			try{
				t.commit();
				em.close();
				return 0;
			} catch(Exception e){
				em.close();
				return -2;
			}	
		} catch(Exception e) {
			if (t != null && t.isActive())
				t.rollback();
			if (em != null)
				em.close();
			return -1;
		}
		
	}

	public TEmpleado mostrar(Integer id) {
		
		EntityManagerFactory emf;
		EntityManager em = null;
		EntityTransaction t = null;
		
		try {
			emf = EMFSingleton.getInstance().getEMF();
			em = emf.createEntityManager();
			t = em.getTransaction();
			if (t == null)
				throw new Exception();
			t.begin();
			
			Empleado emp = em.find(Empleado.class, id,LockModeType.OPTIMISTIC);
			if (emp == null) {
				t.rollback();
				em.close();
				return new TEmpleadoCompleto(-4);
			}
			
			TEmpleado empleado;
			if(emp instanceof EmpleadoCompleto){
				EmpleadoCompleto empC = (EmpleadoCompleto)emp;
				empleado = new TEmpleadoCompleto(empC.getID(),empC.getNombre(),empC.getDNI(),empC.getSueldo(),empC.getTelefono(),empC.getActivo(),empC.getDepartamento().getID(),empC.getEurosPM(),empC.getHorasExtra());
			}
			else {
				EmpleadoParcial empP = (EmpleadoParcial)emp;
				empleado = new TEmpleadoParcial(empP.getID(),empP.getNombre(),empP.getDNI(),empP.getSueldo(),empP.getTelefono(),empP.getActivo(),empP.getDepartamento().getID(),empP.getHoras(),empP.getEurosPH());
			}
			
			try {
				t.commit();
				em.close();
				return empleado;
			} catch(Exception e) {
				em.close();
				return new TEmpleadoCompleto(-2);
			}
			
		} catch(Exception e) {
			if (t != null && t.isActive())
				t.rollback();
			if (em != null)
				em.close();
			return new TEmpleadoCompleto(-1);
		}
		
	}
	
	public ArrayList<TEmpleado> listar() {

		EntityManagerFactory emf;
		EntityManager em = null;
		EntityTransaction t = null;
		ArrayList<TEmpleado> tEmpleados = new ArrayList<TEmpleado>();
		
		try {
			emf = EMFSingleton.getInstance().getEMF();
			em = emf.createEntityManager();
			t = em.getTransaction();
			if (t == null)
				throw new Exception();
			t.begin();
			
			List<Empleado> empleados = em.createNamedQuery("Empleado.readAll",Empleado.class).setLockMode(LockModeType.OPTIMISTIC).getResultList();
			for(Empleado emp: empleados){
				if(emp instanceof EmpleadoCompleto){
					EmpleadoCompleto empC = (EmpleadoCompleto)emp;
					tEmpleados.add(new TEmpleadoCompleto(empC.getID(),empC.getNombre(),empC.getDNI(),empC.getSueldo(),empC.getTelefono(),empC.getActivo(),empC.getDepartamento().getID(),empC.getEurosPM(),empC.getHorasExtra()));
				}
				else{
					EmpleadoParcial empP = (EmpleadoParcial)emp;
					tEmpleados.add(new TEmpleadoParcial(empP.getID(),empP.getNombre(),empP.getDNI(),empP.getSueldo(),empP.getTelefono(),empP.getActivo(),empP.getDepartamento().getID(),empP.getHoras(),empP.getEurosPH()));
				}
			}
			
			try {
				t.commit();
				em.close();
				return tEmpleados;
			} catch(Exception e) {
				em.close();
				ArrayList<TEmpleado> error = new ArrayList<TEmpleado>();
				error.add(new TEmpleadoCompleto(-2));
				return error;
			}	
			
		} catch(Exception e) {
			if (t != null && t.isActive())
				t.rollback();
			if (em != null)
				em.close();
			ArrayList<TEmpleado> error = new ArrayList<TEmpleado>();
			error.add(new TEmpleadoCompleto(-1));
			return error;			
		}
		
	}
	
	public ArrayList<TEmpleadoCompleto> listarCompleto() {

		EntityManagerFactory emf;
		EntityManager em = null;
		EntityTransaction t = null;
		ArrayList<TEmpleadoCompleto> tEmpleados = new ArrayList<TEmpleadoCompleto>();
		
		try {
			emf = EMFSingleton.getInstance().getEMF();
			em = emf.createEntityManager();
			t = em.getTransaction();
			if (t == null)
				throw new Exception();
			t.begin();
			
			List<EmpleadoCompleto> empleados = em.createNamedQuery("EmpleadoCompleto.readAll",EmpleadoCompleto.class).setLockMode(LockModeType.OPTIMISTIC).getResultList();
			for(EmpleadoCompleto emp: empleados) {
				tEmpleados.add(new TEmpleadoCompleto(emp.getID(),emp.getNombre(),emp.getDNI(),emp.getSueldo(),emp.getTelefono(),emp.getActivo(),emp.getDepartamento().getID(),emp.getEurosPM(),emp.getHorasExtra()));
			}
			
			try {
				t.commit();
				em.close();
				return tEmpleados;
			} catch(Exception e) {
				em.close();
				ArrayList<TEmpleadoCompleto> error = new ArrayList<TEmpleadoCompleto>();
				error.add(new TEmpleadoCompleto(-2));
				return error;
			}
			
		} catch(Exception e) {
			if (t != null && t.isActive())
				t.rollback();
			if (em != null)
				em.close();
			ArrayList<TEmpleadoCompleto> error = new ArrayList<TEmpleadoCompleto>();
			error.add(new TEmpleadoCompleto(-1));
			return error;			
		}
		
	}

	public ArrayList<TEmpleadoParcial> listarParcial() {

		EntityManagerFactory emf;
		EntityManager em = null;
		EntityTransaction t = null;
		ArrayList<TEmpleadoParcial> tEmpleados = new ArrayList<TEmpleadoParcial>();
		
		try {
			emf = EMFSingleton.getInstance().getEMF();
			em = emf.createEntityManager();
			t = em.getTransaction();
			if (t == null)
				throw new Exception();
			t.begin();
			
			List<EmpleadoParcial> empleados = em.createNamedQuery("EmpleadoParcial.readAll",EmpleadoParcial.class).setLockMode(LockModeType.OPTIMISTIC).getResultList();
			for(EmpleadoParcial emp: empleados){
				tEmpleados.add(new TEmpleadoParcial(emp.getID(),emp.getNombre(),emp.getDNI(),emp.getSueldo(),emp.getTelefono(),emp.getActivo(),emp.getDepartamento().getID(),emp.getHoras(),emp.getEurosPH()));
			}
			try {
				t.commit();
				em.close();
				return tEmpleados;
			} catch(Exception e) {
				em.close();
				ArrayList<TEmpleadoParcial> error = new ArrayList<TEmpleadoParcial>();
				error.add(new TEmpleadoParcial(-2));
				return error;
			}	
			
		} catch(Exception e) {
			if (t != null && t.isActive())
				t.rollback();
			if (em != null)
				em.close();
			ArrayList<TEmpleadoParcial> error = new ArrayList<TEmpleadoParcial>();
			error.add(new TEmpleadoParcial(-1));
			return error;			
		}
		
	}

	public ArrayList<TEmpleado> listarPorDepartamento(Integer idDep) {

		EntityManagerFactory emf;
		EntityManager em = null;
		EntityTransaction t = null;
		ArrayList<TEmpleado> tEmpleados = new ArrayList<TEmpleado>();
		
		try {
			emf = EMFSingleton.getInstance().getEMF();
			em = emf.createEntityManager();
			t = em.getTransaction();
			if (t == null)
				throw new Exception();
			t.begin();
			
			Departamento dep = em.find(Departamento.class, idDep, LockModeType.OPTIMISTIC);
			
			if (dep == null) {
				//Error: el departamento no existe
				ArrayList<TEmpleado> error = new ArrayList<TEmpleado>();
				error.add(new TEmpleadoCompleto(-7));
				return error;
				
			}
			if(!dep.getActivo()){
				//Error: Departamento inactivo
				ArrayList<TEmpleado> error = new ArrayList<TEmpleado>();
				error.add(new TEmpleadoCompleto(-8));
				return error;
			}
			
			List<Empleado> empleados = dep.getEmpleados();
			for(Empleado emp: empleados){
				em.lock(emp, LockModeType.OPTIMISTIC);
				if(emp.getActivo()) {
					if(emp instanceof EmpleadoCompleto){
						EmpleadoCompleto empC = (EmpleadoCompleto)emp;
						tEmpleados.add(new TEmpleadoCompleto(empC.getID(),empC.getNombre(),empC.getDNI(),empC.getSueldo(),empC.getTelefono(),empC.getActivo(),idDep,empC.getEurosPM(),empC.getHorasExtra()));
					}
					else{
						EmpleadoParcial empP = (EmpleadoParcial)emp;
						tEmpleados.add(new TEmpleadoParcial(empP.getID(),empP.getNombre(),empP.getDNI(),empP.getSueldo(),empP.getTelefono(),empP.getActivo(),idDep,empP.getHoras(),empP.getEurosPH()));
					}
				}
			}
			try {
				t.commit();
				em.close();
				return tEmpleados;
			} catch(Exception e) {
				em.close();
				ArrayList<TEmpleado> error = new ArrayList<TEmpleado>();
				error.add(new TEmpleadoCompleto(-2));
				return error;
			}	
			
		} catch(Exception e) {
			if (t != null && t.isActive())
				t.rollback();
			if (em != null)
				em.close();
			ArrayList<TEmpleado> error = new ArrayList<TEmpleado>();
			error.add(new TEmpleadoCompleto(-1));
			return error;			
		}
	}
	
}