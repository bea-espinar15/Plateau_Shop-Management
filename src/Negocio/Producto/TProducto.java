
package Negocio.Producto;

public abstract class TProducto {

	private Integer id;
	private Boolean activo;
	private String nombre;
	private Integer precioActual;
	private Integer stock;

	public TProducto() {}
	
	public TProducto(Integer id) {
		this.id = id;
	}
	
	public TProducto(Integer id, Boolean activo, String nombre, Integer precioActual, Integer stock) {
		this.id = id;
		this.activo = activo;
		this.nombre = nombre;
		this.precioActual = precioActual;
		this.stock = stock;
	}
	
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

	public void setPrecioActual(Integer precioActual) {
		this.precioActual = precioActual;
	}

	public Integer getPrecioActual() {
		return this.precioActual;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public Integer getStock() {
		return this.stock;
	}
	
}