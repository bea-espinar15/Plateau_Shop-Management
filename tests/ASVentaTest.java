package test;

import static org.junit.Assert.*;
import org.junit.BeforeClass;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import org.junit.Test;

import Negocio.ASFactory.ASFactory;
import Negocio.ClienteJPA.ASClienteJPA;
import Negocio.ClienteJPA.TClienteJPA;
import Negocio.Departamento.ASDepartamento;
import Negocio.Departamento.TDepartamento;
import Negocio.Empleado.ASEmpleado;
import Negocio.Empleado.TEmpleadoCompleto;
import Negocio.Producto.ASProducto;
import Negocio.Producto.TProductoBebida;
import Negocio.Venta.ASVenta;
import Negocio.Venta.TLineaVenta;
import Negocio.Venta.TVenta;
import utilities.Pair;

public class ASVentaTest {
	private static ASVenta asVenta;
	private static ASEmpleado asEmpleado;
	private static ASProducto asProducto;
	private static ASClienteJPA asCliente;
	private static ASDepartamento asDepartamento;
	private static Random random;

	@BeforeClass
	public static void beforeClass() {
		asVenta = ASFactory.getInstance().GetASVenta();
		asEmpleado = ASFactory.getInstance().GetASEmpleado();
		asProducto = ASFactory.getInstance().GetASProducto();
		asCliente = ASFactory.getInstance().GetASClienteJPA();
		asDepartamento = ASFactory.getInstance().GetASDepartamento();
		random = new Random();
	}

	private Integer crearDepartamento() {
		TDepartamento departamento = new TDepartamento();
		departamento.setNombre("VentaTest " + random.nextInt());
		Integer id = asDepartamento.alta(departamento);
		if (id <= 0)
			fail("Error: Alta departamento es un requisito de este test");
		return id;
	}

	private Integer crearEmpleado() {
		ASEmpleado asEmpleado = ASFactory.getInstance().GetASEmpleado();
		TEmpleadoCompleto empleado = new TEmpleadoCompleto();
		empleado.setNombre("Nombre " + random.nextInt());
		empleado.setDNI("DNI " + random.nextInt());
		empleado.setSueldo(1500);
		empleado.setTelefono(666777888);
		empleado.setEurosPM(69);
		empleado.setHorasExtra(2);
		empleado.setIDDpto(crearDepartamento());
		Integer idEmpleado = asEmpleado.alta(empleado);
		if (idEmpleado < 0)
			fail("Error: alta() empleado es requisito para poder testear");
		return idEmpleado;
	}

	private Integer crearProducto() {
		ASProducto asProducto = ASFactory.getInstance().GetASProducto();
		TProductoBebida producto = new TProductoBebida();
		producto.setNombre("Nombre " + random.nextInt());
		producto.setStock(1000);
		producto.setPrecioActual(10);
		producto.setTamano("XL");
		Integer idProducto = asProducto.alta(producto);
		if (idProducto < 0)
			fail("Error: alta() producto es requisito para poder testear");
		return idProducto;
	}

	private Integer crearCliente() {
		ASClienteJPA asCliente = ASFactory.getInstance().GetASClienteJPA();
		TClienteJPA cliente = new TClienteJPA();
		cliente.setNombre("Nombre " + random.nextInt());
		cliente.setDNI("DNI " + random.nextInt());
		Integer idCliente = asCliente.alta(cliente);
		if (idCliente < 0)
			fail("Error: alta() cliente es requisito para poder testear");
		cliente.setID(idCliente);
		cliente.setActivo(true);
		return idCliente;

	}

	private TVenta crearTVenta() {
		TVenta venta = new TVenta();
		venta.setIDClienteJPA(crearCliente());
		venta.setIDEmpleado(crearEmpleado());
		venta.setMetodoPago("Tarjeta");
		return venta;
	}

	private TLineaVenta crearTLineaVenta() {
		TLineaVenta lVenta = new TLineaVenta();
		lVenta.setIDProducto(crearProducto());
		lVenta.setUds(3);
		return lVenta;
	}

	private Pair<TVenta, TLineaVenta> crearVentaLineaVenta() {
		TVenta venta = crearTVenta();
		TLineaVenta lc = crearTLineaVenta();
		HashMap<Integer, TLineaVenta> VentaProductos = new HashMap<>();
		VentaProductos.put(0, lc);
		Integer idVenta = asVenta.cerrar(venta, VentaProductos);
		if (idVenta <= 0)
			fail("Error: cerrar() Venta es requisito para poder testear");
		venta.setID(idVenta);
		return new Pair<TVenta, TLineaVenta>(venta, lc);
	}

	private boolean equals(TVenta a, TVenta b) {
		return a.getID().equals(b.getID()) && a.getIDClienteJPA().equals(b.getIDClienteJPA())
				&& a.getPrecioTotal().equals(b.getPrecioTotal());
	}

