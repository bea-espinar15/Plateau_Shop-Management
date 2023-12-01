package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Random;

import org.junit.BeforeClass;
import org.junit.Test;

import Negocio.ASFactory.ASFactory;
import Negocio.Departamento.ASDepartamento;
import Negocio.Departamento.TDepartamento;
import Negocio.Empleado.ASEmpleado;
import Negocio.Empleado.TEmpleado;
import Negocio.Empleado.TEmpleadoCompleto;
import Negocio.Empleado.TEmpleadoParcial;

public class ASEmpleadoTest {
	private static Random random;
	private static ASEmpleado as;
	private static ASDepartamento asDept;

	@BeforeClass
	public static void beforeClass() {
		as = ASFactory.getInstance().GetASEmpleado();
		asDept = ASFactory.getInstance().GetASDepartamento();
		random = new Random();
	}

	// Funciones auxiliares
	private TEmpleadoParcial crearTParcial() {
		TDepartamento d = new TDepartamento();
		d.setNombre("Dept " + random.nextInt());
		Integer res = asDept.alta(d);
		if (res <= 0)
			fail("Error: Alta dept. es un prequisito de este test");

		TEmpleadoParcial e = new TEmpleadoParcial();
		e.setDNI("ASEmpleadoTest " + random.nextInt());
		e.setEurosPH(2);
		e.setHoras(2);
		e.setIDDpto(res);
		e.setNombre("Nombre");
		e.setTelefono(111222333);
		return e;
	}

	private TEmpleadoCompleto crearTCompleto() {
		TDepartamento d = new TDepartamento();
		d.setNombre("Dept " + random.nextInt());
		Integer res = asDept.alta(d);
		if (res <= 0)
			fail("Error: Alta dept. es un prequisito de este test");

		TEmpleadoCompleto e = new TEmpleadoCompleto();
		e.setDNI("ASEmpleadoTest " + random.nextInt());
		e.setHorasExtra(1);
		e.setEurosPM(2);
		e.setIDDpto(res);
		e.setNombre("Nombre");
		e.setTelefono(111222333);
		return e;
	}

	private TEmpleadoParcial altaEmpleadoParcial() {
		TEmpleadoParcial e = crearTParcial();
		Integer res = as.alta(e);
		e.setID(res);
		if (res <= 0)
			fail("Error: Alta es un prequisito de este test");
		return e;
	}

	private TEmpleadoCompleto altaEmpleadoCompleto() {
		TEmpleadoCompleto e = crearTCompleto();
		Integer res = as.alta(e);
		e.setID(res);
		if (res <= 0)
			fail("Error: Alta es un prequisito de este test");
		return e;
	}

	@Test
	public void altaParcialTest() {
		// Alta - sin error
		TEmpleadoParcial e = crearTParcial();
		Integer res = as.alta(e);
		e.setID(res);
		if (res <= 0)
			fail("Error: Alta sin errores deberia retornar ID > 0 y retorna " + res);

		// Alta - entidad duplicada
		res = as.alta(e);
		if (res != -3)
			fail("Error: Alta entidad activa duplicada deberia retornar -3 y retorna " + res);

		// Alta - reactivar entidad sin modificar
		// Damos de baja
		res = as.baja(e.getID());
		if (res != 0)
			fail("Error: Baja es un prequisito de este test");
		res = as.alta(e);
		if (res <= 0)
			fail("Error: Alta reactivando deberia retornar ID > 0 y retorna " + res);

		// Alta - reactivar entidad modificando
		// Damos de baja
		res = as.baja(e.getID());
		if (res != 0)
			fail("Error: Baja es un prequisito de este test");
		e.setEurosPH(69);
		e.setTelefono(111222444);
		e.setHoras(420);
		e.setNombre("NewName " + random.nextInt());
		e.setDNI("DNI " + random.nextInt());
		res = as.alta(e);
		if (res <= 0)
			fail("Error: Alta reactivando deberia retornar ID > 0 y retorna " + res);

		// Alta - departamento inexistente
		e = crearTParcial();
		e.setIDDpto(-1);
		res = as.alta(e);
		if (res != -7)
			fail("Error: Alta departamento inexistente deberia retornar -7 y retorna " + res);
	}

