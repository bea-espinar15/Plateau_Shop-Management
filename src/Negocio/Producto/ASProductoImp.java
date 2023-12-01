package Negocio.Producto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.LockModeType;
import javax.persistence.TypedQuery;

import Integracion.EMFSingleton.EMFSingleton;
import Negocio.Proveedor.Proveedor;
import Negocio.Venta.LineaVenta;
import Negocio.Venta.Venta;

/*
 * CÓDIGOS DE ERROR 
 * ----------------
 *  · -1: Error con la Base de Datos
 *  · -2: Concurrencia
 *  · -3: Producto repetido ya activo
 *  · -4: Producto no existe
 *  · -5: Producto inactivo
 *  · -6: Producto repetido
 *  · -7: No existe proveedor
 *  · -8: Proveedor inactivo
 *  · -9: Venta inexistente
 *  · -10: Asociación existente
 *  · -11: Asociación inexistente
 *  · -12: Modificar stock de producto vendido
 *  · -13: Tipo modificado
 *  · -99: Error sintaxis
 */

public class ASProductoImp implements ASProducto {

	// realmente esto se comprueba en presentación
	private Integer checkAltaMod(TProducto producto) {

		if (producto.getNombre().equals("")) {
			// Error: nombre no puede ser vacio
			return -99;
		}

		if (producto.getPrecioActual() <= 0) {
			// Error: precio tiene que ser mayor que 0
			return -99;
		}

		if (producto.getStock() <= 0) {
			// Error: stock tiene que ser mayor que 0
			return -99;
		}

		if (producto instanceof TProductoBebida) {
			if (((TProductoBebida) producto).getTamano().equals("")) {
				// Error: tamano bebida no puede ser vacio
				return -99;
			}
		} else {
			if (((TProductoComida) producto).getPeso() <= 0) {
				// Error: peso comida tiene que ser mayor que 0
				return -99;
			}
		}
		// end of solution

		return 0;
	}

	public synchronized Integer alta(TProducto producto) {

		// Verificamos los campos
		Integer check = checkAltaMod(producto);
		if (check != 0) {
			// Error de verificacion de campos
			return check;
		}

		EntityManagerFactory emf;
		EntityManager em = null;
		EntityTransaction t = null;

		try {
			emf = EMFSingleton.getInstance().getEMF();
			em = emf.createEntityManager();
			t = em.getTransaction();
			t.begin();

			TypedQuery<Producto> query = em.createNamedQuery("Producto.findBynombre", Producto.class)
					.setParameter("nombre", producto.getNombre());
			List<Producto> listaProductos = query.getResultList();

			if (listaProductos.isEmpty()) {
				// No existe - damos de alta
				if (producto instanceof TProductoBebida) {
					ProductoBebida bebida = new ProductoBebida();
					bebida.setNombre(producto.getNombre());
					bebida.setPrecioActual(producto.getPrecioActual());
					bebida.setStock(producto.getStock());
					bebida.setTamano(((TProductoBebida) producto).getTamano());
					bebida.setActivo(true);

					em.persist(bebida);
					try {
						t.commit();
						return bebida.getID();
					} catch (Exception e) {
						// Error concurrencia
						return -2;
					}
				} else {
					ProductoComida comida = new ProductoComida();
					comida.setNombre(producto.getNombre());
					comida.setPrecioActual(producto.getPrecioActual());
					comida.setStock(producto.getStock());
					comida.setPeso(((TProductoComida) producto).getPeso());
					comida.setActivo(true);

					em.persist(comida);
					try {
						t.commit();
						return comida.getID();
					} catch (Exception e) {
						// Error concurrencia
						return -2;
					}
				}
			} else {
				Producto prod = listaProductos.get(0);
				if (!prod.getActivo()) {
					// Ya existe y esta de baja, reactivamos

					// Verificamos que los tipos sean los mismos
					if (prod instanceof ProductoBebida && producto instanceof TProductoBebida) {
						((ProductoBebida) prod).setTamano(((TProductoBebida) producto).getTamano());
					} else if (prod instanceof ProductoComida && producto instanceof TProductoComida) {
						((ProductoComida) prod).setPeso(((TProductoComida) producto).getPeso());
					} else {
						// Error: No se puede reactivar si el tipo no es el
						// mismo
						em.getTransaction().rollback();
						return -13;
					}

					prod.setActivo(true);
					prod.setNombre(producto.getNombre());
					prod.setPrecioActual(producto.getPrecioActual());
					prod.setStock(producto.getStock());

					try {
						t.commit();
						return prod.getID();
					} catch (Exception e) {
						// Error concurrencia
						return -2;
					}
				} else {
					// Error: Producto ya existe y ya activo
					t.rollback();
					return -3;
				}
			}
		} catch (Exception e) {
			if(t != null && t.isActive())
				t.rollback();
			return -1;
		} finally {
			if(em != null)
				em.close();
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

			// Verificamos si existe un producto con ese id
			Producto prod = em.find(Producto.class, id);
			if (prod == null) {
				// Error: Producto no existe
				t.rollback();
				return -4;
			}

			// Si existe, verificamos que esté activo
			if (!prod.getActivo()) {
				// Error: Producto ya esta dado de baja
				t.rollback();
				return -5;
			}

			// Baja Proveedor-Producto
			prod.setProveedores(new HashSet<Proveedor>());

			// Damos de baja el producto
			prod.setActivo(false);

			try {
				t.commit();
				return 0;
			} catch (Exception e) {
				// Error concurrencia
				return -2;
			}
		} catch (Exception e) {
			if(t != null && t.isActive())
				t.rollback();
			return -1;
		} finally {
			if(em != null)
				em.close();
		}
	}

