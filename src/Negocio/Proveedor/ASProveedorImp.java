
package Negocio.Proveedor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.LockModeType;
import javax.persistence.TypedQuery;
import Integracion.EMFSingleton.EMFSingleton;
import Negocio.Producto.Producto;

/* CÓDIGOS DE ERROR
*  ----------------
*  · -1: Error con la Base de Datos
*  · -2: Error concurrencia
*  · -3: Ya existe proveedor activo con CIF
*  · -4: No existe proveedor
*  · -5: Proveedor inactivo
*  · -6: Ya existe proveedor con CIF
*  · -7: No existe producto
*  · -8: Producto inactivo 
*  */

public class ASProveedorImp implements ASProveedor {

	public synchronized Integer alta(TProveedor proveedor) {

		Integer result = -1;
		EntityManagerFactory emf;
		EntityManager em = null;
		EntityTransaction t = null;

		try {
			emf = EMFSingleton.getInstance().getEMF();
			em = emf.createEntityManager();
			t = em.getTransaction();

			t.begin();

			TypedQuery<Proveedor> query = em.createNamedQuery("Proveedor.findBycif", Proveedor.class)
					.setParameter("cif", proveedor.getCIF());
			List<Proveedor> proveedoresAux = query.getResultList();

			if (proveedoresAux.isEmpty()) {
				Proveedor p = new Proveedor(new HashSet<Producto>(), proveedor.getTelefono(), proveedor.getNombre(),
						proveedor.getCIF(), true);
				em.persist(p);
				try {
					t.commit();
				} catch (Exception e) {
					em.close();
					return -2;
				}
				result = p.getID();
			} else {
				Proveedor p = proveedoresAux.get(0);
				if (p.getActivo()) {
					t.rollback();
					em.close();
					return -3;
				} else {
					p.setActivo(true);
					p.setTelefono(proveedor.getTelefono());
					p.setNombre(proveedor.getNombre());
					p.setCIF(proveedor.getCIF());
					try {
						t.commit();
					} catch (Exception e) {
						t.rollback();
						em.close();
						return -2;
					}
					result = p.getID();
				}
			}

			em.close();
			return result;

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

			t.begin();

			Proveedor p = em.find(Proveedor.class, id);
			if (p == null) {
				t.rollback();
				em.close();
				return -4;
			}
			if (!p.getActivo()) {
				t.rollback();
				em.close();
				return -5;
			}

			p.setActivo(false);
			HashSet<Producto> aux = p.getProductos();
			for (Producto prod : aux) {
				HashSet<Proveedor> x = prod.getProveedores();
				x.remove(p);
				prod.setProveedores(x);
			}

			try {
				t.commit();
			} catch (Exception e) {
				em.close();
				return -2;
			}

			em.close();
			return 0;

		} catch (Exception e) {
			if (t != null && t.isActive())
				t.rollback();
			if (em != null)
				em.close();
			return -1;
		}

	}

	public Integer modificar(TProveedor proveedor) {

		EntityManagerFactory emf;
		EntityManager em = null;
		EntityTransaction t = null;

		try {
			emf = EMFSingleton.getInstance().getEMF();
			em = emf.createEntityManager();
			t = em.getTransaction();
			t.begin();

			Proveedor prodAnt = em.find(Proveedor.class, proveedor.getID());
			if (prodAnt == null) {
				t.rollback();
				em.close();
				return -4;
			}
			if (!prodAnt.getActivo()) {
				t.rollback();
				em.close();
				return -5;
			}

			if (!proveedor.getCIF().equals(prodAnt.getCIF())) {
				TypedQuery<Proveedor> query = em.createNamedQuery("Proveedor.findBycif", Proveedor.class)
						.setParameter("cif", proveedor.getCIF());
				List<Proveedor> proveedoresAux = query.getResultList();
				if (!proveedoresAux.isEmpty()) {
					t.rollback();
					em.close();
					return -6;
				}
			}
			prodAnt.setCIF(proveedor.getCIF());
			prodAnt.setNombre(proveedor.getNombre());
			prodAnt.setTelefono(proveedor.getTelefono());

			try {
				t.commit();
			} catch (Exception e) {
				em.close();
				return -2;
			}

			em.close();
			return 0;

		} catch (Exception e) {
			if (t != null && t.isActive())
				t.rollback();
			if (em != null)
				em.close();
			return -1;
		}

	}

