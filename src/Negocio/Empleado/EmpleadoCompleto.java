
package Negocio.Empleado;

import javax.persistence.Entity;
import javax.persistence.NamedQuery;

import java.io.Serializable;

@Entity
@NamedQuery(name = "EmpleadoCompleto.readAll", query = "select obj from EmpleadoCompleto obj order by obj.id asc")
public class EmpleadoCompleto extends Empleado implements Serializable {
	
	private static final long serialVersionUID = 0;
	private Integer eurosPM;
	private Integer horasExtra;

	public EmpleadoCompleto(String nombre, String dni, Integer sueldo, Integer telefono, Boolean activo, Integer eurosPM, Integer horasExtra){
		super(nombre,dni,sueldo,telefono,activo);
		this.eurosPM = eurosPM;
		this.horasExtra = horasExtra;
	}
	
	public EmpleadoCompleto() {}

	public Integer getEurosPM() {
		return this.eurosPM;
	}

	public void setEurosPM(Integer eurosPM) {
		this.eurosPM = eurosPM;
	}

	public Integer getHorasExtra() {
		return this.horasExtra;
	}

	public void setHorasExtra(Integer horasExtra) {
		this.horasExtra = horasExtra;
	}

	@Override
	public void calcularSueldo() {
		this.setSueldo(eurosPM+horasExtra*5);
	}
	
}