	public Integer modificar(TProducto producto) {

		// Verificamos los campos
		Integer check = checkAltaMod(producto);
		if (check != 0) {
			// Error de verificacion de campos
			return check;
		}

		EntityManagerFactory emf;
		EntityManager em = null;
		EntityTransaction t = null;

		try {
			emf = EMFSingleton.getInstance().getEMF();
			em = emf.createEntityManager();
			t = em.getTransaction();
			t.begin();

			// Verificamos si existe un producto con ese nombre
			Producto prodAnt = em.find(Producto.class, producto.getID());
			if (prodAnt == null) {
				// Error: Producto no existe
				t.rollback();
				return -4;
			}

			// Si existe, verificamos que este activo
			if (!prodAnt.getActivo()) {
				// Error: Producto inactivo
				t.rollback();
				return -5;

			}

			// Verificamos que si ha cambiado el nombre, no coincida con otro
			if(!prodAnt.getNombre().equals(producto.getNombre())){
				// Producto.findBynombre
				List<Producto> productoDuplicado = em.createNamedQuery("Producto.findBynombre", Producto.class).setParameter("nombre", producto.getNombre())
						.getResultList();
				if(!productoDuplicado.isEmpty()) {
					t.rollback();
					return -6;
				}
			}
			
			// Verificamos que si ha cambiado el stock, no tenga lineas venta
			if(!prodAnt.getStock().equals(producto.getStock()) && !prodAnt.getLineasVenta().isEmpty()){
				// Error: No se puede modificar el stock de un producto con ventas
				t.rollback();
				return -12;
			}
			
			prodAnt.setNombre(producto.getNombre());
			prodAnt.setPrecioActual(producto.getPrecioActual());
			prodAnt.setStock(producto.getStock());
			// Verificacion de que sea el mismo tipo siempre deberia ser TRUE porque en la interfaz no se puede modificar tipo
			if (prodAnt instanceof ProductoBebida && producto instanceof TProductoBebida)
				((ProductoBebida) prodAnt).setTamano(((TProductoBebida) producto).getTamano());
			else if(prodAnt instanceof ProductoComida && producto instanceof TProductoComida)
				((ProductoComida) prodAnt).setPeso(((TProductoComida) producto).getPeso());
			else {
				// Esto nunca se deberia dar porque en la interfaz no se puede poner otro tipo
				t.rollback();
				return -13;
			}

			try {
				t.commit();
			} catch (Exception e) {
				// Error concurrencia
				return -2;
			}
			return 0;
		} catch (Exception e) {
			if(t != null && t.isActive())
				t.rollback();
			return -1;
		} finally {
			if(em != null)
				em.close();
		}
	}

	public TProducto mostrar(Integer id) {

		EntityManagerFactory emf;
		EntityManager em = null;
		EntityTransaction t = null;

		try {
			emf = EMFSingleton.getInstance().getEMF();
			em = emf.createEntityManager();
			t = em.getTransaction();
			t.begin();

			// Verificamos si existe el producto
			Producto prod = em.find(Producto.class, id, LockModeType.OPTIMISTIC);
			if (prod == null) {
				t.rollback();
				return new TProductoComida(-4);
			}

			if (prod instanceof ProductoBebida) {
				TProductoBebida bebida = new TProductoBebida(prod.getID(), prod.getActivo(), prod.getNombre(),
						prod.getPrecioActual(), prod.getStock(), ((ProductoBebida) prod).getTamano());

				try {
					t.commit();
				} catch (Exception e) {
					// Error concurrencia
					return new TProductoBebida(-2);
				}
				return bebida;
			} else {
				TProductoComida comida = new TProductoComida(prod.getID(), prod.getActivo(), prod.getNombre(),
						prod.getPrecioActual(), prod.getStock(), ((ProductoComida) prod).getPeso());
				try {
					t.commit();
				} catch (Exception e) {
					// Error concurrencia
					return new TProductoComida(-2);
				}
				return comida;
			}
		} catch (Exception e) {
			if(t != null && t.isActive())
				t.rollback();
			return new TProductoComida(-1);
		} finally {
			if(em != null)
				em.close();
		}

	}

