
package Negocio.Proveedor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.io.Serializable;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Version;
import Negocio.Producto.Producto;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import java.util.HashSet;

@Entity
@NamedQueries({
		@NamedQuery(name = "Proveedor.findBycif", query = "select obj from Proveedor obj where :cif = obj.cif "),
		@NamedQuery(name = "Proveedor.readAll", query = "select obj from Proveedor obj order by obj.id asc") })
public class Proveedor implements Serializable {

	private static final long serialVersionUID = 0;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Version
	private Integer version;
	private Integer telefono;
	private String nombre;
	private String cif;
	private Boolean activo;

	@ManyToMany(mappedBy = "proveedores")
	private HashSet<Producto> productos;

	public Proveedor(HashSet<Producto> productos, Integer telefono, String nombre, String cif, Boolean activo) {
		this.productos = productos;
		this.telefono = telefono;
		this.nombre = nombre;
		this.cif = cif;
		this.activo = activo;
	}

	public Proveedor() {}

	public Proveedor(Integer id) {
		this.id = id;
	}

	public Integer getID() {
		return this.id;
	}

	public Integer getTelefono() {
		return this.telefono;
	}

	public String getNombre() {
		return this.nombre;
	}

	public String getCIF() {
		return this.cif;
	}

	public Boolean getActivo() {
		return this.activo;
	}

	public void setID(Integer id) {
		this.id = id;
	}

	public void setTelefono(Integer tlf) {
		this.telefono = tlf;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setCIF(String cif) {
		this.cif = cif;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public HashSet<Producto> getProductos() {
		return this.productos;
	}

	public void setProductos(HashSet<Producto> productos) {
		this.productos = productos;
	}
	
}