
package Negocio.Empleado;

public class TEmpleadoParcial extends TEmpleado {
	
	private Integer horas;
	private Integer eurosPH;

	public TEmpleadoParcial(Integer id, String nombre, String dni, Integer sueldo, Integer telefono, Boolean activo, Integer idDpto, Integer horas, Integer eurosPH) {
		super(id, nombre, dni, sueldo, telefono, activo, idDpto);
		this.horas = horas;
		this.eurosPH = eurosPH;
	}
	
	public TEmpleadoParcial(Integer id) {
		super(id);
	}
	
	public TEmpleadoParcial() {}
	
	public Integer getHoras() {
		return horas;
	}

	public void setHoras(Integer horas) {
		this.horas = horas;
	}

	public Integer getEurosPH() {
		return eurosPH;
	}

	public void setEurosPH(Integer eurosPH) {
		this.eurosPH = eurosPH;
	}
	
}