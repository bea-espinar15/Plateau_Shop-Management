
package Negocio.Venta;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.MapsId;
import javax.persistence.Version;

import java.io.Serializable;
import Negocio.Producto.Producto;
import javax.persistence.ManyToOne;

@Entity
public class LineaVenta implements Serializable {
	
	private static final long serialVersionUID = 0;
	@EmbeddedId private LineaVentaID lineaVentaID;
	private Integer uds;
	private Integer precio;
	@Version Integer version;
	
	@ManyToOne
	@MapsId private Producto producto;
	
	@ManyToOne
	@MapsId private Venta venta;

	public LineaVenta() {}
	
	public Integer getUds() {
		return this.uds;
	}

	public Integer getPrecio() {
		return this.precio;
	}

	public void setUds(Integer uds) {
		this.uds = uds;
	}

	public void setPrecio(Integer precio) {
		this.precio = precio;
	}

	public Producto getProducto() {
		return this.producto;
	}

	public Venta getVenta() {
		return this.venta;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public void setVenta(Venta venta) {
		this.venta = venta;
	}
}