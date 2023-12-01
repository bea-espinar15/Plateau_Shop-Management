
package Presentacion.Gui.Panels.Departamento;

import javax.swing.table.AbstractTableModel;

import Negocio.Departamento.TDepartamento;

import java.util.ArrayList;

public class ListarDepartamentosTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;

	private ArrayList<TDepartamento> departamentos;
	private String[] columnNames = { "ID", "Nombre", "Nomina", "Activo" };

	public ListarDepartamentosTableModel(ArrayList<TDepartamento> departamentos) {
		this.departamentos = departamentos;
	}

	public int getRowCount() {
		return departamentos.size();
	}

	public int getColumnCount() {
		return columnNames.length;
	}

	public String getColumnName(int col) {
		return columnNames[col];
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return departamentos.get(rowIndex).getID();
		case 1:
			return departamentos.get(rowIndex).getNombre();
		case 2:
			return departamentos.get(rowIndex).getNomina();
		case 3:
			if (departamentos.get(rowIndex).getActivo()) return "SI";
			else return "NO";
		}
		return null;
	}

	public void updateList(ArrayList<TDepartamento> departamentos) {
		this.departamentos = departamentos;
	}

}