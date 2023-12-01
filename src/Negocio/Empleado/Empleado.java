
package Negocio.Empleado;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import java.io.Serializable;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.NamedQueries;
import Negocio.Departamento.Departamento;
import javax.persistence.ManyToOne;
import Negocio.Venta.Venta;
import javax.persistence.OneToMany;
import javax.persistence.Version;
import java.util.ArrayList;
import javax.persistence.InheritanceType;
import javax.persistence.Inheritance;

@Inheritance(strategy = InheritanceType.JOINED)
@Entity
@NamedQueries({
		@NamedQuery(name = "Empleado.findBydni", query = "select obj from Empleado obj where :dni = obj.dni "),
		@NamedQuery(name = "Empleado.readAll", query = "select obj from Empleado obj order by obj.id asc") })
public abstract class Empleado implements Serializable {
	
	private static final long serialVersionUID = 0;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String nombre;
	private String dni;
	private Integer sueldo;
	private Integer telefono;
	private Boolean activo;
	@ManyToOne(fetch = FetchType.LAZY)
	private Departamento departamento;
	@OneToMany(mappedBy = "empleado")
	private ArrayList<Venta> ventas;
	@Version
	private Integer version;
	

	public Empleado(String nombre,String dni,Integer sueldo,Integer telefono,Boolean activo){
		this.nombre = nombre;
		this.dni = dni;
		this.sueldo = sueldo;
		this.telefono = telefono;
		this.activo = activo;
	}
	
	public Empleado() {}

	public Integer getID() {
		return this.id;
	}

	public void setID(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDNI() {
		return this.dni;
	}

	public void setDNI(String dni) {
		this.dni = dni;
	}

	public Integer getSueldo() {
		return this.sueldo;
	}

	public void setSueldo(Integer sueldo) {
		this.sueldo = sueldo;
	}

	public Integer getTelefono() {
		return this.telefono;
	}

	public void setTelefono(Integer telefono) {
		this.telefono = telefono;
	}

	public Departamento getDepartamento() {
		return this.departamento;
	}

	public void setDepartamento(Departamento dept) {
		this.departamento = dept;
	}

	public ArrayList<Venta> getVentas() {
		return this.ventas;
	}

	public void setVentas(ArrayList<Venta> ventas) {
		this.ventas = ventas;
	}

	public Boolean getActivo() {
		return this.activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}
	public abstract void calcularSueldo();
	
	
}