package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Random;

import org.junit.BeforeClass;
import org.junit.Test;

import Negocio.ASFactory.ASFactory;
import Negocio.ClienteJPA.ASClienteJPA;
import Negocio.ClienteJPA.TClienteJPA;

public class ASClienteJPATest {

	private static ASClienteJPA asCliente;
	private static Random random;

	@BeforeClass
	public static void beforeClass() {
		asCliente = ASFactory.getInstance().GetASClienteJPA();
		random = new Random();
	}

	private boolean equals(TClienteJPA depA, TClienteJPA depB) {
		return depA.getActivo() == depB.getActivo() && depA.getID().equals(depB.getID())
				&& depA.getNombre().equals(depB.getNombre()) && depA.getDNI().equals(depB.getDNI());
	}

	public TClienteJPA creaTClienteJPARandom() {

		TClienteJPA tcliente = new TClienteJPA();
		tcliente.setNombre("ClienteJPA test" + random.nextInt());
		tcliente.setDNI("ClienteJPA " + random.nextInt());
		tcliente.setActivo(true);

		return tcliente;
	}

	@Test
	public void altaClienteJPATest() {
		// Alta - sin errores
		TClienteJPA cl = creaTClienteJPARandom();
		Integer res = asCliente.alta(cl);
		if (res <= 0)
			fail("Error: Alta ClienteJPA sin errores deberia retornar ID > 0 y retorna " + res);
		
		// Alta - sin error entidad inactiva
		// Primero damos de baja
		res = asCliente.baja(res);
		if(res != 0)
			fail("Error: Baja es un prequisito de este test");
		res = asCliente.alta(cl);
		if(res <= 0)
			fail("Error: Alta ClienteJPA reactivando deberia retornar ID > 0 y retorna " + res);
		
		// Alta - entidad existente
		res = asCliente.alta(cl);
		if (res != -3)
			fail("Error: Entidad repetida activa, debería devolver -3 y devuelve: " + res);
	}

	@Test
	public void bajaClienteJPATest() {
		// Baja - sin errores
		// Primero se da de alta
		Integer res = asCliente.alta(creaTClienteJPARandom());
		if(res <= 0)
			fail("Error: Alta cliente es un prequisito de este test");
		
		Integer sol = asCliente.baja(res);
		if (sol < 0)
			fail("Error: Baja clienteJPA sin errores deberia retornar ID >= 0 y retorna " + res);
		
		// Baja - entidad inactiva
		sol = asCliente.baja(res);
		if (sol != -5)
			fail("Error: Entidad ya dada de baja debería devolver -5 y devuelve: " + res);
		
		// Baja - entidad inexistente
		sol = asCliente.baja(-1);
		if (sol != -4)
			fail("Error: entidad inexistente debería devolver -4 y devuelve: " + res);
	}

	@Test
	public void mostrarClienteJPATest() {
		// Mostrar - error entidad inexistente
		TClienteJPA res = asCliente.mostrar(-1);
		if (res.getID() != -4)
			fail("Error: Mostrar clienteJPA inexistente deberia retornar -4 y retorna " + res);

		// Mostrar - sin errores
		TClienteJPA cl = creaTClienteJPARandom();
		Integer id = asCliente.alta(cl);
		cl.setID(id);
		if(id <= 0)
			fail("Error: Alta es un prerequisito de este test");
		res = asCliente.mostrar(id);
		if (!equals(cl, (TClienteJPA) res))
			fail("Error: Mostrar clienteJPA deberia retornar una entidad identica");
	}

	@Test
	public void listarClienteJPATest() {
		// Listar - sin errores
		// Creamos n Clientes
		Integer numClientes = 2;
		TClienteJPA[] clientes = new TClienteJPA[numClientes];
		for(int i = 0; i < numClientes; i++){
			clientes[i] = creaTClienteJPARandom();
			Integer id = asCliente.alta(clientes[i]);
			if(id <= 0)
				fail("Error: Alta es un prerequisito de este test");
			clientes[i].setID(id);
		}

		ArrayList<TClienteJPA> lista = asCliente.listar();
		Boolean[] found = new Boolean[numClientes];
		for (TClienteJPA cl : lista) {
			for(int i = 0; i < numClientes; i++){
				if(equals(cl, clientes[i])){
					found[i] = true;
					break;
				}
			}
		}

		for(int i = 0; i < numClientes; i++) {
			if(!found[i])
				fail("Error: El cliente " + i + " no fue encontrada");
		}	
	}

	@Test
	public void modificarClienteJPATest() {
		// Modificar - entidad inexistente
		TClienteJPA cl = new TClienteJPA();
		cl.setID(-1);
		Integer res = asCliente.modificar(cl);
		if(res != -4)
			fail("Error: Modificar entidad inexistente deberia retornar -4 y retorna " + res);
		
		// Modificar - entidad inactiva
		// Primero damos de alta
		cl = creaTClienteJPARandom();
		res = asCliente.alta(cl);
		cl.setID(res);
		if(res <= 0)
			fail("Error: Alta es un prerequisito de este test");
		// Damos de baja
		res = asCliente.baja(cl.getID());
		if(res != 0)
			fail("Error: Baja es un prerequisito de este test");
		// Modificamos
		res = asCliente.modificar(cl);
		if(res != -5)
			fail("Error: Modificar entidad inexistente deberia retornar -4 y retorna " + res);
		
		// Modificar - DNI repetido
		// Damos de alta 1er cliente
		cl = creaTClienteJPARandom();
		res = asCliente.alta(cl);
		cl.setID(res);
		if(res <= 0)
			fail("Error: Alta es un prerequisito de este test");
		// Damos de alta 2do cliente
		TClienteJPA cl2 = creaTClienteJPARandom();
		res = asCliente.alta(cl2);
		cl2.setID(res);
		if(res <= 0)
			fail("Error: Alta es un prerequisito de este test");
		// Modificamos
		cl2.setDNI(cl.getDNI());
		res = asCliente.modificar(cl2);
		if(res != -6)
			fail("Error:  Modificar entidad duplicada deberia retornar -6 y retorna " + res);
	}

}
