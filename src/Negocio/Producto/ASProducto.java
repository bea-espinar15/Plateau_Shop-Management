
package Negocio.Producto;

import java.util.ArrayList;

public interface ASProducto {
	
	public Integer alta(TProducto producto);
	public Integer baja(Integer id);
	public Integer modificar(TProducto producto);
	public TProducto mostrar(Integer id);
	public ArrayList<TProducto> listar();
	public ArrayList<TProductoBebida> listarBebidas();
	public ArrayList<TProductoComida> listarComidas();
	public ArrayList<TProducto> listarPorProveedor(Integer idProveedor);
	public ArrayList<TProducto> listarPorVenta(Integer idVenta);
	public Integer anadirProveedor(Integer idProducto, Integer idProveedor);
	public Integer quitarProveedor(Integer idProducto, Integer idProveedor);
	
}