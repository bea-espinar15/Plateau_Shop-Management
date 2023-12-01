package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Random;

import org.junit.BeforeClass;
import org.junit.Test;

import Negocio.ASFactory.ASFactory;
import Negocio.Departamento.ASDepartamento;
import Negocio.Departamento.TDepartamento;

public class ASDepartamentoTest {

	private static ASDepartamento asDepartamento;
	private static Random random;

	@BeforeClass
	public static void beforeClass() {
		asDepartamento = ASFactory.getInstance().GetASDepartamento();
		random = new Random();
	}

	public TDepartamento creaTDepartamentoRandom() {
		TDepartamento departamento = new TDepartamento();
		departamento.setNombre("ASDepartamento departamento " + random.nextInt());
		return departamento;
	}

	@Test
	public void altaDepartamento() {
		TDepartamento dep = creaTDepartamentoRandom();
		Integer res = asDepartamento.alta(dep);
		if (res <= 0)
			fail("Error: Alta departamento sin errores deberia retornar ID > 0 y retorna " + res);
		res = asDepartamento.alta(dep);
		if (res != -3)
			fail("Error: Entidad repetida activa, debería devolver -3 y devuelve: " + res);
	}

	@Test
	public void bajaDepartamento() {
		// test de concurrencia con hebras (?)
		Integer res = asDepartamento.alta(creaTDepartamentoRandom());
		Integer sol = asDepartamento.baja(res);
		if (sol < 0)
			fail("Error: Baja departamento sin errores deberia retornar ID >= 0 y retorna " + res);
		sol = asDepartamento.baja(res);
		if (sol != -5)
			fail("Error: Entidad ya dada de baja debería devolver -5 y devuelve: " + res);
		sol = asDepartamento.baja(-2154);
		if (sol != -4)
			fail("Error: entidad inexistente debería devolver -4 y devuelve: " + res);
	}

	@Test
	public void mostrarTest() {
		// Mostrar - departamento inexistente
		TDepartamento res = asDepartamento.mostrar(-1);
		if (res.getID() != -4)
			fail("Error: Mostrar departamento inexsitente deberia retornar -4 y retorna " + res);

		// Mostrar - sin error
		TDepartamento dep = creaTDepartamentoRandom();
		Integer rdo = asDepartamento.alta(dep);
		res = asDepartamento.mostrar(rdo);
		if (res == null || res.getID() < 0)
			fail("Error: Mostrar departamento deberia retornar una entidad sin errores.");

	}

	@Test
	public void listarTest() {
		// Listar - sin errores
		TDepartamento dep1 = creaTDepartamentoRandom();
		TDepartamento dep2 = creaTDepartamentoRandom();
		Integer dep1ID = asDepartamento.alta(dep1);
		if (dep1ID <= 0)
			fail("Error: Alta es un prerequisito de este test");
		Integer dep2ID = asDepartamento.alta(dep2);
		if (dep2ID <= 0)
			fail("Error: Alta es un prerequisito de este test");
		ArrayList<TDepartamento> lista = asDepartamento.listar();
		boolean dep1Found = false, dep2Found = false;
		for (TDepartamento deps : lista) {
			if (deps.getID().equals(dep1ID)) {
				dep1Found = true;
			}
			if (deps.getID().equals(dep2ID)) {
				dep2Found = true;
			}
		}

		if (!dep1Found || !dep2Found)
			fail("Error: Listar deberia contener las entidades creadas. DEP1: " + dep1Found + ", DEP2: " + dep2Found);
	}

}
