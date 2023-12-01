package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import org.junit.BeforeClass;
import org.junit.Test;

import Negocio.ASFactory.ASFactory;
import Negocio.ClienteJPA.ASClienteJPA;
import Negocio.ClienteJPA.TClienteJPA;
import Negocio.Departamento.ASDepartamento;
import Negocio.Departamento.TDepartamento;
import Negocio.Empleado.ASEmpleado;
import Negocio.Empleado.TEmpleadoCompleto;
import Negocio.Producto.ASProducto;
import Negocio.Producto.TProducto;
import Negocio.Producto.TProductoBebida;
import Negocio.Producto.TProductoComida;
import Negocio.Proveedor.ASProveedor;
import Negocio.Proveedor.TProveedor;
import Negocio.Venta.ASVenta;
import Negocio.Venta.TLineaVenta;
import Negocio.Venta.TVenta;
import utilities.Pair;

public class ASProductoTest {
	private static ASProducto as;
	private static ASProveedor asProveedor;
	private static ASDepartamento asDepartamento;
	private static ASVenta asVenta;
	private static Random random;

	@BeforeClass
	public static void beforeClass() {
		as = ASFactory.getInstance().GetASProducto();
		asProveedor = ASFactory.getInstance().GetASProveedor();
		asDepartamento = ASFactory.getInstance().GetASDepartamento();
		asVenta = ASFactory.getInstance().GetASVenta();
		random = new Random();
	}

	// Funciones auxiliares
	private boolean equals(TProducto a, TProducto b) {
		return a.getActivo() == b.getActivo() && a.getID().equals(b.getID()) && a.getNombre().equals(b.getNombre())
				&& a.getPrecioActual().equals(b.getPrecioActual()) && a.getStock().equals(b.getStock());
	}

	private boolean equals(TProductoBebida a, TProductoBebida b) {
		return equals((TProducto) a, (TProducto) b) && a.getTamano().equals(b.getTamano());
	}

	private boolean equals(TProductoComida a, TProductoComida b) {
		return equals((TProducto) a, (TProducto) b) && a.getPeso().equals(b.getPeso());
	}

	private TProductoBebida crearTBebida() {
		TProductoBebida producto = new TProductoBebida();
		producto.setNombre("ASProductoTest Bebida " + random.nextInt());
		producto.setPrecioActual(1);
		producto.setStock(2);
		producto.setTamano("M");
		return producto;
	}

	private TProductoComida crearTComida() {
		TProductoComida producto = new TProductoComida();
		producto.setNombre("ASProductoTest Comida " + random.nextInt());
		producto.setPrecioActual(1);
		producto.setStock(999);
		producto.setPeso(2);
		return producto;
	}

	private TProductoBebida altaBebida() {
		TProductoBebida bebidaCreada = crearTBebida();
		Integer res = as.alta(bebidaCreada);
		Integer idBebidaCreada = res;
		if (res <= 0)
			fail("Error: Prerequisito crear bebida con ID > 0 y retorna " + res);
		bebidaCreada.setActivo(true);
		bebidaCreada.setID(idBebidaCreada);
		return bebidaCreada;
	}

	private TProductoComida altaComida() {
		TProductoComida comidaCreada = crearTComida();
		Integer res = as.alta(comidaCreada);
		Integer idComidaCreada = res;
		if (res <= 0)
			fail("Error:  Prerequisito crear comida con ID > 0 y retorna " + res);
		comidaCreada.setActivo(true);
		comidaCreada.setID(idComidaCreada);
		return comidaCreada;
	}

