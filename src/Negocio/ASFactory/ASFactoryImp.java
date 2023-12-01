
package Negocio.ASFactory;

import Negocio.ClienteJPA.ASClienteJPA;
import Negocio.ClienteJPA.ASClienteJPAImp;
import Negocio.Departamento.ASDepartamento;
import Negocio.Departamento.ASDepartamentoImp;
import Negocio.Empleado.ASEmpleado;
import Negocio.Empleado.ASEmpleadoImp;
import Negocio.Producto.ASProducto;
import Negocio.Producto.ASProductoImp;
import Negocio.Proveedor.ASProveedor;
import Negocio.Proveedor.ASProveedorImp;
import Negocio.Venta.ASVenta;
import Negocio.Venta.ASVentaImp;

public class ASFactoryImp extends ASFactory {

	@Override
	public ASVenta GetASVenta() {
		return new ASVentaImp();
	}
	@Override
	public ASDepartamento GetASDepartamento() {
		return new ASDepartamentoImp();
	}
	@Override
	public ASEmpleado GetASEmpleado() {
		return new ASEmpleadoImp();
	}
	@Override
	public ASClienteJPA GetASClienteJPA() {
		return new ASClienteJPAImp();
	}

	@Override
	public ASProducto GetASProducto() {
		return new ASProductoImp();
	}
	@Override
	public ASProveedor GetASProveedor() {
		return new ASProveedorImp();
	}
	
}