	public ArrayList<TProducto> listar() {

		EntityManagerFactory emf;
		EntityManager em = null;
		EntityTransaction t = null;

		try {
			emf = EMFSingleton.getInstance().getEMF();
			em = emf.createEntityManager();
			t = em.getTransaction();
			t.begin();

			List<Producto> auxListaProductos = em.createNamedQuery("Producto.readAll", Producto.class)
					.setLockMode(LockModeType.OPTIMISTIC).getResultList();

			ArrayList<TProducto> listaProductos = new ArrayList<TProducto>();
			for (Producto p : auxListaProductos) {
				if (p instanceof ProductoBebida) {
					TProductoBebida prod = new TProductoBebida();
					prod.setID(p.getID());
					prod.setNombre(p.getNombre());
					prod.setActivo(p.getActivo());
					prod.setPrecioActual(p.getPrecioActual());
					prod.setStock(p.getStock());
					prod.setTamano(((ProductoBebida) p).getTamano());
					listaProductos.add(prod);
				} else {
					TProductoComida prod = new TProductoComida();
					prod.setID(p.getID());
					prod.setNombre(p.getNombre());
					prod.setActivo(p.getActivo());
					prod.setPrecioActual(p.getPrecioActual());
					prod.setStock(p.getStock());
					prod.setPeso(((ProductoComida) p).getPeso());
					listaProductos.add(prod);
				}
			}

			try {
				t.commit();
			} catch (Exception e) {
				// Error concurrencia
				ArrayList<TProducto> error = new ArrayList<TProducto>();
				error.add(new TProductoComida(-2));
				return error;
			}

			return listaProductos;
		} catch (Exception e) {
			if(t != null && t.isActive())
				t.rollback();
			ArrayList<TProducto> error = new ArrayList<TProducto>();
			error.add(new TProductoComida(-1));
			return error;
		} finally {
			if(em != null)
				em.close();
		}
	}

	public ArrayList<TProductoBebida> listarBebidas() {

		EntityManagerFactory emf;
		EntityManager em = null;
		EntityTransaction t = null;

		try {
			emf = EMFSingleton.getInstance().getEMF();
			em = emf.createEntityManager();
			t = em.getTransaction();
			t.begin();

			List<ProductoBebida> auxListaProductos = em.createNamedQuery("ProductoBebida.readAll", ProductoBebida.class)
					.setLockMode(LockModeType.OPTIMISTIC).getResultList();

			ArrayList<TProductoBebida> listaProductos = new ArrayList<TProductoBebida>();
			for (ProductoBebida p : auxListaProductos) {
				TProductoBebida prod = new TProductoBebida();
				prod.setID(p.getID());
				prod.setNombre(p.getNombre());
				prod.setActivo(p.getActivo());
				prod.setPrecioActual(p.getPrecioActual());
				prod.setStock(p.getStock());
				prod.setTamano(p.getTamano());
				listaProductos.add(prod);
			}

			try {
				t.commit();
			} catch (Exception e) {
				// Error concurrencia
				ArrayList<TProductoBebida> error = new ArrayList<TProductoBebida>();
				error.add(new TProductoBebida(-2));
				return error;
			}

			return listaProductos;
		} catch (Exception e) {
			if(t != null && t.isActive())
				t.rollback();
			ArrayList<TProductoBebida> error = new ArrayList<TProductoBebida>();
			error.add(new TProductoBebida(-1));
			return error;
		} finally {
			if(em != null)
				em.close();
		}

	}

