
package Negocio.Producto;

import javax.persistence.Entity;
import javax.persistence.NamedQuery;

import java.io.Serializable;

@Entity
@NamedQuery(name = "ProductoComida.readAll", query = "select obj from ProductoComida obj order by obj.id asc")
public class ProductoComida extends Producto implements Serializable {

	private static final long serialVersionUID = 0;
	private Integer peso;
	
	public ProductoComida() {}

	public void setPeso(Integer peso) {
		this.peso = peso;
	}

	public Integer getPeso() {
		return this.peso;
	}
	
}