	@Test
	public void altaCompletoTest() {
		// Alta - sin error
		TEmpleadoCompleto e = crearTCompleto();
		Integer res = as.alta(e);
		e.setID(res);
		if (res <= 0)
			fail("Error: Alta sin errores deberia retornar ID > 0 y retorna " + res);

		// Alta - entidad duplicada
		res = as.alta(e);
		if (res != -3)
			fail("Error: Alta entidad activa duplicada deberia retornar -3 y retorna " + res);

		// Alta - reactivar entidad sin modificar
		// Damos de baja
		res = as.baja(e.getID());
		if (res != 0)
			fail("Error: Baja es un prequisito de este test");
		res = as.alta(e);
		if (res <= 0)
			fail("Error: Alta reactivando deberia retornar ID > 0 y retorna " + res);

		// Alta - reactivar entidad modificando
		// Damos de baja
		res = as.baja(e.getID());
		if (res != 0)
			fail("Error: Baja es un prequisito de este test");
		e.setHorasExtra(69);
		e.setTelefono(111222444);
		e.setEurosPM(420);
		e.setNombre("NewName " + random.nextInt());
		e.setDNI("DNI " + random.nextInt());
		res = as.alta(e);
		if (res <= 0)
			fail("Error: Alta reactivando deberia retornar ID > 0 y retorna " + res);

		// Alta - departamento inexistente
		e = crearTCompleto();
		e.setIDDpto(-1);
		res = as.alta(e);
		if (res != -7)
			fail("Error: Alta departamento inexistente deberia retornar -7 y retorna " + res);
	}

	@Test
	public void bajaTest() {
		// Baja - entidad inexistente
		Integer res = as.baja(-1);
		if (res != -4)
			fail("Error: Baja entidad inexistente deberia retornar -4 y retorna " + res);

		// Baja - sin errores
		TEmpleadoParcial e = altaEmpleadoParcial(); // Damos de alta
		res = as.baja(e.getID());
		if (res != 0)
			fail("Error: Baja sin error deberia retornar 0 y retorna " + res);

		// Baja - entidad inactiva
		res = as.baja(e.getID());
		if (res != -5)
			fail("Error: Baja entidad inactiva deberia retornar -5 y retorna " + res);
	}

	@Test
	public void modificarTest() {
		TEmpleado completo = altaEmpleadoCompleto();
		Integer idCompleto = completo.getID();
		TEmpleado parcial = altaEmpleadoParcial();

		// Entidad inexistente
		completo.setID(-10);
		Integer failure = as.modificar(completo);
		if (failure >= 0)
			fail("La entidad modificada no existe, deberia devolver -4 y devuelve " + failure);

		// Sin error
		completo.setID(idCompleto);
		failure = as.modificar(completo);
		if (failure != 0)
			fail("la entidad deberia poder modificarse, deberia devolver 0 y devuelve " + failure);
		
		// Entidad inactiva
		failure = as.baja(completo.getID());
		failure = as.modificar(completo);
		if (failure >= 0)
			fail("no se puede modificar un empleado inactivo, deberia devolver -5 y devuelve " + failure);
		
		// DNI conflictivo
		completo.setDNI(parcial.getDNI());
		failure = as.modificar(completo);
		if (failure >= 0)
			fail("La entidad modificada tiene el id de una entidad existente, deberia devolver -9 y devuelve "
					+ failure);

	}

	@Test
	public void mostrarTest() {
		// Mostrar - entidad inexistente
		TEmpleado res = as.mostrar(-1);
		if (res.getID() != -4)
			fail("Error: Mostrar entidad inexistente deberia retornar -4 y retorna " + res.getID());

		// Mostrar Parcial - sin error
		TEmpleadoParcial ep = altaEmpleadoParcial(); // Damos de alta
		res = as.mostrar(ep.getID());
		if (res.getID() <= 0)
			fail("Error: Mostrar parcial sin error deberia retornar ID > 0 y retorna " + res.getID());

		// Mostrar Completo - sin error
		TEmpleadoCompleto ec = altaEmpleadoCompleto(); // Damos de alta
		res = as.mostrar(ec.getID());
		if (res.getID() <= 0)
			fail("Error: Mostrar completo sin error deberia retornar ID > 0 y retorna " + res.getID());
	}

