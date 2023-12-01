package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Random;

import org.junit.BeforeClass;
import org.junit.Test;

import Negocio.ASFactory.ASFactory;
import Negocio.Producto.ASProducto;
import Negocio.Producto.TProductoBebida;
import Negocio.Proveedor.ASProveedor;
import Negocio.Proveedor.TProveedor;

public class ASProveedorTest {
	static Random random;
	static ASProveedor asProveedor;
	private static ASProducto as;

	@BeforeClass
	public static void beforeClass() {
		as = ASFactory.getInstance().GetASProducto();
		asProveedor = ASFactory.getInstance().GetASProveedor();
		random = new Random();
	}

	// funciones auxiliares
	private boolean equals(TProveedor a, TProveedor b) {
		return (a.getCIF().equals(b.getCIF()) && a.getNombre().equals(b.getNombre())
				&& a.getTelefono().equals(b.getTelefono()));
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

	private TProveedor altaProveedorAux() {
		TProveedor proveedorCreado = creaTProveedorRandom();
		Integer idProveedorCreado = asProveedor.alta(proveedorCreado);
		if (idProveedorCreado <= 0)
			fail("Error: Prerequisito crear bebida con ID > 0 y retorna " + idProveedorCreado);
		proveedorCreado.setActivo(true);
		proveedorCreado.setID(idProveedorCreado);
		return proveedorCreado;
	}

	public TProveedor creaTProveedorRandom() {
		TProveedor proveedor = new TProveedor();
		proveedor.setCIF("ASProductoTest " + random.nextInt());
		proveedor.setNombre("Proveedor test" + random.nextInt(1000) + 1);
		proveedor.setTelefono(random.nextInt(800000000) + 100000000);
		proveedor.setActivo(true);
		return proveedor;
	}

	private TProductoBebida crearTBebida() {
		TProductoBebida producto = new TProductoBebida();
		producto.setNombre("ASProductoTest Bebida " + random.nextInt());
		producto.setPrecioActual(1);
		producto.setStock(2);
		producto.setTamano("3");
		return producto;
	}

	// Tests
	@Test
	public void altaProveedor() {
		TProveedor p = creaTProveedorRandom();
		Integer res = asProveedor.alta(p);
		if (res <= 0)
			fail("Error: Alta proveedor sin errores deberia retornar ID > 0 y retorna " + res);

		p.setID(res);
		res = asProveedor.alta(p);
		if (res != -3)
			fail("Error: Entidad repetida activa, debería devolver -3 y devuelve: " + res);

	}

	@Test
	public void bajaProveedor() {
		// test de concurrencia con hebras (?)
		Integer res = asProveedor.alta(creaTProveedorRandom());
		Integer sol = asProveedor.baja(res);
		if (sol < 0)
			fail("Error: Baja proveedor sin errores deberia retornar ID >= 0 y retorna " + res);
		sol = asProveedor.baja(res);
		if (sol != -5)
			fail("Error: Entidad ya dada de baja debería devolver -5 y devuelve: " + res);
		sol = asProveedor.baja(-2154);
		if (sol != -4)
			fail("Error: entidad inexistente debería devolver -4 y devuelve: " + res);
	}

	@Test
	public void ModificarProveedor() {
		// Proveedor inexistente
		TProveedor p = creaTProveedorRandom();
		Integer idP = asProveedor.alta(p);

		p.setID(-10);
		Integer res = asProveedor.modificar(p);
		if (res != -4)
			fail("Error: Modificar proveedor inexistente deberia retornar -4 y retorna " + res);
		// Nombre vacio proveedor
		p.setID(idP);
		asProveedor.baja(idP);
		// Proveedor inactivo
		res = asProveedor.modificar(p);
		if (res != -5)
			fail("Error: Modificar proveedor inactivo deberia retornar -5 y retorna " + res);

		TProveedor aux = creaTProveedorRandom();
		asProveedor.alta(aux);
		asProveedor.alta(p);
		p.setCIF(aux.getCIF());
		Integer result = asProveedor.alta(p);
		if (result != -3)
			fail("Error: no puedes dar de alta un proveedor con cif repetido");
		res = asProveedor.modificar(p);
		if (res != -6)
			fail("Error: Modificar proveedor con un CIF que ya existe deberia retornar -6 y retorna " + res);

	}

	@Test
	public void mostrarTest() {
		// Mostrar - proveedor inexistente
		TProveedor res = asProveedor.mostrar(-1);
		if (res.getID() != -4)
			fail("Error: Mostrar proveedor inexsitente deberia retornar -4 y retorna " + res);

		// Mostrar - sin error
		TProveedor proveedor = creaTProveedorRandom();
		Integer result = asProveedor.alta(proveedor);
		res = asProveedor.mostrar(result);
		if (res == null || res.getID() < 0)
			fail("Error: Mostrar proveedor sin fallos devuelve >= 0 y devuelve " + res.getID());

	}

	@Test
	public void listarTest() {
		// Listar - sin errores
		TProveedor p1 = creaTProveedorRandom();
		TProveedor p2 = creaTProveedorRandom();
		if(asProveedor.alta(p1) <= 0)
			fail("Error: Alta es un prerequisito de este test");
		if(asProveedor.alta(p2) <= 0)
			fail("Error: Alta es un prerequisito de este test");
		ArrayList<TProveedor> lista = asProveedor.listar();
		boolean p1Found = false, p2Found = false;
		for (TProveedor p : lista) {
			if (equals(p, p1))
				p1Found = true;
			else if (equals(p, p2))
				p2Found = true;
		}

		if (!p1Found || !p2Found)
			fail("Error: Listar deberia contener las entidades creadas. P1: " + p1Found + ", P2: " + p2Found);
	}

	@Test
	public void listarPorProductoTest() {

		// Creamos bebida
		TProductoBebida bebida = altaBebida();

		// FINISHED TESTS PROVEEDOR
		// Creamos proveedores
		Integer numProveedores = 3;
		ArrayList<TProveedor> proveedores = new ArrayList<>();
		for (int i = 0; i < numProveedores; i++)
			proveedores.add(altaProveedorAux());

		for (TProveedor p : proveedores) {
			Integer res = as.anadirProveedor(bebida.getID(), p.getID());
			if (res != 0)
				fail("Error: Anadir proveedor sin error deberia retornar 0 y retorna " + res);
		}

		// ListarPorProducto sin error
		ArrayList<TProveedor> lista = asProveedor.listarPorProducto(bebida.getID());
		boolean[] proveedoresFound = new boolean[numProveedores];

		for (TProveedor p : lista) {
			for (int i = 0; i < numProveedores; i++) {
				if (equals(p, proveedores.get(i))) {
					proveedoresFound[i] = true;
					break;
				}
			}
		}

		for (int i = 0; i < numProveedores; i++) {
			if (!proveedoresFound[i])
				fail("Error: El proveedor " + i + "-esimo no fue encontrado");
		}

		// Producto no activo
		as.baja(bebida.getID());
		lista = asProveedor.listarPorProducto(bebida.getID());
		if (lista.get(0).getID() != -8)
			fail("Error: Listar por producto, producto inactivo deberia retornar -8 y retorna " + lista.get(0).getID());

		// Producto no existente
		lista = asProveedor.listarPorProducto(-1);
		if (lista.get(0).getID() != -7)
			fail("Error: Listar por producto, producto inexistente deberia retornar -7 y retorna "
					+ lista.get(0).getID());

		// Producto lista vacia
		bebida = altaBebida();
		lista = asProveedor.listarPorProducto(bebida.getID());
		if (!lista.isEmpty())
			fail("Error: Listar Por producto sin proveedores vinculados deberia retornar lista vacia");

	}

}
