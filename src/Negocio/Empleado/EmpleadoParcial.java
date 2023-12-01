package Negocio.Empleado;

import javax.persistence.Entity;
import javax.persistence.NamedQuery;

import java.io.Serializable;

@Entity
@NamedQuery(name = "EmpleadoParcial.readAll", query = "select obj from EmpleadoParcial obj order by obj.id asc")
public class EmpleadoParcial extends Empleado implements Serializable {
	
	private static final long serialVersionUID = 0;
	private Integer horas;
	private Integer eurosPH;

	public EmpleadoParcial(String nombre, String dni, Integer sueldo, Integer telefono, Boolean activo, Integer horas, Integer eurosPH){
		super(nombre,dni,sueldo,telefono,activo);
		this.horas = horas;
		this.eurosPH = eurosPH;
	}
	
	public EmpleadoParcial() {}

	public Integer getHoras() {
		return this.horas;
	}

	public void setHoras(Integer horas) {
		this.horas = horas;
	}

	public Integer getEurosPH() {
		return this.eurosPH;
	}

	public void setEurosPH(Integer eurosPH) {
		this.eurosPH = eurosPH;
	}

	@Override
	public void calcularSueldo() {
		this.setSueldo(horas*20*eurosPH);
	}
}