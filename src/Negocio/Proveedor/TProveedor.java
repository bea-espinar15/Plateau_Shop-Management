
package Negocio.Proveedor;

public class TProveedor {
	
	private Integer id;
	private Integer telefono;
	private String nombre;
	private String cif;
	private Boolean activo;

	public TProveedor() {}

	public TProveedor(Integer id, Integer telefono, String nombre, String cif, Boolean activo) {
		this.id = id;
		this.telefono = telefono;
		this.nombre = nombre;
		this.cif = cif;
		this.activo = activo;
	}

	public TProveedor(Integer id) {
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

	public Boolean getActivo() {
		return this.activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}
	
}