	@Test
	public void testCerrar() {
		// Test codigo de error 13 - Venta con empleado no existente
		TVenta Venta = crearTVenta();
		Venta.setIDEmpleado(-1);
		TLineaVenta lv = crearTLineaVenta();
		HashMap<Integer, TLineaVenta> Ventaproductos = new HashMap<>();
		Ventaproductos.put(0, lv);

		Integer res = asVenta.cerrar(Venta, Ventaproductos);
		if (res != -13) {
			fail("Error: 13 - cerrar() de venta con empleado inexistente deberia devolver -13 y retorna " + res);
		}

		// Test codigo de error 14 - Venta con empleado no activo
		Venta = crearTVenta();
		lv = crearTLineaVenta();
		Ventaproductos = new HashMap<>();
		Ventaproductos.put(0, lv);
		asEmpleado.baja(Venta.getIDEmpleado());

		res = asVenta.cerrar(Venta, Ventaproductos);
		if (res != -14) {
			fail("Error: 14 - cerrar() de venta con cliente no activo deberia devolver -14 y retorna " + res);
		}

		// Test codigo de error 11 - Venta con cliente no existente
		Venta = crearTVenta();
		lv = crearTLineaVenta();
		Ventaproductos = new HashMap<>();
		Ventaproductos.put(0, lv);
		Venta.setIDClienteJPA(-1);

		res = asVenta.cerrar(Venta, Ventaproductos);
		if (res != -11) {
			fail("Error: 11 - cerrar() de venta con cliente inexistente deberia devolver -11 y retorna " + res);
		}

		// Test codigo de error 12 - Venta con cliente no activo
		Venta = crearTVenta();
		lv = crearTLineaVenta();
		Ventaproductos = new HashMap<>();
		Ventaproductos.put(0, lv);
		asCliente.baja(Venta.getIDClienteJPA());

		res = asVenta.cerrar(Venta, Ventaproductos);
		if (res != -12) {
			fail("Error: 12 - cerrar() de venta con cliente no activo deberia devolver -12 y retorna " + res);
		}

		// Test codigo de error 9 - Venta con producto inexistente
		Venta = crearTVenta();
		lv = crearTLineaVenta();
		Ventaproductos = new HashMap<>();
		Ventaproductos.put(0, lv);
		lv.setIDProducto(-1);

		res = asVenta.cerrar(Venta, Ventaproductos);
		if (res != -9) {
			fail("Error: 9 - cerrar() de venta con producto no existente deberia devolver -9 y retorna " + res);
		}

		// Test codigo de error 10 - Venta con producto no activo
		Venta = crearTVenta();
		lv = crearTLineaVenta();
		Ventaproductos = new HashMap<>();
		Ventaproductos.put(0, lv);
		asProducto.baja(lv.getIDProducto());

		res = asVenta.cerrar(Venta, Ventaproductos);
		if (res != -10) {
			fail("Error: 10 - cerrar() de venta con producto no activo deberia devolver -10 y retorna " + res);
		}

		// Test codigo de error 7 - Venta con producto con stock insuficiente
		Venta = crearTVenta();
		lv = crearTLineaVenta();
		lv.setUds(1001);
		Ventaproductos = new HashMap<>();
		Ventaproductos.put(0, lv);

		res = asVenta.cerrar(Venta, Ventaproductos);
		if (res != -7) {
			fail("Error: 7 - cerrar() de Ventar con producto con asientos insuficientes deberia devolver -7 y retorna "
					+ res);
		}
		
		// Cerrar sin errores
		TVenta venta = crearTVenta();
		TLineaVenta lc = crearTLineaVenta();
		HashMap<Integer, TLineaVenta> VentaProductos = new HashMap<>();
		VentaProductos.put(0, lc);
		Integer idVenta = asVenta.cerrar(venta, VentaProductos);
		if (idVenta <= 0)
			fail("Error: cerrar() deberia retornar id > 0 y retorna " + idVenta);
	}

	@Test
	public void testMostrar() {
		// Test codigo de error 4 - Venta inexistente
		Integer res = asVenta.mostrar(-1).getID();
		if (res != -4) {
			fail("Error: 4 - mostrar() con Venta inexistente deberia establecer su ID en -4 y retorna " + res);
		}
		// Test mostrar correcto
		Pair<TVenta, TLineaVenta> pair = crearVentaLineaVenta();
		res = asVenta.mostrar(pair.getFirst().getID()).getID();
		if (res <= 0) {
			fail("Error: mostrar() correcto deberia devolver Venta con id positivo y retorna " + res);
		}
	}

	@Test
	public void testListar() {
		Pair<TVenta, TLineaVenta> pair = crearVentaLineaVenta();
		TVenta Venta = asVenta.mostrar(pair.getFirst().getID());

		boolean found = false;
		ArrayList<TVenta> listaAll = asVenta.listar();
		for (TVenta c : listaAll) {
			if (equals(c, Venta))
				found = true;
		}

		if (!found)
			fail("Error: listar() sin error deberia contener la Venta creada");
	}

