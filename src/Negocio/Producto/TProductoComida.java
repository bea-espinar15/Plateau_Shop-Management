
package Negocio.Producto;

public class TProductoComida extends TProducto {

	private Integer peso;

	public TProductoComida(Integer id, Boolean activo, String nombre, Integer precioActual, Integer stock, Integer peso) {
		super(id, activo, nombre, precioActual, stock);
		this.peso = peso;
	}
	
	public TProductoComida(Integer id) {
		super(id);
	}

	public TProductoComida() {}

	public void setPeso(Integer peso) {
		this.peso = peso;
	}

	public Integer getPeso() {
		return this.peso;
	}
	
}