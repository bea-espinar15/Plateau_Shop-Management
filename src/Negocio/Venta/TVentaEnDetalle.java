
package Negocio.Venta;

import Negocio.ClienteJPA.TClienteJPA;
import Negocio.Empleado.TEmpleado;
import Negocio.Producto.TProducto;
import java.util.ArrayList;

public class TVentaEnDetalle {
	
	private TVenta tVenta;
	private ArrayList<TLineaVenta> tLineasVenta;
	private TClienteJPA tClienteJPA;
	private TEmpleado tEmpleado;
	private ArrayList<TProducto> tProductos;
	
	public TVentaEnDetalle() {}
	
	public TVentaEnDetalle(TVenta tVenta, ArrayList<TLineaVenta> tLineasVenta, TClienteJPA tClienteJPA, TEmpleado tEmpleado, ArrayList<TProducto> tProductos) {
		this.tVenta = tVenta;
		this.tLineasVenta = tLineasVenta;
		this.tClienteJPA = tClienteJPA;
		this.tEmpleado = tEmpleado;
		this.tProductos = tProductos;
	}
	
	public TClienteJPA getTClienteJPA() {
		return this.tClienteJPA;
	}

	public void setTClienteJPA(TClienteJPA TClienteJPA) {
		this.tClienteJPA = TClienteJPA;
	}

	public TEmpleado getTEmpleado() {
		return this.tEmpleado;
	}

	public void setTEmpleado(TEmpleado TEmpleado) {
		this.tEmpleado = TEmpleado;
	}

	public TVenta getTVenta() {
		return this.tVenta;
	}

	public void setTVenta(TVenta TVenta) {
		this.tVenta = TVenta;
	}

	public ArrayList<TProducto> getTProductos() {
		return this.tProductos;
	}

	public void setTProductos(ArrayList<TProducto> tProductos) {
		this.tProductos = tProductos;
	}

	public ArrayList<TLineaVenta> getTLineasVenta() {
		return this.tLineasVenta;
	}

	public void setTLineasVenta(ArrayList<TLineaVenta> tLineasVenta) {
		this.tLineasVenta = tLineasVenta;
	}
	
}