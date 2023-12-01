
package Negocio.Producto;

import javax.persistence.Entity;
import javax.persistence.NamedQuery;

import java.io.Serializable;

@Entity
@NamedQuery(name = "ProductoBebida.readAll", query = "select obj from ProductoBebida obj order by obj.id asc")
public class ProductoBebida extends Producto implements Serializable {

	private static final long serialVersionUID = 0;
	private String tamano;

	public ProductoBebida() {}

	public void setTamano(String tamano) {
		this.tamano = tamano;
	}

	public String getTamano() {
		return this.tamano;
	}
	
}