	public TProveedor mostrar(Integer id) {

		TProveedor aux = null;
		EntityManagerFactory emf;
		EntityManager em = null;
		EntityTransaction t = null;

		try {
			emf = EMFSingleton.getInstance().getEMF();
			em = emf.createEntityManager();
			t = em.getTransaction();
			t.begin();

			Proveedor p = em.find(Proveedor.class, id, LockModeType.OPTIMISTIC);
			if (p == null) {
				t.rollback();
				em.close();
				return new TProveedor(-4);
			}

			aux = new TProveedor(p.getID(), p.getTelefono(), p.getNombre(), p.getCIF(), p.getActivo());
			try {
				t.commit();
			} catch (Exception e) {
				em.close();
				return new TProveedor(-2);
			}

			em.close();
			return aux;

		} catch (Exception e) {
			if (t != null && t.isActive())
				t.rollback();
			if (em != null)
				em.close();
			return new TProveedor(-1);
		}

	}

	public ArrayList<TProveedor> listar() {

		EntityManagerFactory emf;
		EntityManager em = null;
		EntityTransaction t = null;

		try {
			emf = EMFSingleton.getInstance().getEMF();
			em = emf.createEntityManager();
			t = em.getTransaction();
			t.begin();

			List<Proveedor> aux = em.createNamedQuery("Proveedor.readAll", Proveedor.class)
					.setLockMode(LockModeType.OPTIMISTIC).getResultList();

			ArrayList<TProveedor> listaproveedores = new ArrayList<TProveedor>();
			for (Proveedor p : aux) {
				TProveedor prov = new TProveedor(p.getID(), p.getTelefono(), p.getNombre(), p.getCIF(), p.getActivo());
				listaproveedores.add(prov);
			}

			try {
				t.commit();
			} catch (Exception e) {
				em.close();
				ArrayList<TProveedor> a = new ArrayList<TProveedor>();
				a.add(new TProveedor(-2));
				return a;
			}

			em.close();
			return listaproveedores;

		} catch (Exception e) {
			if (t != null && t.isActive())
				t.rollback();
			if (em != null)
				em.close();
			ArrayList<TProveedor> error = new ArrayList<TProveedor>();
			error.add(new TProveedor(-1));
			return error;
		}

	}

	public ArrayList<TProveedor> listarPorProducto(Integer idProducto) {
		
		EntityManagerFactory emf;
		EntityManager em = null;
		EntityTransaction t = null;

		try {
			emf = EMFSingleton.getInstance().getEMF();
			em = emf.createEntityManager();
			t = em.getTransaction();
			t.begin();

			Producto prod = em.find(Producto.class, idProducto, LockModeType.OPTIMISTIC);
			if (prod == null) {
				t.rollback();
				em.close();
				ArrayList<TProveedor> listaError = new ArrayList<TProveedor>();
				listaError.add(new TProveedor(-7));
				return listaError;
			}
			if (!prod.getActivo()) {
				t.rollback();
				em.close();
				ArrayList<TProveedor> listaError = new ArrayList<TProveedor>();
				listaError.add(new TProveedor(-8));
				return listaError;
			}

			HashSet<Proveedor> auxListaProv = prod.getProveedores();

			ArrayList<TProveedor> listaProvs = new ArrayList<TProveedor>();
			for (Proveedor p : auxListaProv) {
				em.lock(p, LockModeType.OPTIMISTIC);
				if (p.getActivo()) {
					TProveedor prov = new TProveedor(p.getID(), p.getTelefono(), p.getNombre(), p.getCIF(),
							p.getActivo());
					listaProvs.add(prov);
				}
			}
			
			try {
				t.commit();
			} catch (Exception e) {
				em.close();
				ArrayList<TProveedor> a = new ArrayList<TProveedor>();
				a.add(new TProveedor(-2));
				return a;
			}
			
			em.close();
			return listaProvs;

		} catch (Exception e) {
			if (t != null && t.isActive())
				t.rollback();
			if (em != null)
				em.close();
			ArrayList<TProveedor> listaError = new ArrayList<TProveedor>();
			listaError.add(new TProveedor(-1));
			return listaError;
		}

	}

}