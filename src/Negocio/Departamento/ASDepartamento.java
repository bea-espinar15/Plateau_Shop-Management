
package Negocio.Departamento;

import java.util.ArrayList;


public interface ASDepartamento {
	
	public Integer alta(TDepartamento dpto);
	public Integer baja(Integer id);
	public Integer modificar(TDepartamento dpto);
	public TDepartamento mostrar(Integer id);
	public ArrayList<TDepartamento> listar();
	
}