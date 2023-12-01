
package Negocio.ClienteJPA;

import java.util.ArrayList;


public interface ASClienteJPA {
	
	public Integer alta(TClienteJPA cliente);
	public Integer baja(Integer id_cliente);
	public Integer modificar(TClienteJPA cliente);	
	public TClienteJPA mostrar(Integer id_cliente);
	public ArrayList<TClienteJPA> listar();
	
}