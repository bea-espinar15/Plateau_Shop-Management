
package Negocio.ClienteJPA;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import java.io.Serializable;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.NamedQueries;
import Negocio.Venta.Venta;
import javax.persistence.OneToMany;
import javax.persistence.Version;

import java.util.ArrayList;

@Entity
@NamedQueries({
		@NamedQuery(name = "ClienteJPA.findBydni", query = "select obj from ClienteJPA obj where :dni = obj.dni "),
		@NamedQuery(name = "ClienteJPA.readAll", query = "select obj from ClienteJPA obj order by obj.id asc") })
public class ClienteJPA implements Serializable {

	private static final long serialVersionUID = 0;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String dni;
	private String nombre;
	private Boolean activo;
	@Version
	private Integer version;

	@OneToMany(mappedBy = "clienteJPA")
	private ArrayList<Venta> ventas;

	public ClienteJPA() {
	}

	public ClienteJPA(String dni, String nombre, ArrayList<Venta> ventas, Boolean activo) {
		this.dni = dni;
		this.nombre = nombre;
		this.ventas = ventas;
		this.activo = activo;
	}

	public Integer getID() {
		return this.id;
	}

	public void setID(Integer id) {
		this.id = id;
	}

	public String getDNI() {
		return this.dni;
	}

	public void setDNI(String dni) {
		this.dni = dni;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Boolean getActivo() {
		return this.activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public ArrayList<Venta> getVentas() {
		return this.ventas;
	}

	public void setVentas(ArrayList<Venta> ventas) {
		this.ventas = ventas;
	}
	
}