	public ArrayList<TProductoComida> listarComidas() {

		EntityManagerFactory emf;
		EntityManager em = null;
		EntityTransaction t = null;

		try {
			emf = EMFSingleton.getInstance().getEMF();
			em = emf.createEntityManager();
			t = em.getTransaction();
			t.begin();

			List<ProductoComida> auxListaProductos = em.createNamedQuery("ProductoComida.readAll", ProductoComida.class)
					.setLockMode(LockModeType.OPTIMISTIC).getResultList();

			ArrayList<TProductoComida> listaProductos = new ArrayList<TProductoComida>();
			for (ProductoComida p : auxListaProductos) {
				TProductoComida prod = new TProductoComida();
				prod.setID(p.getID());
				prod.setNombre(p.getNombre());
				prod.setActivo(p.getActivo());
				prod.setPrecioActual(p.getPrecioActual());
				prod.setStock(p.getStock());
				prod.setPeso(p.getPeso());
				listaProductos.add(prod);
			}

			try {
				t.commit();
			} catch (Exception e) {
				// Error concurrencia
				ArrayList<TProductoComida> error = new ArrayList<TProductoComida>();
				error.add(new TProductoComida(-2));
				return error;
			}

			return listaProductos;

		} catch (Exception e) {
			if(t != null && t.isActive())
				t.rollback();
			ArrayList<TProductoComida> error = new ArrayList<TProductoComida>();
			error.add(new TProductoComida(-1));
			return error;
		} finally {
			if(em != null)
				em.close();
		}

	}

	public ArrayList<TProducto> listarPorProveedor(Integer idProveedor) {

		EntityManagerFactory emf;
		EntityManager em = null;
		EntityTransaction t = null;

		try {
			emf = EMFSingleton.getInstance().getEMF();
			em = emf.createEntityManager();
			t = em.getTransaction();
			t.begin();

			Proveedor prov = em.find(Proveedor.class, idProveedor, LockModeType.OPTIMISTIC);

			if (prov == null) {
				// Error: El proveedor no existe
				ArrayList<TProducto> error = new ArrayList<TProducto>();
				error.add(new TProductoComida(-7));
				return error;
			}
			if(!prov.getActivo()){
				// Error: Proveedor inactivo
				ArrayList<TProducto> error = new ArrayList<TProducto>();
				error.add(new TProductoComida(-8));
				return error;
			}

			HashSet<Producto> auxListaProductos = prov.getProductos();

			ArrayList<TProducto> listaProductos = new ArrayList<TProducto>();
			for (Producto p : auxListaProductos) {
				em.lock(p, LockModeType.OPTIMISTIC);
				
				// Sólo se añaden los productos que estén activos
				if(!p.getActivo())
					continue;
				
				if(p instanceof ProductoBebida){
					TProductoBebida bebida = new TProductoBebida();
					bebida.setID(p.getID());
					bebida.setNombre(p.getNombre());
					bebida.setActivo(p.getActivo());
					bebida.setPrecioActual(p.getPrecioActual());
					bebida.setStock(p.getStock());
					bebida.setTamano(((ProductoBebida) p).getTamano());
					listaProductos.add(bebida);
				}
				else{
					TProductoComida comida = new TProductoComida();
					comida.setID(p.getID());
					comida.setNombre(p.getNombre());
					comida.setActivo(p.getActivo());
					comida.setPrecioActual(p.getPrecioActual());
					comida.setStock(p.getStock());
					comida.setPeso(((ProductoComida) p).getPeso());
					listaProductos.add(comida);
				}
			}

			try {
				t.commit();
			} catch (Exception e) {
				// Error concurrencia
				ArrayList<TProducto> error = new ArrayList<TProducto>();
				error.add(new TProductoComida(-2));
				return error;
			}
			return listaProductos;
		} catch (Exception e) {
			if(t != null && t.isActive())
				t.rollback();
			ArrayList<TProducto> error = new ArrayList<TProducto>();
			error.add(new TProductoComida(-1));
			return error;
		} finally {
			if(em != null)
				em.close();
		}
	}

