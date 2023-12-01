
package Negocio.Venta;

import java.util.HashMap;
import java.util.ArrayList;

public interface ASVenta {

	public Integer cerrar(TVenta venta, HashMap<Integer, TLineaVenta> carrito);
	public TVenta mostrar(Integer id);
	public ArrayList<TVenta> listar();
	public TVentaEnDetalle mostrarEnDetalle(Integer id);
	public ArrayList<TVenta> listarPorProducto(Integer idProducto);
	public ArrayList<TVenta> listarPorCliente(Integer idCliente);
	public ArrayList<TVenta> listarPorEmpleado(Integer idEmpleado);
	public Integer devolver(Integer idVenta, Integer idProducto, Integer uds);
	
}