
package Negocio.Venta;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.LockModeType;

import Integracion.EMFSingleton.EMFSingleton;
import Negocio.ClienteJPA.ClienteJPA;
import Negocio.ClienteJPA.TClienteJPA;
import Negocio.Empleado.Empleado;
import Negocio.Empleado.EmpleadoCompleto;
import Negocio.Empleado.EmpleadoParcial;
import Negocio.Empleado.TEmpleado;
import Negocio.Empleado.TEmpleadoCompleto;
import Negocio.Empleado.TEmpleadoParcial;
import Negocio.Producto.Producto;
import Negocio.Producto.ProductoBebida;
import Negocio.Producto.ProductoComida;
import Negocio.Producto.TProducto;
import Negocio.Producto.TProductoBebida;
import Negocio.Producto.TProductoComida;

/*  CÓDIGOS DE ERROR
 *  ----------------
 *  · -1: Base de datos
 *  · -2: Concurrencia optimista
 *  · -4: No existe venta
 *  · -7: Stock insuficiente
 *  · -8: Producto no incluido en la venta
 *  · -9: No existe producto
 *  · -10: Producto inactivo
 *  · -11: No existe cliente
 *  · -12: Cliente inactivo
 *  · -13: No existe empleado
 *  · -14: Empleado inactivo
 */

public class ASVentaImp implements ASVenta {

