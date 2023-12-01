
package Negocio.Producto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import java.io.Serializable;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.NamedQueries;
import Negocio.Proveedor.Proveedor;
import javax.persistence.ManyToMany;
import Negocio.Venta.LineaVenta;
import javax.persistence.OneToMany;
import javax.persistence.Version;

import java.util.ArrayList;
import java.util.HashSet;

import javax.persistence.InheritanceType;
import javax.persistence.Inheritance;


@Inheritance(strategy = InheritanceType.JOINED)
@Entity
@NamedQueries({
		@NamedQuery(name = "Producto.findBynombre", query = "select obj from Producto obj where obj.nombre = :nombre"),
		@NamedQuery(name = "Producto.readAll", query = "select obj from Producto obj order by obj.id asc")	})
public abstract class Producto implements Serializable {
	
	private static final long serialVersionUID = 1;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private Boolean activo;
	private String nombre;
	private Integer precio_actual;
	private Integer stock;
	@Version
	private Integer version;
	
	@ManyToMany
	private HashSet<Proveedor> proveedores;
	
	@OneToMany(mappedBy = "producto")
	private ArrayList<LineaVenta> lineasVenta;
	
	public Producto() {}
	
	public void setID(Integer id) {
		this.id = id;
	}

	public Integer getID() {
		return this.id;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public Boolean getActivo() {
		return this.activo;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setPrecioActual(Integer precio) {
		this.precio_actual = precio;
	}

	public Integer getPrecioActual() {
		return this.precio_actual;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public Integer getStock() {
		return this.stock;
	}

	public void setProveedores(HashSet<Proveedor> proveedores) {
		this.proveedores = proveedores;
	}

	public HashSet<Proveedor> getProveedores() {
		return this.proveedores;
	}

	public ArrayList<LineaVenta> getLineasVenta() {
		return this.lineasVenta;
	}

	public void setLineasVenta(ArrayList<LineaVenta> lineasVenta) {
		this.lineasVenta = lineasVenta;
	}
	
}