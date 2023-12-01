
package Negocio.Departamento;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import java.io.Serializable;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import Negocio.Empleado.Empleado;
import javax.persistence.OneToMany;
import javax.persistence.Version;

import java.util.ArrayList;

@Entity
@NamedQueries({
	@NamedQuery(name = "Departamento.readAll", query = "select obj from Departamento obj order by obj.id asc"),
	@NamedQuery(name = "Departamento.findByNombre", query = "select obj from Departamento obj where obj.nombre = :nombre") })
public class Departamento implements Serializable {

	private static final long serialVersionUID = 0;
	@Id@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Version
	private Integer version;
	private Boolean activo;
	private String nombre;
	private Integer nomina;	
	
	@OneToMany(mappedBy = "departamento")
	private ArrayList<Empleado> empleados;
	

	public Departamento() {}

	public Integer getID() {
		return this.id;
	}

	public void setID(Integer id) {
		this.id = id;
	}

	public Boolean getActivo() {
		return this.activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Integer getNomina() {
		return this.nomina;
	}

	public void setNomina(Integer nomina) {
		this.nomina = nomina;
	}

	public ArrayList<Empleado> getEmpleados() {
		return this.empleados;
	}

	public void setEmpleados(ArrayList<Empleado> empleados) {
		this.empleados = empleados;
	}

}