	@Override
	public Integer cerrar(TVenta venta, HashMap<Integer, TLineaVenta> carrito) {

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

			// comprobamos que el empleado existe y está activo
			Empleado emp = em.find(Empleado.class, venta.getIDEmpleado(), LockModeType.OPTIMISTIC_FORCE_INCREMENT);
			if (emp == null) {
				t.rollback();
				em.close();
				return -13;
			}
			if (!emp.getActivo()) {
				t.rollback();
				em.close();
				return -14;
			}

			// comprobamos que el cliente existe y está activo
			ClienteJPA c = em.find(ClienteJPA.class, venta.getIDClienteJPA(), LockModeType.OPTIMISTIC_FORCE_INCREMENT);
			if (c == null) {
				t.rollback();
				em.close();
				return -11;
			}
			if (!c.getActivo()) {
				t.rollback();
				em.close();
				return -12;
			}
			
			// creamos la venta
			Venta v = new Venta();
			v.setFecha(new Date());
			v.setMetodoPago(venta.getMetodoPago());
			v.setEmpleado(emp);
			v.setClienteJPA(c);
			em.persist(v);
			
			Integer precioTotal = 0;
			
			// lineas venta
			for (Integer i : carrito.keySet()) {
				TLineaVenta lineaVenta = carrito.get(i);
				
				// comprobamos que el producto existe y está activo
				Producto p = em.find(Producto.class, lineaVenta.getIDProducto());
				if (p == null) {
					t.rollback();
					em.close();
					return -9;
				}
				if (!p.getActivo()) {
					t.rollback();
					em.close();
					return -10;
				}
				
				// comprobamos que hay suficiente stock
				if (lineaVenta.getUds() > p.getStock()) {
					t.rollback();
					em.close();
					return -7;
				}
				
				// actualizamos stock del producto
				p.setStock(p.getStock() - lineaVenta.getUds());
				
				// creamos linea venta
				LineaVenta lv = new LineaVenta();
				lv.setVenta(v);
				lv.setProducto(p);
				lv.setPrecio(p.getPrecioActual());
				lv.setUds(lineaVenta.getUds());
				em.persist(lv);
				
				// acumulamos precio total
				precioTotal += lv.getPrecio() * lv.getUds();
			}			
			
			// ponemos precio a la venta
			v.setPrecioTotal(precioTotal);
			
			try {
				t.commit();
				em.close();
				return v.getID();
			} catch (Exception e) {
				em.close();
				return -2;
			}

		} catch (Exception e) {
			if (t != null && t.isActive())
				t.rollback();
			if (em != null)
				em.close();
			return -1;
		}

	}

	@Override
	public TVenta mostrar(Integer id) {
		
		EntityManagerFactory emf;
		EntityManager em = null;
		EntityTransaction t = null;
		TVenta tventa = new TVenta();
		
		try {
			emf = EMFSingleton.getInstance().getEMF();
			em = emf.createEntityManager();
			t = em.getTransaction();
			
			if (t == null) throw new Exception();
			t.begin();
			
			// comprobamos que existe
			Venta venta = em.find(Venta.class, id, LockModeType.OPTIMISTIC);
			if (venta == null){
				t.rollback();
				em.close();
				tventa.setID(-4);
				return tventa;
			}
			
			tventa.setID(venta.getID());
			tventa.setIDClienteJPA(venta.getClienteJPA().getID());
			tventa.setIDEmpleado(venta.getEmpleado().getID());
			tventa.setFecha(venta.getFecha());
			tventa.setMetodoPago(venta.getMetodoPago());
			tventa.setPrecioTotal(venta.getPrecioTotal());	
		
			try {
				t.commit();
				em.close();
				return tventa;
			} catch (Exception e) {
				em.close();
				return new TVenta(-2);
			}
			
		} catch(Exception e) {
			if (t != null &&t.isActive())
				t.rollback();
			if (em != null)
				em.close();
			tventa.setID(-1);;
			return tventa;
		}
		
	}

	@Override
	public ArrayList<TVenta> listar() {
		
		EntityManagerFactory emf;
		EntityManager em = null;
		EntityTransaction t = null;
		ArrayList<TVenta> tventas = new ArrayList<TVenta>();
		
		try {
			emf = EMFSingleton.getInstance().getEMF();
			em = emf.createEntityManager();
			t = em.getTransaction();
			
			if (t == null) throw new Exception();
			t.begin();
			
			List<Venta> ventas = em.createNamedQuery("Venta.readAll", Venta.class).setLockMode(LockModeType.OPTIMISTIC).getResultList();
			for (Venta v : ventas) {
				tventas.add(new TVenta(v.getID(), v.getClienteJPA().getID(), v.getEmpleado().getID(), v.getFecha(), v.getPrecioTotal(), v.getMetodoPago()));
			}
			
			try {
				t.commit();
				em.close();
				return tventas;
			} catch (Exception e) {
				em.close();
				ArrayList<TVenta> error = new ArrayList<TVenta>();
				error.add(new TVenta(-2));
				return error;
			}
			
		} catch(Exception e) {
			if (t != null & t.isActive())
				t.rollback();
			if (em != null)
				em.close();
			ArrayList<TVenta> error = new ArrayList<TVenta>();
			error.add(new TVenta(-1));
			return error;
		}
		
	}

	@Override
	public TVentaEnDetalle mostrarEnDetalle(Integer id) {

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
			Venta v = em.find(Venta.class, id); // no hace falta bloquear la venta, si se devuelve se modifica el producto que sí se bloquea
			if (v == null) {
				t.rollback();
				em.close();
				TVentaEnDetalle error = new TVentaEnDetalle();
				error.setTVenta(new TVenta(-4));
				return error;
			}

			// creamos el empleado
			Empleado emp = v.getEmpleado();
			em.lock(emp, LockModeType.OPTIMISTIC);
			TEmpleado empleado;
			if (emp instanceof EmpleadoCompleto)
				empleado = new TEmpleadoCompleto(emp.getID(), emp.getNombre(), emp.getDNI(), emp.getSueldo(),
						emp.getTelefono(), emp.getActivo(), emp.getDepartamento().getID(),
						((EmpleadoCompleto) emp).getEurosPM(), ((EmpleadoCompleto) emp).getHorasExtra());
			else
				empleado = new TEmpleadoParcial(emp.getID(), emp.getNombre(), emp.getDNI(), emp.getSueldo(),
						emp.getTelefono(), emp.getActivo(), emp.getDepartamento().getID(),
						((EmpleadoParcial) emp).getHoras(), ((EmpleadoParcial) emp).getEurosPH());

			// creamos el cliente
			ClienteJPA c = v.getClienteJPA();
			em.lock(c, LockModeType.OPTIMISTIC);
			TClienteJPA cliente = new TClienteJPA(c.getID(), c.getDNI(), c.getNombre(), c.getActivo());

			// creamos la venta
			TVenta venta = new TVenta(v.getID(), c.getID(), emp.getID(), v.getFecha(), v.getPrecioTotal(),
					v.getMetodoPago());

			// creamos las lineas venta y los productos
			ArrayList<LineaVenta> listLV = v.getLineasVenta(); // no hace falta bloquear las lineasVenta, si se devuelve se modifica el producto que sí se bloquea
			ArrayList<TLineaVenta> lineasVenta = new ArrayList<TLineaVenta>();
			ArrayList<TProducto> productos = new ArrayList<TProducto>();
			for (LineaVenta lv : listLV) {
				Producto p = lv.getProducto();
				em.lock(p, LockModeType.OPTIMISTIC);
				// lineaVenta
				lineasVenta.add(new TLineaVenta(v.getID(), p.getID(), lv.getUds(), lv.getPrecio()));
				// producto
				if (p instanceof ProductoComida)
					productos.add(new TProductoComida(p.getID(), p.getActivo(), p.getNombre(), p.getPrecioActual(),
							p.getStock(), ((ProductoComida) p).getPeso()));
				else
					productos.add(new TProductoBebida(p.getID(), p.getActivo(), p.getNombre(), p.getPrecioActual(),
							p.getStock(), ((ProductoBebida) p).getTamano()));
			}

			// TOA
			TVentaEnDetalle ventaDetallada = new TVentaEnDetalle(venta, lineasVenta, cliente, empleado, productos);

			try {
				t.commit();
				em.close();
				return ventaDetallada;
			} catch (Exception e) {
				em.close();
				TVentaEnDetalle error = new TVentaEnDetalle();
				error.setTVenta(new TVenta(-2));
				return error;
			}

		} catch (Exception e) {
			if (t != null && t.isActive())
				t.rollback();
			if (em != null)
				em.close();
			TVentaEnDetalle error = new TVentaEnDetalle();
			error.setTVenta(new TVenta(-1));
			return error;
		}

	}

	@Override
	public ArrayList<TVenta> listarPorProducto(Integer idProducto) {

		EntityManagerFactory emf;
		EntityManager em = null;
		EntityTransaction t = null;
		ArrayList<TVenta> ventas = new ArrayList<TVenta>();
		
		try {
			emf = EMFSingleton.getInstance().getEMF();
			em = emf.createEntityManager();
			t = em.getTransaction();
			
			if (t == null) throw new Exception();
			t.begin();
			
			// comprobamos que existe y que está activo
			Producto producto = em.find(Producto.class, idProducto, LockModeType.OPTIMISTIC);			
			if (producto == null) {
				t.rollback();
				em.close();
				ventas.add(new TVenta(-9));
				return ventas;
			}
			
			if (!producto.getActivo()) {
				t.rollback();
				em.close();
				ventas.add(new TVenta(-10));
				return ventas;
			}
			
			// no hace falta bloquear las lineasVenta, si se devuelve se modifica la venta que sí se bloquea
			ArrayList<LineaVenta> lineasVenta = producto.getLineasVenta();
			for (LineaVenta lv : lineasVenta) {
				Venta v = lv.getVenta();
				em.lock(v, LockModeType.OPTIMISTIC);
				ventas.add(new TVenta(v.getID(), v.getClienteJPA().getID(), v.getEmpleado().getID(), v.getFecha(), v.getPrecioTotal(), v.getMetodoPago()));
			}
			
			try {
				t.commit();
				em.close();
				return ventas;
			} catch (Exception e) {
				em.close();
				ArrayList<TVenta> error = new ArrayList<TVenta>();
				error.add(new TVenta(-2));
				return error;
			}
			
		} catch(Exception e) {
			if (t != null && t.isActive())
				t.rollback();
			if (em != null)
				em.close();
			ArrayList<TVenta> error = new ArrayList<TVenta>();
			error.add(new TVenta(-1));
			return error;
		}
		
	}

	@Override
	public ArrayList<TVenta> listarPorCliente(Integer idCliente) {

		EntityManagerFactory emf;
		EntityManager em = null;
		EntityTransaction t = null;
		ArrayList<TVenta> ventas = new ArrayList<TVenta>();
		
		try {
			emf = EMFSingleton.getInstance().getEMF();
			em = emf.createEntityManager();
			t = em.getTransaction();
			if (t == null)
				throw new Exception();
			t.begin();

			// comprobamos que el cliente existe y está activo
			ClienteJPA cliente = em.find(ClienteJPA.class, idCliente, LockModeType.OPTIMISTIC);
			if (cliente == null) {
				t.rollback();
				em.close();
				ventas.add(new TVenta(-11));
				return ventas;
			}
			if (!cliente.getActivo()) {
				t.rollback();
				em.close();
				ventas.add(new TVenta(-12));
				return ventas;
			}

			ArrayList<Venta> listaVentas = cliente.getVentas();
			for (Venta v : listaVentas) {
				em.lock(v, LockModeType.OPTIMISTIC);
				ventas.add(new TVenta(v.getID(), cliente.getID(), v.getEmpleado().getID(), v.getFecha(),
						v.getPrecioTotal(), v.getMetodoPago()));
			}

			try {
				t.commit();
				em.close();
				return ventas;
			} catch (Exception e) {
				em.close();
				ArrayList<TVenta> error = new ArrayList<>();
				error.add(new TVenta(-2));
				return error;
			}

		} catch (Exception e) {
			if (t != null && t.isActive())
				t.rollback();
			if (em != null)
				em.close();
			ArrayList<TVenta> error = new ArrayList<TVenta>();
			error.add(new TVenta(-1));
			return error;
		}
		
	}

	@Override
	public ArrayList<TVenta> listarPorEmpleado(Integer idEmpleado) {

		EntityManagerFactory emf;
		EntityManager em = null;
		EntityTransaction t = null;
		ArrayList<TVenta> ventas = new ArrayList<TVenta>();
		
		try {

			// creamos entity manager y empezamos transacción
			emf = EMFSingleton.getInstance().getEMF();
			em = emf.createEntityManager();
			t = em.getTransaction();
			if (t == null)
				throw new Exception();
			t.begin();

			// comprobamos que el empleado existe y está activo
			Empleado emp = em.find(Empleado.class, idEmpleado, LockModeType.OPTIMISTIC);
			if (emp == null) {
				t.rollback();
				em.close();
				ventas.add(new TVenta(-13));
				return ventas;
			}
			if (!emp.getActivo()) {
				t.rollback();
				em.close();
				ventas.add(new TVenta(-14));
				return ventas;
			}

			// obtenemos las ventas del empleado
			ArrayList<Venta> listaVentas = emp.getVentas();
			for (Venta v : listaVentas) {
				em.lock(v, LockModeType.OPTIMISTIC);
				ventas.add(new TVenta(v.getID(), v.getClienteJPA().getID(), emp.getID(), v.getFecha(),
						v.getPrecioTotal(), v.getMetodoPago()));
			}

			try {
				t.commit();
				em.close();
				return ventas;
			} catch (Exception e) {
				em.close();
				ArrayList<TVenta> error = new ArrayList<TVenta>();
				error.add(new TVenta(-2));
				return error;
			}

		} catch (Exception e) {
			if (t != null && t.isActive())
				t.rollback();
			if (em != null)
				em.close();
			ArrayList<TVenta> error = new ArrayList<TVenta>();
			error.add(new TVenta(-1));
			return error;
		}

	}

	@Override
	public Integer devolver(Integer idVenta, Integer idProducto, Integer uds) {
		
		EntityManagerFactory emf;
		EntityManager em = null;
		EntityTransaction t = null;
		
		try {
			emf = EMFSingleton.getInstance().getEMF();
			em = emf.createEntityManager();
			t = em.getTransaction();
			
			if (t == null) throw new Exception();
			t.begin();
			
			// comprobamos que la venta existe
			Venta venta = em.find(Venta.class, idVenta);
			if (venta == null) {
				t.rollback();
				em.close();
				return -4;
			}
			
			// comprobamos que el producto existe
			Producto producto = em.find(Producto.class, idProducto);
			if (producto == null) {
				t.rollback();
				em.close();
				return -9;
			}
			
			// comprobamos que el producto estaba incluido en la venta
			LineaVentaID lvID = new LineaVentaID(idVenta, idProducto);
			LineaVenta lineaventa = em.find(LineaVenta.class, lvID);
			if (lineaventa == null) {
				t.rollback();
				em.close();
				return -8;
			}
			
			// devolvemos las unidades y actualizamos stock y precio total
			Integer udsDevueltas = uds;
			if (lineaventa.getUds() - uds < 0)
				udsDevueltas = lineaventa.getUds();
			lineaventa.setUds(lineaventa.getUds() - udsDevueltas);
			producto.setStock(producto.getStock() + udsDevueltas);
			venta.setPrecioTotal(venta.getPrecioTotal() - udsDevueltas * lineaventa.getPrecio());
			
			try {
				t.commit();
				em.close();
				return 0;
			} catch (Exception e) {
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

}