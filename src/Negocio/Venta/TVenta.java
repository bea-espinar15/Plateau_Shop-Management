
package Negocio.Venta;

import java.util.Date;

public class TVenta {
	
	private Integer id;
	private Integer idClienteJPA;
	private Integer idEmpleado;
	private Date fecha;
	private Integer precioTotal;
	private String metodoPago;

	public TVenta() {}
	
	public TVenta(Integer id){
		this.id = id;
	}
	
	public TVenta(Integer id, Integer idClienteJPA, Integer idEmpleado, Date fecha, Integer precioTotal, String metodoPago) {
		this.id = id;
		this.idClienteJPA = idClienteJPA;
		this.idEmpleado = idEmpleado;
		this.fecha = fecha;
		this.precioTotal = precioTotal;
		this.metodoPago = metodoPago;
	}
	
	public Integer getID() {
		return this.id;
	}

	public Integer getIDClienteJPA() {
		return this.idClienteJPA;
	}

	public Integer getIDEmpleado() {
		return this.idEmpleado;
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

	public void setIDClienteJPA(Integer id) {
		this.idClienteJPA = id;
	}

	public void setIDEmpleado(Integer id) {
		this.idEmpleado = id;
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
	
}