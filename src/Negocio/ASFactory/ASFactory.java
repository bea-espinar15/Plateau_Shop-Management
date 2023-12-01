
package Negocio.ASFactory;

import Negocio.ClienteJPA.ASClienteJPA;
import Negocio.Departamento.ASDepartamento;
import Negocio.Empleado.ASEmpleado;
import Negocio.Producto.ASProducto;
import Negocio.Proveedor.ASProveedor;
import Negocio.Venta.ASVenta;

public abstract class ASFactory {

	private static ASFactory instance;

	public static synchronized ASFactory getInstance() {
		if (instance == null) {
			instance = new ASFactoryImp();
		}
		return instance;
	}
	
	public abstract ASVenta GetASVenta();
	public abstract ASDepartamento GetASDepartamento();
	public abstract ASEmpleado GetASEmpleado();
	public abstract ASClienteJPA GetASClienteJPA();
	public abstract ASProducto GetASProducto();
	public abstract ASProveedor GetASProveedor();

}