
package Negocio.Empleado;


public abstract class TEmpleado {

	private Integer id;
	private String nombre;
	private String dni;
	private Integer sueldo;
	private Integer telefono;
	private Boolean activo;
	private Integer idDpto;
	
	public TEmpleado(Integer id, String nombre, String dni, Integer sueldo, Integer telefono, Boolean activo, Integer idDpto) {
		this.id = id;
		this.nombre = nombre;
		this.dni = dni;
		this.sueldo = sueldo;
		this.telefono = telefono;
		this.activo = activo;
		this.idDpto = idDpto;
	}
	
	public TEmpleado(Integer id) {
		this.id = id;
	}
	
	public TEmpleado() {}
	
	public Integer getID() {
		return id;
	}

	public void setID(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDNI() {
		return dni;
	}

	public void setDNI(String dni) {
		this.dni = dni;
	}

	public Integer getSueldo() {
		return sueldo;
	}

	public void setSueldo(Integer sueldo) {
		this.sueldo = sueldo;
	}

	public Integer getTelefono() {
		return telefono;
	}

	public void setTelefono(Integer telefono) {
		this.telefono = telefono;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public Integer getIDDpto() {
		return idDpto;
	}
	
	public void setIDDpto(Integer idDpto) {
		this.idDpto = idDpto;
	}
	
}