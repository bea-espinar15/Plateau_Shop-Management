package Negocio.Empleado;

import java.util.ArrayList;

public interface ASEmpleado {
	
	public Integer alta(TEmpleado empleado);
	public Integer baja(Integer id);
	public Integer modificar(TEmpleado empleado);
	public TEmpleado mostrar(Integer id);
	public ArrayList<TEmpleado> listar();
	public ArrayList<TEmpleadoCompleto> listarCompleto();
	public ArrayList<TEmpleadoParcial> listarParcial();
	public ArrayList<TEmpleado> listarPorDepartamento(Integer idDpto);
	
}