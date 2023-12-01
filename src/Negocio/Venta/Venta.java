
package Negocio.Venta;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import java.io.Serializable;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import java.util.Date;
import Negocio.ClienteJPA.ClienteJPA;
import javax.persistence.ManyToOne;
import Negocio.Empleado.Empleado;
import javax.persistence.OneToMany;
import javax.persistence.Version;

import java.util.ArrayList;

@Entity
@NamedQuery(name = "Venta.readAll", query = "select obj from Venta obj order by obj.id asc")
public class Venta implements Serializable {

	private static final long serialVersionUID = 0;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Version
	private Integer Version;
	private Date fecha;
	private Integer precioTotal;
	private String metodoPago;

	@ManyToOne(fetch = FetchType.LAZY)
	private ClienteJPA clienteJPA;

	@ManyToOne(fetch = FetchType.LAZY)
	private Empleado empleado;

	@OneToMany(mappedBy = "venta")
	private ArrayList<LineaVenta> lineasVenta;

	public Venta() {}

	public Integer getID() {
		return this.id;
	}

	public ArrayList<LineaVenta> getLineasVenta() {
		return this.lineasVenta;
	}

	public ClienteJPA getClienteJPA() {
		return this.clienteJPA;
	}

	public Empleado getEmpleado() {
		return this.empleado;
	}

	public Date getFecha() {
		return this.fecha;
	}

	public Integer getPrecioTotal() {
		return this.precioTotal;
	}

	public String getMetodoPago() {
		return this.metodoPago;
	}

	public void setID(Integer id) {
		this.id = id;
	}

	public void setClienteJPA(ClienteJPA cliente) {
		this.clienteJPA = cliente;
	}

	public void setEmpleado(Empleado empleado) {
		this.empleado = empleado;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public void setPrecioTotal(Integer precio) {
		this.precioTotal = precio;
	}

	public void setMetodoPago(String metodoPago) {
		this.metodoPago = metodoPago;
	}

	public void setLineasVenta(ArrayList<LineaVenta> lineasVenta) {
		this.lineasVenta = lineasVenta;
	}
	
}