	public ArrayList<TProducto> listarPorVenta(Integer idVenta) {

		EntityManagerFactory emf;
		EntityManager em = null;
		EntityTransaction t = null;

		try {
			emf = EMFSingleton.getInstance().getEMF();
			em = emf.createEntityManager();
			t = em.getTransaction();
			t.begin();

			Venta v = em.find(Venta.class, idVenta); // no hace falta bloquear la venta, si se devuelve se modifica el producto que sí se bloquea

			if (v == null) {
				// Error: La venta no existe
				ArrayList<TProducto> error = new ArrayList<TProducto>();
				error.add(new TProductoComida(-9));
				return error;
			}

			// no hace falta bloquear las lineasVenta, si se devuelve se modifica el producto que sí se bloquea
			List<LineaVenta> listaLineaVentas = v.getLineasVenta();

			ArrayList<TProducto> listaProductos = new ArrayList<TProducto>();

			for (LineaVenta l : listaLineaVentas) {
				Producto p = l.getProducto();
				em.lock(p, LockModeType.OPTIMISTIC);

				if(p instanceof ProductoComida) {
					TProductoComida prod = new TProductoComida();
					prod.setID(p.getID());
					prod.setNombre(p.getNombre());
					prod.setActivo(p.getActivo());
					prod.setPrecioActual(p.getPrecioActual());
					prod.setStock(p.getStock());
					prod.setPeso(((ProductoComida) p).getPeso());
					listaProductos.add(prod);
				}
				else {
					TProductoBebida prod = new TProductoBebida();
					prod.setID(p.getID());
					prod.setNombre(p.getNombre());
					prod.setActivo(p.getActivo());
					prod.setPrecioActual(p.getPrecioActual());
					prod.setStock(p.getStock());
					prod.setTamano(((ProductoBebida) p).getTamano());
					listaProductos.add(prod);
				}
			}

			try {
				t.commit();
			} catch (Exception e) {
				// Error concurrencia
				ArrayList<TProducto> error = new ArrayList<TProducto>();
				error.add(new TProductoComida(-2));
				return error;
			}

			return listaProductos;
		} catch (Exception e) {
			if(t != null && t.isActive())
				t.rollback();
			ArrayList<TProducto> error = new ArrayList<TProducto>();
			error.add(new TProductoComida(-1));
			return error;
		} finally {
			if(em != null)
				em.close();
		}

	}

	public Integer anadirProveedor(Integer idProducto, Integer idProveedor) {

		EntityManagerFactory emf;
		EntityManager em = null;
		EntityTransaction t = null;

		try {
			emf = EMFSingleton.getInstance().getEMF();
			em = emf.createEntityManager();
			t = em.getTransaction();
			t.begin();

			Producto prod = em.find(Producto.class, idProducto);
			if (prod == null) {
				// Error: Producto no existe
				t.rollback();
				return -4;
			}
			if (!prod.getActivo()) {
				// Error: Producto inactivo
				t.rollback();
				return -5;
			}

			Proveedor prov = em.find(Proveedor.class, idProveedor, LockModeType.OPTIMISTIC_FORCE_INCREMENT);
			if (prov == null) {
				// Error: Proveedor no existe
				t.rollback();
				return -7;
			}
			if (!prov.getActivo()) {
				// Error: Proveedor inactivo
				t.rollback();
				return -8;
			}

			HashSet<Proveedor> listaProveedores = prod.getProveedores();
			if (listaProveedores.contains(prov)) {
				// Error: Proveedor ya vinculado
				t.rollback();
				return -10;
			}

			// Anadimos proveedor a producto
			listaProveedores.add(prov);
			prod.setProveedores(listaProveedores);

			try {
				t.commit();
			} catch (Exception e) {
				// Error concurrencia
				return -2;
			}
			return 0;
		} catch (Exception e) {
			if(t != null && t.isActive())
				t.rollback();
			return -1;
		} finally {
			if(em != null)
				em.close();
		}
	}

	public Integer quitarProveedor(Integer idProducto, Integer idProveedor) {

		EntityManagerFactory emf;
		EntityManager em = null;
		EntityTransaction t = null;

		try {
			emf = EMFSingleton.getInstance().getEMF();
			em = emf.createEntityManager();
			t = em.getTransaction();
			t.begin();

			Producto prod = em.find(Producto.class, idProducto);
			if (prod == null) {
				// Error: Producto no existe
				t.rollback();
				return -4;
			}
			if (!prod.getActivo()) {
				// Error: Producto inactivo
				t.rollback();
				return -5;
			}

			Proveedor prov = em.find(Proveedor.class, idProveedor, LockModeType.OPTIMISTIC_FORCE_INCREMENT);
			if (prov == null) {
				// Error: Proveedor no existe
				t.rollback();
				return -7;
			}
			if (!prov.getActivo()) {
				// Error: Proveedor inactivo
				t.rollback();
				return -8;
			}

			HashSet<Proveedor> listaProveedores = prod.getProveedores();
			if (!listaProveedores.contains(prov)) {
				// Error: Proveedor no vinculado
				t.rollback();
				return -11;
			}
			listaProveedores.remove(prov);
			prod.setProveedores(listaProveedores);

			try {
				t.commit();
			} catch (Exception e) {
				// Error concurrencia
				return -2;
			}
			return 0;
		} catch (Exception e) {
			if(t != null && t.isActive())
				t.rollback();
			return -1;
		} finally {
			if(em != null)
				em.close();
		}
	}
	
}