
package Negocio.Departamento;

public class TDepartamento {

	private Integer id;
	private Boolean activo;
	private String nombre;
	private Integer nomina;

	public TDepartamento(Integer id, String nombre, Integer nomina, Boolean activo) {
		this.id = id;
		this.nombre = nombre;
		this.nomina = nomina;
		this.activo = activo;
	}

	public TDepartamento(Integer id) {
		this.id = id;
	}
	
	public TDepartamento() {}

	public Integer getID() {
		return id;
	}

	public void setID(Integer id) {
		this.id = id;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Integer getNomina() {
		return nomina;
	}

	public void setNomina(Integer nomina) {
		this.nomina = nomina;
	}
	
}