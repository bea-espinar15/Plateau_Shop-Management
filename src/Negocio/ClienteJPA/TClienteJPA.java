
package Negocio.ClienteJPA;

public class TClienteJPA {

	private Integer id;
	private String dni;
	private String nombre;
	private Boolean activo;

	public TClienteJPA() {
	}

	public TClienteJPA(Integer id, String dni, String nombre, Boolean activo) {
		this.id = id;
		this.dni = dni;
		this.nombre = nombre;
		this.activo = activo;
	}

	public TClienteJPA(Integer id) {
		this.id = id;
	}

	public Integer getID() {
		return this.id;
	}

	public void setID(Integer id) {
		this.id = id;
	}

	public String getDNI() {
		return this.dni;
	}

	public void setDNI(String dni) {
		this.dni = dni;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Boolean getActivo() {
		return this.activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}
	
}