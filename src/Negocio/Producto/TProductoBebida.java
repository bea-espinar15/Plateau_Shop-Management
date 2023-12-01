
package Negocio.Producto;

public class TProductoBebida extends TProducto {

	private String tamano;

	public TProductoBebida(Integer id, Boolean activo, String nombre, Integer precioActual, Integer stock, String tamano) {
		super(id, activo, nombre, precioActual, stock);
		this.tamano = tamano;
	}

	public TProductoBebida(Integer id) {
		super(id);
	}
	
	public TProductoBebida() {}

	public void setTamano(String tamano) {
		this.tamano = tamano;
	}

	public String getTamano() {
		return this.tamano;
	}
	
}