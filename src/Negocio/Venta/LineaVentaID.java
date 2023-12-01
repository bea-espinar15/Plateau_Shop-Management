
package Negocio.Venta;

import java.io.Serializable;
import javax.persistence.Embeddable;


@Embeddable
public class LineaVentaID implements Serializable {
	
	private static final long serialVersionUID = 0;
	
	private Integer venta;
	private Integer producto;
	
	public LineaVentaID() {}
	
	public LineaVentaID(Integer venta, Integer producto) {
		this.venta = venta;
		this.producto = producto;
	}


	public Integer getVenta() {
		return venta;
	}

	public Integer getProducto() {
		return producto;
	}

	public boolean equals(Object obj) {
		return (obj instanceof LineaVentaID) && venta == ((LineaVentaID)obj).getVenta() && producto == ((LineaVentaID)obj).getProducto();
	}

	public int hashCode() {
		return venta.hashCode() + producto.hashCode();
	}
	
}