	@Test
	public void listarTest() {
		Integer nParcial = 2;
		Integer nCompleto = 2;

		TEmpleado[] empleados = new TEmpleado[nParcial + nCompleto];

		for (int i = 0; i < nParcial; i++) {
			empleados[i] = altaEmpleadoParcial();
		}
		for (int i = nParcial; i < nParcial + nCompleto; i++) {
			empleados[i] = altaEmpleadoCompleto();
		}

		ArrayList<TEmpleado> res = as.listar();

		Boolean[] found = new Boolean[nParcial + nCompleto];
		for (TEmpleado e : res) {
			for (int i = 0; i < nParcial + nCompleto; i++) {
				if (e.getID().equals(empleados[i].getID())) {
					found[i] = true;
					break;
				}
			}
		}

		for (int i = 0; i < nParcial + nCompleto; i++) {
			if (!found[i])
				fail("Error: Listar no contiene la entidad " + i);
		}
	}

	@Test
	public void listarCompletoTest() {
		Integer nCompleto = 2;

		TEmpleado[] empleados = new TEmpleado[nCompleto];

		for (int i = 0; i < nCompleto; i++) {
			empleados[i] = altaEmpleadoCompleto();
		}

		ArrayList<TEmpleadoCompleto> res = as.listarCompleto();

		Boolean[] found = new Boolean[nCompleto];
		for (TEmpleadoCompleto e : res) {
			for (int i = 0; i < nCompleto; i++) {
				if (e.getID().equals(empleados[i].getID())) {
					found[i] = true;
					break;
				}
			}
		}

		for (int i = 0; i < nCompleto; i++) {
			if (!found[i])
				fail("Error: ListarCompleto no contiene la entidad " + i);
		}
	}

	@Test
	public void listarParcialTest() {
		Integer nParcial = 2;

		TEmpleado[] empleados = new TEmpleado[nParcial];

		for (int i = 0; i < nParcial; i++) {
			empleados[i] = altaEmpleadoParcial();
		}

		ArrayList<TEmpleadoParcial> res = as.listarParcial();

		Boolean[] found = new Boolean[nParcial];
		for (TEmpleadoParcial e : res) {
			for (int i = 0; i < nParcial; i++) {
				if (e.getID().equals(empleados[i].getID())) {
					found[i] = true;
					break;
				}
			}
		}

		for (int i = 0; i < nParcial; i++) {
			if (!found[i])
				fail("Error: ListarParcial no contiene la entidad " + i);
		}
	}

	@Test
	public void listarPorDepartamentoTest() {
		Integer nParcial = 2;
		Integer nCompleto = 2;

		TEmpleado[] empleados = new TEmpleado[nParcial + nCompleto];

		// Creamos el primer empelado para tener departamento creado
		empleados[0] = altaEmpleadoParcial();
		Integer idDpto = empleados[0].getIDDpto();

		for (int i = 1; i < nParcial; i++) {
			empleados[i] = crearTParcial();
			empleados[i].setIDDpto(idDpto);
			Integer res = as.alta(empleados[i]);
			empleados[i].setID(res);
			if (res <= 0)
				fail("Error: Alta es un prequisito de este test");
		}
		for (int i = nParcial; i < nParcial + nCompleto; i++) {
			empleados[i] = crearTCompleto();
			empleados[i].setIDDpto(idDpto);
			Integer res = as.alta(empleados[i]);
			empleados[i].setID(res);
			if (res <= 0)
				fail("Error: Alta es un prequisito de este test");
		}

		ArrayList<TEmpleado> res = as.listarPorDepartamento(idDpto);
		Boolean[] found = new Boolean[nParcial + nCompleto];
		for (TEmpleado e : res) {
			for (int i = 0; i < nParcial + nCompleto; i++) {
				if (e.getID().equals(empleados[i].getID())) {
					found[i] = true;
					break;
				}
			}
		}

		for (int i = 0; i < nParcial + nCompleto; i++) {
			if (!found[i])
				fail("Error: Listar no contiene la entidad " + i);
		}
	}
}
