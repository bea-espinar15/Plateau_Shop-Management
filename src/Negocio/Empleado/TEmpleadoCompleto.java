
package Negocio.Empleado;

public class TEmpleadoCompleto extends TEmpleado {
	
	private Integer eurosPM;
	private Integer horasExtra;
	
	public TEmpleadoCompleto(Integer id, String nombre, String dni, Integer sueldo, Integer telefono, Boolean activo, Integer idDpto, Integer eurosPM, Integer horasExtra) {
		super(id, nombre, dni, sueldo, telefono, activo, idDpto);
		this.eurosPM = eurosPM;
		this.horasExtra = horasExtra;
	}
	
	public TEmpleadoCompleto(Integer id) {
		super(id);
	}
	
	public TEmpleadoCompleto() {}
	
	public Integer getEurosPM() {
		return eurosPM;
	}

	public void setEurosPM(Integer eurosPM) {
		this.eurosPM = eurosPM;
	}

	public Integer getHorasExtra() {
		return horasExtra;
	}

	public void setHorasExtra(Integer horasExtra) {
		this.horasExtra = horasExtra;
	}
	
}