
package Presentacion.Gui.Panels.ClienteJPA;

import javax.swing.table.AbstractTableModel;

import Negocio.ClienteJPA.TClienteJPA;

import java.util.ArrayList;
import java.util.List;

public class ListarClientesJPATableModel extends AbstractTableModel {
	
	private static final long serialVersionUID = 1L;
	
	private String[] columnNames = {"ID","Nombre", "DNI", "Activo"};
	private List<TClienteJPA> clientesJPA;
	
	public ListarClientesJPATableModel(List<TClienteJPA> clientes){
		clientesJPA = clientes;
	}

	public int getRowCount() {
		return clientesJPA.size();
	}

	public int getColumnCount() {
		return this.columnNames.length;
	}

	public String getColumnName(int i) {
		return this.columnNames[i];
	}
	
	public void updateList(ArrayList<TClienteJPA> list) {
		clientesJPA = list;
	}
	
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return clientesJPA.get(rowIndex).getID().toString();
		case 1:
			return clientesJPA.get(rowIndex).getNombre().toString();
		case 2:
			return clientesJPA.get(rowIndex).getDNI().toString();
		case 3:
			if (clientesJPA.get(rowIndex).getActivo()) return "SI";
			else return "NO";
		}
		return null;
	}
	
}