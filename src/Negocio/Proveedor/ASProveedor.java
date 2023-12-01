package Negocio.Proveedor;

import java.util.ArrayList;

public interface ASProveedor {
	
	public Integer alta(TProveedor proveedor);
	public Integer baja(Integer id);
	public Integer modificar(TProveedor proveedor);
	public TProveedor mostrar(Integer id);
	public ArrayList<TProveedor> listar();
	public ArrayList<TProveedor> listarPorProducto(Integer idProducto);
	
}