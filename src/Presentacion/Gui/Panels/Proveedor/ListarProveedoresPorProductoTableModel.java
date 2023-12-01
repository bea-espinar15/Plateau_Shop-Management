package Presentacion.Gui.Panels.Proveedor;

import javax.swing.table.AbstractTableModel;
import Negocio.Proveedor.TProveedor;
import java.util.ArrayList;

public class ListarProveedoresPorProductoTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	
	private String[] columnNames = { "ID", "Telefono", "Nombre", "CIF" };	
	private ArrayList<TProveedor> proveedores;

	public ListarProveedoresPorProductoTableModel(ArrayList<TProveedor> proveedores) {
		this.proveedores = proveedores;
	}
	
	public int getRowCount() {
		return proveedores.size();
	}

	public int getColumnCount() {
		return columnNames.length;
	}

	public String getColumnName(int col) {
		return columnNames[col];
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		switch(columnIndex){
		case 0:
			return proveedores.get(rowIndex).getID();
		case 1:
			return proveedores.get(rowIndex).getTelefono();
		case 2:
			return proveedores.get(rowIndex).getNombre();
		case 3: 
			return proveedores.get(rowIndex).getCIF();
	}
		return null;
	}

	public void updateList(ArrayList<TProveedor> proveedores) {
		this.proveedores = proveedores;
	}

}