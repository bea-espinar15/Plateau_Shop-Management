
package Negocio.Venta;


public class TLineaVenta {
	
	private Integer idVenta;
	private Integer idProducto;
	private Integer uds;
	private Integer precio;
	
	public TLineaVenta() {}
	
	public TLineaVenta(Integer idVenta) {
		this.idVenta = idVenta;
	}
	
	public TLineaVenta(Integer idVenta, Integer idProducto, Integer uds, Integer precio) {
		this.idVenta = idVenta;
		this.idProducto = idProducto;
		this.uds = uds;
		this.precio = precio;
	}
	
	public Integer getIDVenta() {
		return idVenta;
	}

	public void setIDVenta(Integer idVenta) {
		this.idVenta = idVenta;
	}

	public Integer getIDProducto() {
		return idProducto;
	}

	public void setIDProducto(Integer idProducto) {
		this.idProducto = idProducto;
	}

	public Integer getUds() {
		return uds;
	}

	public void setUds(Integer uds) {
		this.uds = uds;
	}

	public Integer getPrecio() {
		return precio;
	}

	public void setPrecio(Integer precio) {
		this.precio = precio;
	}
	
}