	private TProveedor altaProveedor() {
		TProveedor proveedor = new TProveedor();
		proveedor.setCIF("ASProductoTest " + random.nextInt());
		proveedor.setNombre("Nombre");
		proveedor.setTelefono(999999999);
		Integer res = asProveedor.alta(proveedor);
		if (res <= 0)
			fail("Error: Alta proveedor es un requisito para este test");
		proveedor.setActivo(true);
		proveedor.setID(res);
		return proveedor;
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

	private TLineaVenta crearTLineaVenta() {
		TLineaVenta lVenta = new TLineaVenta();
		lVenta.setIDProducto(altaComida().getID());
		lVenta.setUds(3);
		return lVenta;
	}

	private Pair<TVenta, TLineaVenta> crearVentaLineaVenta() {
		TVenta venta = new TVenta();
		venta.setIDClienteJPA(crearCliente());
		venta.setIDEmpleado(crearEmpleado());
		venta.setMetodoPago("Tarjeta");
		TLineaVenta lc = crearTLineaVenta();
		HashMap<Integer, TLineaVenta> VentaProductos = new HashMap<>();
		VentaProductos.put(0, lc);
		Integer idVenta = asVenta.cerrar(venta, VentaProductos);
		if (idVenta <= 0)
			fail("Error: cerrar() Venta es requisito para poder testear");
		venta.setID(idVenta);
		return new Pair<TVenta, TLineaVenta>(venta, lc);
	}

	// Tests
	@Test
	public void altaBebidaTest() {
		// Alta - Bebida sin errores
		TProductoBebida bebidaCreada = crearTBebida();
		Integer res = as.alta(bebidaCreada);
		Integer idBebidaCreada = res;
		if (res <= 0)
			fail("Error: Alta bebida sin errores deberia retornar ID > 0 y retorna " + res);

		// Alta - Bebida sin nombre
		TProductoBebida bebida = crearTBebida();
		bebida.setNombre("");
		res = as.alta(bebida);
		if (res != -99)
			fail("Error: Alta bebida sin nombre deberia retornar -99 y retorna " + res);

		// Alta - Bebida con stock erroneo
		bebida = crearTBebida();
		bebida.setStock(0);
		res = as.alta(bebida);
		if (res != -99)
			fail("Error: Alta bebida con stock erroneo deberia retornar -99 y retorna " + res);

		// Alta - Bebida con precio erroneo
		bebida = crearTBebida();
		bebida.setPrecioActual(0);
		res = as.alta(bebida);
		if (res != -99)
			fail("Error: Alta bebida con precio erroneo deberia retornar -99 y retorna " + res);

		// Alta - Bebida con tamano erroneo
		bebida = crearTBebida();
		bebida.setTamano("");
		res = as.alta(bebida);
		if (res != -99)
			fail("Error: Alta bebida tamano erroneo deberia retornar -99 y retorna " + res);

		// Alta - Comida ya existente de baja intentando modificar tipo
		TProductoComida comida = new TProductoComida();
		comida.setNombre(bebidaCreada.getNombre());
		comida.setPrecioActual(bebidaCreada.getPrecioActual());
		comida.setStock(bebidaCreada.getStock());
		comida.setPeso(12);

		// Damos de baja bebidaCreada
		if (as.baja(idBebidaCreada) != 0)
			fail("Error: Baja sin errores de bebida es un requisito del proximo test");

		res = as.alta(comida);
		if (res != -13)
			fail("Error: Alta comida con bebida existente y mismo nombre deberia retornar -13 y retorna " + res);
	}

	@Test
	public void altaComidaTest() {
		// Alta - Comida sin errores
		TProductoComida comidaCreada = crearTComida();
		Integer res = as.alta(comidaCreada);
		Integer idComidaCreada = res;
		if (res <= 0)
			fail("Error: Alta comida sin errores deberia retornar ID > 0 y retorna " + res);

		// Alta - Comida sin nombre
		TProductoComida comida = crearTComida();
		comida.setNombre("");
		res = as.alta(comida);
		if (res != -99)
			fail("Error: Alta comida sin nombre deberia retornar -99 y retorna " + res);

		// Alta - Comida con stock erroneo
		comida = crearTComida();
		comida.setStock(0);
		res = as.alta(comida);
		if (res != -99)
			fail("Error: Alta comida con stock erroneo deberia retornar -99 y retorna " + res);

		// Alta - Comida con precio erroneo
		comida = crearTComida();
		comida.setPrecioActual(0);
		res = as.alta(comida);
		if (res != -99)
			fail("Error: Alta comida con precio erroneo deberia retornar -99 y retorna " + res);

		// Alta - Comida con peso erroneo
		comida = crearTComida();
		comida.setPeso(0);
		res = as.alta(comida);
		if (res != -99)
			fail("Error: Alta comida tamano erroneo deberia retornar -99 y retorna " + res);

		// Alta - Bebida ya existente de baja intentando modificar tipo
		TProductoBebida bebida = new TProductoBebida();
		bebida.setNombre(comidaCreada.getNombre());
		bebida.setPrecioActual(comidaCreada.getPrecioActual());
		bebida.setStock(comidaCreada.getStock());
		bebida.setTamano("M");

		// Damos de baja comidaCreada
		if (as.baja(idComidaCreada) != 0)
			fail("Error: Baja sin errores de comida es un requisito del proximo test");

		res = as.alta(bebida);
		if (res != -13)
			fail("Error: Alta bebida con comida existente y mismo nombre deberia retornar -13 y retorna " + res);

	}

	@Test
	public void bajaTest() {
		// Baja - producto no existe
		Integer res = as.baja(-1);
		if (res != -4)
			fail("Error: Baja producto no existente deberia retornar -4 y retorna " + res);

		// Baja - Comida sin error
		TProductoComida comida = altaComida();
		res = as.baja(comida.getID());
		if (res != 0)
			fail("Error: Baja comida sin error deberia retornar 0 y retorna " + res);

		// Baja - Comida no activa
		res = as.baja(comida.getID());
		if (res != -5)
			fail("Error: Baja comida no activa deberia retornar -5 y retorna " + res);

		// Baja - Bebida sin error
		TProductoBebida bebida = altaBebida();
		res = as.baja(bebida.getID());
		if (res != 0)
			fail("Error: Baja bebida sin error deberia retornar 0 y retorna " + res);

		// Baja - Comida no activa
		res = as.baja(bebida.getID());
		if (res != -5)
			fail("Error: Baja bebida no activa deberia retornar -5 y retorna " + res);

		// Testeamos baja con proveedor producto
		// Damos de alta a un proveedor-comida
		TProveedor proveedor = altaProveedor();
		comida = altaComida();
		res = as.anadirProveedor(comida.getID(), proveedor.getID());
		if (res != 0)
			fail("Error: Anadir proveedor sin error deberia retornar 0 y retorna " + res);
		// Damos de baja la comida
		res = as.baja(comida.getID());
		if (res != 0)
			fail("Error: Baja comida sin error deberia retornar 0 y retorna " + res);
		// Verificamos que proveedor no este vinculado
		ArrayList<TProducto> lista = as.listarPorProveedor(proveedor.getID());
		for (TProducto p : lista) {
			if (p.getID().equals(comida.getID()))
				fail("Error: Al dar de baja una comida, se deberia de dar de baja proveedor-comida");
		}

		// Damos de alta a un proveedor-bebida
		proveedor = altaProveedor();
		bebida = altaBebida();
		res = as.anadirProveedor(bebida.getID(), proveedor.getID());
		if (res != 0)
			fail("Error: Anadir proveedor sin error deberia retornar 0 y retorna " + res);
		// Damos de baja la bebida
		res = as.baja(bebida.getID());
		if (res != 0)
			fail("Error: Baja bebida sin error deberia retornar 0 y retorna " + res);
		// Verificamos que proveedor no este vinculado
		lista = as.listarPorProveedor(proveedor.getID());
		for (TProducto p : lista) {
			if (p.getID().equals(bebida.getID()))
				fail("Error: Al dar de baja una bebida, se deberia de dar de baja proveedor-bebida");
		}
	}

	@Test
	public void mostrarTest() {
		// Mostrar - producto inexistente
		TProducto res = as.mostrar(-1);
		if (res.getID() != -4)
			fail("Error: Mostrar producto inexsitente deberia retornar -4 y retorna " + res);

		// Mostrar - Comida sin error
		TProductoComida comida = altaComida();
		res = as.mostrar(comida.getID());
		if (!equals(comida, (TProductoComida) res))
			fail("Error: Mostrar comida deberia retornar una entidad identica");

		// Mostrar - Bebida sin error
		TProductoBebida bebida = altaBebida();
		res = as.mostrar(bebida.getID());
		if (!equals(bebida, (TProductoBebida) res))
			fail("Error: Mostrar bebida deberia retornar una entidad identica");
	}

	@Test
	public void modificarTest() {
		// Producto inexistente bebida
		TProductoBebida bebida = crearTBebida();
		bebida.setID(-1);
		Integer res = as.modificar(bebida);
		if (res != -4)
			fail("Error: Modificar producto inexistente deberia retornar -4 y retorna " + res);

		// Producto inexistente comida
		TProductoComida comida = crearTComida();
		comida.setID(-1);
		res = as.modificar(comida);
		if (res != -4)
			fail("Error: Modificar producto inexistente deberia retornar -4 y retorna " + res);

		// Nombre vacio comida
		comida = altaComida();
		comida.setNombre("");
		res = as.modificar(comida);
		if (res != -99)
			fail("Error: Modificar con nombre vacio deberia retornar -99 y retorna " + res);

		// Nombre vacio bebida
		bebida = altaBebida();
		bebida.setNombre("");
		res = as.modificar(bebida);
		if (res != -99)
			fail("Error: Modificar con nombre vacio deberia retornar -99 y retorna " + res);

		// Precio erroneo comida
		comida = altaComida();
		comida.setPrecioActual(0);
		res = as.modificar(comida);
		if (res != -99)
			fail("Error: Modificar con precio erroneo deberia retornar -99 y retorna " + res);

		// Precio erroneo bebida
		bebida = altaBebida();
		bebida.setPrecioActual(0);
		res = as.modificar(bebida);
		if (res != -99)
			fail("Error: Modificar con precio erroneo deberia retornar -99 y retorna " + res);

		// Stock erroneo comida
		comida = altaComida();
		comida.setStock(0);
		res = as.modificar(comida);
		if (res != -99)
			fail("Error: Modificar con stock erroneo deberia retornar -99 y retorna " + res);

		// Stock erroneo bebida
		bebida = altaBebida();
		bebida.setStock(0);
		res = as.modificar(bebida);
		if (res != -99)
			fail("Error: Modificar con stock erroneo deberia retornar -99 y retorna " + res);

		// Peso erroneo
		comida = altaComida();
		comida.setPeso(0);
		res = as.modificar(comida);
		if (res != -99)
			fail("Error: Modificar con peso erroneo deberia retornar -99 y retorna " + res);

		// Tamano erroneo
		bebida = altaBebida();
		bebida.setTamano("");
		res = as.modificar(bebida);
		if (res != -99)
			fail("Error: Modificar con tamano erroneo deberia retornar -99 y retorna " + res);

		// Cambio de tipo comida a bebida
		comida = altaComida();
		TProductoBebida cambioTipoComida = new TProductoBebida();
		cambioTipoComida.setID(comida.getID());
		cambioTipoComida.setActivo(comida.getActivo());
		cambioTipoComida.setNombre(comida.getNombre());
		cambioTipoComida.setPrecioActual(comida.getPrecioActual());
		cambioTipoComida.setStock(comida.getStock());
		cambioTipoComida.setTamano("M");
		res = as.modificar(cambioTipoComida);
		if (res != -13)
			fail("Error: Modificar tipo deberia retornar -13 y retorna " + res);

		// Cambio de tipo bebida a comida
		bebida = altaBebida();
		TProductoComida cambioTipoBebida = new TProductoComida();
		cambioTipoBebida.setID(bebida.getID());
		cambioTipoBebida.setActivo(bebida.getActivo());
		cambioTipoBebida.setNombre(bebida.getNombre());
		cambioTipoBebida.setPrecioActual(bebida.getPrecioActual());
		cambioTipoBebida.setStock(bebida.getStock());
		cambioTipoBebida.setPeso(12);
		res = as.modificar(cambioTipoBebida);
		if (res != -13)
			fail("Error: Modificar tipo deberia retornar -13 y retorna " + res);

		// SIN ERROR - comida
		comida = altaComida();
		comida.setNombre("New name " + random.nextInt());
		comida.setPeso(99);
		comida.setPrecioActual(69);
		comida.setStock(420);
		res = as.modificar(comida);
		if (res != 0)
			fail("Error: Modificar sin errores deberia retornar 0 y retorna " + res);

		// SIN ERROR - bebida
		bebida = altaBebida();
		bebida.setNombre("New name " + random.nextInt());
		bebida.setTamano("L");
		bebida.setPrecioActual(69);
		bebida.setStock(420);
		res = as.modificar(bebida);
		if (res != 0)
			fail("Error: Modificar sin errores deberia retornar 0 y retorna " + res);
	}

	@Test
	public void listarTest() {
		// Listar - sin errores
		TProductoBebida bebida = altaBebida();
		TProductoComida comida = altaComida();

		ArrayList<TProducto> lista = as.listar();
		boolean bFound = false, cFound = false;
		for (TProducto p : lista) {
			if (equals(p, bebida))
				bFound = true;
			else if (equals(p, comida))
				cFound = true;
		}

		if (!bFound || !cFound)
			fail("Error: Listar deberia contener las entidades creadas. Comida: " + cFound + ", Bebida: " + bFound);
	}

	@Test
	public void listarBebidasTest() {
		Integer numBebidas = 5;
		ArrayList<TProductoBebida> bebidas = new ArrayList<>();
		for (int i = 0; i < numBebidas; i++)
			bebidas.add(altaBebida());

		// Sin error
		ArrayList<TProductoBebida> lista = as.listarBebidas();
		boolean[] encontrados = new boolean[numBebidas];
		for (TProductoBebida p : lista) {
			for (int i = 0; i < numBebidas; i++) {
				if (equals(p, bebidas.get(i))) {
					encontrados[i] = true;
					break;
				}
			}
		}

		for (int i = 0; i < numBebidas; i++) {
			if (!encontrados[i])
				fail("Error: La bebida " + i + " no fue encontrada");
		}
	}

	@Test
	public void listarComidasTest() {
		Integer numComidas = 5;
		ArrayList<TProductoComida> comidas = new ArrayList<>();
		for (int i = 0; i < numComidas; i++)
			comidas.add(altaComida());

		// Sin error
		ArrayList<TProductoComida> lista = as.listarComidas();
		boolean[] encontrados = new boolean[numComidas];
		for (TProductoComida p : lista) {
			for (int i = 0; i < numComidas; i++) {
				if (equals(p, comidas.get(i))) {
					encontrados[i] = true;
					break;
				}
			}
		}

		for (int i = 0; i < numComidas; i++) {
			if (!encontrados[i])
				fail("Error: La comida " + i + " no fue encontrada");
		}
	}

	@Test
	public void listarPorProveedorTest() {
		// Creamos bebidas
		Integer numBebidas = 2;
		ArrayList<TProductoBebida> bebidas = new ArrayList<>();
		for (int i = 0; i < numBebidas; i++)
			bebidas.add(altaBebida());
		// Creamos comidas
		Integer numComidas = 2;
		ArrayList<TProductoComida> comidas = new ArrayList<>();
		for (int i = 0; i < numComidas; i++)
			comidas.add(altaComida());
		// Vinculamos con proveedor
		TProveedor proveedor = altaProveedor();

		for (TProductoBebida b : bebidas) {
			Integer res = as.anadirProveedor(b.getID(), proveedor.getID());
			if (res != 0)
				fail("Error: Anadir proveedor sin error deberia retornar 0 y retorna " + res);
		}
		for (TProductoComida c : comidas) {
			Integer res = as.anadirProveedor(c.getID(), proveedor.getID());
			if (res != 0)
				fail("Error: Anadir proveedor sin error deberia retornar 0 y retorna " + res);
		}

		// ListarPorProveedor sin error
		ArrayList<TProducto> lista = as.listarPorProveedor(proveedor.getID());
		boolean[] bebidasFound = new boolean[numBebidas];
		boolean[] comidasFound = new boolean[numComidas];
		for (TProducto p : lista) {
			for (int i = 0; i < numComidas; i++) {
				if (equals(p, comidas.get(i))) {
					comidasFound[i] = true;
					break;
				}
			}
			for (int i = 0; i < numBebidas; i++) {
				if (equals(p, bebidas.get(i))) {
					bebidasFound[i] = true;
					break;
				}
			}
		}

		for (int i = 0; i < numComidas; i++) {
			if (!comidasFound[i])
				fail("Error: La comida " + i + " no fue encontrada");
		}
		for (int i = 0; i < numBebidas; i++) {
			if (!bebidasFound[i])
				fail("Error: La bebida " + i + " no fue encontrada");
		}

		// Proveedor no existente
		lista = as.listarPorProveedor(-1);
		if (lista.get(0).getID() != -7)
			fail("Error: Listar por proveedor, proveedor inexistente deberia retornar -7 y retorna "
					+ lista.get(0).getID());

		// Proveedor lista vacia
		proveedor = altaProveedor();
		lista = as.listarPorProveedor(proveedor.getID());
		if (!lista.isEmpty())
			fail("Error: Listar Por proveedor sin productos vinculados deberia retornar lista vacia");
	}

	@Test
	public void listarPorVentaTest() {
		// Venta inexistente
		Integer res = as.listarPorVenta(-1).get(0).getID();
		if (res != -9)
			fail("Error: ListarPorVenta entidad inexistente deberia devolver -9 y devuelve " + res);

		// Venta sin errores
		Pair<TVenta, TLineaVenta> ventaLineaVenta = crearVentaLineaVenta();
		res = as.listarPorVenta(ventaLineaVenta.getFirst().getID()).get(0).getID();
		if (!res.equals(ventaLineaVenta.getSecond().getIDProducto()))
			fail("Error: ListarPorVenta deberia contener entidad creada");
	}

	@Test
	public void anadirProveedorTest() {
		TProveedor proveedor = altaProveedor();
		TProductoBebida bebida = altaBebida();
		TProductoComida comida = altaComida();

		// Producto no existe
		Integer res = as.anadirProveedor(-1, proveedor.getID());
		if (res != -4)
			fail("Error: Anadir proveedor, producto no existente deberia retornar -4 y retorna " + res);

		// Proveedor no existente comida
		res = as.anadirProveedor(comida.getID(), -1);
		if (res != -7)
			fail("Error: Anadir proveedor, proveedor no existente deberia retornar -7 y retorna " + res);

		// Proveedor no existente bebida
		res = as.anadirProveedor(bebida.getID(), -1);
		if (res != -7)
			fail("Error: Anadir proveedor, proveedor no existente deberia retornar -7 y retorna " + res);

		// Sin error comida
		res = as.anadirProveedor(comida.getID(), proveedor.getID());
		if (res != 0)
			fail("Error: Anadir proveedor sin error deberia retornar 0 y retorna " + res);

		// Sin error bebida
		res = as.anadirProveedor(bebida.getID(), proveedor.getID());
		if (res != 0)
			fail("Error: Anadir proveedor sin error deberia retornar 0 y retorna " + res);

		// Proveedor-Comida ya vinculados
		res = as.anadirProveedor(comida.getID(), proveedor.getID());
		if (res != -10)
			fail("Error: Anadir proveedor ya vinculado deberia retornar -10 y retorna " + res);

		// Proveedor-Bebida ya vinculados
		res = as.anadirProveedor(bebida.getID(), proveedor.getID());
		if (res != -10)
			fail("Error: Anadir proveedor ya vinculado deberia retornar -10 y retorna " + res);
	}

	@Test
	public void quitarProveedorTest() {
		TProveedor proveedor = altaProveedor();
		TProductoBebida bebida = altaBebida();
		TProductoComida comida = altaComida();

		// Producto no existe
		Integer res = as.quitarProveedor(-1, proveedor.getID());
		if (res != -4)
			fail("Error: Quitar proveedor, producto no existente deberia retornar -4 y retorna " + res);

		// Proveedor no existente comida
		res = as.quitarProveedor(comida.getID(), -1);
		if (res != -7)
			fail("Error: Anadir proveedor, proveedor no existente deberia retornar -7 y retorna " + res);

		// Proveedor no existente bebida
		res = as.quitarProveedor(bebida.getID(), -1);
		if (res != -7)
			fail("Error: Anadir proveedor, proveedor no existente deberia retornar -7 y retorna " + res);

		// Proveedor-Comida no vinculados
		res = as.quitarProveedor(comida.getID(), proveedor.getID());
		if (res != -11)
			fail("Error: Anadir proveedor no vinculados deberia retornar -11 y retorna " + res);

		// Proveedor-Bebida no vinculados
		res = as.quitarProveedor(bebida.getID(), proveedor.getID());
		if (res != -11)
			fail("Error: Anadir proveedor no vinculados deberia retornar -11 y retorna " + res);

		// Sin error comida
		// Anadimos proveedor
		res = as.anadirProveedor(comida.getID(), proveedor.getID());
		if (res != 0)
			fail("Error: Anadir proveedor es un requisito");
		// Quitamos proveedor
		res = as.quitarProveedor(comida.getID(), proveedor.getID());
		if (res != 0)
			fail("Error: Anadir proveedor sin error deberia retornar 0 y retorna " + res);

		// Sin error bebida
		// Anadimos proveedor
		res = as.anadirProveedor(bebida.getID(), proveedor.getID());
		if (res != 0)
			fail("Error: Anadir proveedor es un requisito");
		// Quitamos proveedor
		res = as.quitarProveedor(bebida.getID(), proveedor.getID());
		if (res != 0)
			fail("Error: Anadir proveedor sin error deberia retornar 0 y retorna " + res);

	}
}
