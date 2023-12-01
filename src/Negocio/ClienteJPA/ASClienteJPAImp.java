
package Negocio.ClienteJPA;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.LockModeType;
import javax.persistence.TypedQuery;

import Integracion.EMFSingleton.EMFSingleton;
import Negocio.Venta.Venta;

/*     CÓDIGOS DE ERROR: CLIENTEJPA
*  ·  -1: Error con la Base de Datos
*  ·  -2: Error Concurrencia
*  ·  -3: Existe un cliente activo con DNI
*  ·  -4: Cliente inexistente
*  .  -5: Cliente inactivo 
*  ·  -6: Cliente repetido
*  */

public class ASClienteJPAImp implements ASClienteJPA {

	public synchronized Integer alta(TClienteJPA cliente) {
		
		int res = -1;
		EntityManagerFactory emf;
		EntityManager em = null;
		EntityTransaction t = null;
		
		try {
			emf = EMFSingleton.getInstance().getEMF();
			em = emf.createEntityManager();
			t = em.getTransaction();
			
			t.begin();
			TypedQuery<ClienteJPA> query = em.createNamedQuery("ClienteJPA.findBydni", ClienteJPA.class).setParameter("dni", cliente.getDNI());
			List<ClienteJPA> list = query.getResultList();
			
			if (list.isEmpty()) { // Crear cliente nuevo, ya que no existe
				ClienteJPA clienteJPA = new ClienteJPA(cliente.getDNI(), cliente.getNombre(), new ArrayList<Venta>(), true);
				em.persist(clienteJPA);
				
				try {
					t.commit();
				} catch(Exception e) {
					em.close();
					return -2;
				}
				
				res = clienteJPA.getID();
				
			} else { // Existe un cliente con ese DNI
				
				ClienteJPA cl = list.get(0);
				if (cl.getActivo()) { // Error: No se puede dar de alta a un cliente existente y activo
					t.rollback();
					em.close();
					return -3;
					
				} else { // Reactivación 
					cl.setActivo(true);
					cl.setNombre(cliente.getNombre());
					cl.setDNI(cliente.getDNI());
					
					try{
						t.commit();
					} catch(Exception e) {
						em.close();
						return -2;
					}
					
					res = cl.getID();
				}
			}
			
		}
		catch(Exception e) {
			if (t != null && t.isActive())
				t.rollback();
			if (em != null)
				em.close();
			return -1;
		}
		
		em.close();		
		return res;
		
	}
	
	
	public Integer baja(Integer id_cliente) {
		
		EntityManagerFactory emf;
		EntityManager em = null;
		EntityTransaction t = null;
		
		try{
			emf = EMFSingleton.getInstance().getEMF();
			em = emf.createEntityManager();
			t = em.getTransaction();
			
			t.begin();
			ClienteJPA cliente = em.find(ClienteJPA.class, id_cliente);
			
			if (cliente == null) {
				t.rollback();
				em.close();
				return -4;
			}
			
			if (!cliente.getActivo()){
				t.rollback();
				em.close();
				return -5;
			}
			
			cliente.setActivo(false);
			
			try{
				t.commit();
			} catch(Exception e) {
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
		
		em.close();		
		return 0;
		
	}
	
	public Integer modificar(TClienteJPA cliente) {
		
		EntityManagerFactory emf = null;
		EntityManager em = null;
		EntityTransaction t = null;
		
		try{
			emf = EMFSingleton.getInstance().getEMF();
			em = emf.createEntityManager();
			t = em.getTransaction();
			
			t.begin();
			ClienteJPA cliente_anterior = em.find(ClienteJPA.class, cliente.getID());
			
			if(cliente_anterior == null){
				t.rollback();
				em.close();
				return -4;
			}
			
			if(!cliente_anterior.getActivo()){
				t.rollback();
				em.close();
				return -5;
			}
			

			if (!cliente_anterior.getDNI().equals(cliente.getDNI())) { // DNI distinto, se busca si existe alguno en la BD con el DNI que se ha introducido
				
				TypedQuery<ClienteJPA> query = em.createNamedQuery("ClienteJPA.findBydni", ClienteJPA.class).setParameter("dni", cliente.getDNI());
				List<ClienteJPA> list = query.getResultList();
				
				if (list.isEmpty()){ // No existe ningún cliente con el DNI que se quiere introducir
					cliente_anterior.setNombre(cliente.getNombre());
					cliente_anterior.setDNI(cliente.getDNI());
					
					try{
						t.commit();
					} catch(Exception e) {
						em.close();
						return -2;
					}
					
				} else{ // Existe un cliente con el DNI que se quiere introducir
		
					t.rollback();
					em.close();
					return -6;	
				}
				
			}
			else { // Mismo DNI, se actualizan sólo el resto de atributos
				
				cliente_anterior.setNombre(cliente.getNombre());
				
				try{
					t.commit();
				} catch(Exception e) {
					em.close();
					return -2;
				}
				
			}
			
		} catch(Exception e) {
			if (t != null && t.isActive())
				t.rollback();
			if (em != null)
				em.close();
			return -1;
		}
		
		em.close();		
		return 0;
		
	}
	
	public TClienteJPA mostrar(Integer id_cliente) {
		
		TClienteJPA tClienteJPA = new TClienteJPA();
		
		EntityManagerFactory emf;
		EntityManager em = null;
		EntityTransaction t = null;
		
		try {
			emf = EMFSingleton.getInstance().getEMF();
			em = emf.createEntityManager();
			t = em.getTransaction();
			
			t.begin();
			ClienteJPA cliente = em.find(ClienteJPA.class, id_cliente, LockModeType.OPTIMISTIC);
			
			if(cliente == null){
				t.rollback();
				em.close();
				tClienteJPA.setID(-4);
				return tClienteJPA;
			}
			
			tClienteJPA.setID(cliente.getID());
			tClienteJPA.setDNI(cliente.getDNI());
			tClienteJPA.setNombre(cliente.getNombre());
			tClienteJPA.setActivo(cliente.getActivo());
			
			try {
				t.commit();
			} catch(Exception e) {
				em.close();
				tClienteJPA.setID(-2);				
				return tClienteJPA;
			}
			
		} catch(Exception e) {
			if (t != null && t.isActive())
				t.rollback();
			if (em != null)
				em.close();
			tClienteJPA.setID(-1);
			return tClienteJPA;
		}
		
		em.close();		
		return tClienteJPA;
		
	}
	
	public ArrayList<TClienteJPA> listar() {
		
		EntityManagerFactory emf;
		EntityManager em = null;
		EntityTransaction t = null;
		
		ArrayList<TClienteJPA> list = new ArrayList<TClienteJPA>();
		
		try {
			emf = EMFSingleton.getInstance().getEMF();
			em = emf.createEntityManager();
			t = em.getTransaction();
			
			t.begin();
			TypedQuery<ClienteJPA> query = em.createNamedQuery("ClienteJPA.readAll", ClienteJPA.class).setLockMode(LockModeType.OPTIMISTIC);
			List<ClienteJPA> clientes = query.getResultList();
			
			for (ClienteJPA cl : clientes){
				TClienteJPA tClienteJPA = new TClienteJPA(cl.getID(), cl.getDNI(), cl.getNombre(), cl.getActivo());
				list.add(tClienteJPA);
			}
			
			try {
				t.commit();
			} catch(Exception e) {
				em.close();
				ArrayList<TClienteJPA> error = new ArrayList<TClienteJPA>();
				error.add(new TClienteJPA(-2));
				return error;
			}
			
		} catch(Exception e) {
			if (t != null && t.isActive())
				t.rollback();
			if (em != null)
				em.close();
			ArrayList<TClienteJPA> error = new ArrayList<TClienteJPA>();
			error.add(new TClienteJPA(-1));
			return error;
		}
		
		em.close();
		
		return list;
	}
	
}