	@Test
	public void testMostrarEnDetalle() {
		// Test codigo de error 4 - Venta inexistente
		if (asVenta.mostrarEnDetalle(-1).getTVenta().getID() != -4) {
			fail("Error: 4 - MostrarEnDetalle() con Venta inexistente deberia establecer su ID en -4");
		}

		// Mostrarendetalle correcto
		Pair<TVenta, TLineaVenta> pair = crearVentaLineaVenta();
		if (asVenta.mostrarEnDetalle(pair.getFirst().getID()).getTVenta().getID() < 0) {
			fail("Error: mostrarEnDetalle() correcto deberia devolver una Venta con id positivo");
		}

	}

	@Test
	public void testListarPorCliente() {
		// Test codigo de error 11 - cliente inexistente
		if (asVenta.listarPorCliente(-1).get(0).getID() != -11) {
			fail("Error: 11 - listarPorCliente() con cliente inexistente deberia devolver una Venta con ID -11");
		}

		// Test listarPorCliente correcto
		Pair<TVenta, TLineaVenta> pair = crearVentaLineaVenta();
		if (asVenta.listarPorCliente(pair.getFirst().getIDClienteJPA()).get(0).getID() < 0) {
			fail("Error: listarPorCliente() correcto deberia devolver una Venta con ID positivo");
		}

		// Test codigo de error 12 - cliente no activo
		asCliente.baja(pair.getFirst().getIDClienteJPA());

		if (asVenta.listarPorCliente(pair.getFirst().getIDClienteJPA()).get(0).getID() != -12) {
			fail("Error: 12 - listarPorCliente() con cliente no activo deberia devolver una Venta con ID -12");
		}

	}

	@Test
	public void testListarPorProducto() {

		// Test codigo de error 9 - producto inexistente
		Integer res = asVenta.listarPorProducto(-1).get(0).getID();
		if (res != -9) {
			fail("Error: 9 - listarPorProducto() con producto inexistente deberia devolver una Venta con ID -9 y devuelve " + res);
		}

		// Test listarPorproducto correcto
		Pair<TVenta, TLineaVenta> pair = crearVentaLineaVenta();
		res = asVenta.listarPorProducto(pair.getSecond().getIDProducto()).get(0).getID();
		if (res < 0) {
			fail("Error: listarPorProducto() correcto deberia devolver una Venta con ID positivo y devuelve " + res);
		}

		// Test codigo de error 10 - producto no activo
		asProducto.baja(pair.getSecond().getIDProducto());
		res = asVenta.listarPorProducto(pair.getSecond().getIDProducto()).get(0).getID();
		if (res != -10) {
			fail("Error: 10 - listarPorProducto() con producto no activo deberia devolver una Venta con ID -10 y devuelve " + res);
		}
	}

	@Test
	public void testListarPorEmpleado() {

		// Test codigo de error 13 - empleado inexistente\
		Integer res = asVenta.listarPorEmpleado(-1).get(0).getID();
		if (res != -13) {
			fail("Error: 13 - ListarPorEmpleado() con empleado inexistente deberia devolver una Venta con ID -13 y devuelve " + res);
		}

		// Test listarPorEmpleado correcto
		Pair<TVenta, TLineaVenta> pair = crearVentaLineaVenta();
		res = asVenta.listarPorEmpleado(pair.getFirst().getIDEmpleado()).get(0).getID();
		if (res < 0) {
			fail("Error: ListarPorEmpleado() correcto deberia devolver una Venta con ID positivo y devuelve " + res);
		}

		// Test codigo de error 14 - empleado no activo
		if(asEmpleado.baja(pair.getFirst().getIDEmpleado()) != 0)
			fail("Error: Baja empleado es prequisito de este test");
		res = asVenta.listarPorEmpleado(pair.getFirst().getIDEmpleado()).get(0).getID();
		if (res != -14) {
			fail("Error: 14 - ListarPorEmpleado() con empleado no activo deberia devolver una Venta con ID -14 y devuelve " + res);
		}
	}

	@Test
	public void DevolverVenta() {
		Pair<TVenta, TLineaVenta> pair = crearVentaLineaVenta();

		// Test codigo de error 4 - Venta inexistente
		Integer res = asVenta.devolver(-1, pair.getSecond().getIDProducto(), 1);
		if (res != -4) {
			fail("Error: 4 - DevolverVenta() con Venta inexistente deberia devolver -4 y devuelve " + res);
		}

		// Test codigo de error 9 - producto inexistente
		res = asVenta.devolver(pair.getFirst().getID(), -1, 1);
		if (res != -9) {
			fail("Error: 9 - DevolverVenta() con producto inexistente deberia devolver -9 y devuelve " + res);
		}

		// Test codigo de error 8 - producto no incluido en Venta
		Integer idproducto = crearProducto();
		res = asVenta.devolver(pair.getFirst().getID(), idproducto, 1);
		if (res != -8) {
			fail("Error: 8 - DevolverVenta() con producto no incluido en Venta deberia devolver -8 y devuelve " + res);
		}

		// Test devolver Venta correcto
		res = asVenta.devolver(pair.getFirst().getID(), pair.getSecond().getIDProducto(), 1);
		if (res != 0) {
			fail("Error: DevolverVenta() correcto deberia devolver 0 y devuelve " + res);
		}
	}

}
