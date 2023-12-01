
package Presentacion.Controller;

import Presentacion.Gui.Panels.ClienteJPA.AltaClienteJPAPanel;
import Presentacion.Gui.Panels.ClienteJPA.BajaClienteJPAPanel;
import Presentacion.Gui.Panels.ClienteJPA.ListarClientesJPAPanel;
import Presentacion.Gui.Panels.ClienteJPA.ModificarClienteJPAPanel;
import Presentacion.Gui.Panels.ClienteJPA.MostrarClienteJPAPanel;
import Presentacion.Gui.Panels.Departamento.AltaDepartamentoPanel;
import Presentacion.Gui.Panels.Departamento.BajaDepartamentoPanel;
import Presentacion.Gui.Panels.Departamento.ListarDepartamentosPanel;
import Presentacion.Gui.Panels.Departamento.ModificarDepartamentoPanel;
import Presentacion.Gui.Panels.Departamento.MostrarDepartamentoPanel;
import Presentacion.Gui.Panels.Empleado.AltaEmpleadoPanel;
import Presentacion.Gui.Panels.Empleado.BajaEmpleadoPanel;
import Presentacion.Gui.Panels.Empleado.ListarEmpleadosCompletoPanel;
import Presentacion.Gui.Panels.Empleado.ListarEmpleadosPanel;
import Presentacion.Gui.Panels.Empleado.ListarEmpleadosParcialPanel;
import Presentacion.Gui.Panels.Empleado.ListarPorDepartamentoPanel;
import Presentacion.Gui.Panels.Empleado.ModificarEmpleadoPanel;
import Presentacion.Gui.Panels.Empleado.MostrarEmpleadoPanel;
import Presentacion.Gui.Panels.Producto.AltaProductoPanel;
import Presentacion.Gui.Panels.Producto.AnadirProveedorPanel;
import Presentacion.Gui.Panels.Producto.BajaProductoPanel;
import Presentacion.Gui.Panels.Producto.ListarBebidasPanel;
import Presentacion.Gui.Panels.Producto.ListarComidasPanel;
import Presentacion.Gui.Panels.Producto.ListarPorProveedorPanel;
import Presentacion.Gui.Panels.Producto.ListarPorVentaPanel;
import Presentacion.Gui.Panels.Producto.ListarProductosPanel;
import Presentacion.Gui.Panels.Producto.ModificarProductoPanel;
import Presentacion.Gui.Panels.Producto.MostrarProductoPanel;
import Presentacion.Gui.Panels.Producto.QuitarProveedorPanel;
import Presentacion.Gui.Panels.Proveedor.AltaProveedorPanel;
import Presentacion.Gui.Panels.Proveedor.BajaProveedorPanel;
import Presentacion.Gui.Panels.Proveedor.ListarProveedoresPanel;
import Presentacion.Gui.Panels.Proveedor.ListarProveedoresPorProductoPanel;
import Presentacion.Gui.Panels.Proveedor.ModificarProveedorPanel;
import Presentacion.Gui.Panels.Proveedor.MostrarProveedorPanel;
import Presentacion.Gui.Panels.Venta.CerrarVentaPanel;
import Presentacion.Gui.Panels.Venta.DevolverVentaPanel;
import Presentacion.Gui.Panels.Venta.ListarVentasPanel;
import Presentacion.Gui.Panels.Venta.ListarVentasPorClientePanel;
import Presentacion.Gui.Panels.Venta.ListarVentasPorEmpleadoPanel;
import Presentacion.Gui.Panels.Venta.ListarVentasPorProductoPanel;
import Presentacion.Gui.Panels.Venta.MostrarVentaEnDetallePanel;
import Presentacion.Gui.Panels.Venta.MostrarVentaPanel;

public class DispatcherImp extends Dispatcher {

	DispatcherImp() {}

	public void Dispatch(Context response) {
		switch (response.getContext()) {
		case ALTACLIENTEJPA:
			AltaClienteJPAPanel.getInstance().update(response.getData());
			break;
		case ALTADEPARTAMENTO:
			AltaDepartamentoPanel.getInstance().update(response.getData());
			break;
		case ALTAEMPLEADO:
			AltaEmpleadoPanel.getInstance().update(response.getData());
			break;
		case ALTAPRODUCTO:
			AltaProductoPanel.getInstance().update(response.getData());
			break;
		case ALTAPROVEEDOR:
			AltaProveedorPanel.getInstance().update(response.getData());
			break;
		case ANADIRPROVEEDOR:
			AnadirProveedorPanel.getInstance().update(response.getData());
			break;
		case BAJACLIENTEJPA:
			BajaClienteJPAPanel.getInstance().update(response.getData());
			break;
		case BAJADEPARTAMENTO:
			BajaDepartamentoPanel.getInstance().update(response.getData());
			break;
		case BAJAEMPLEADO:
			BajaEmpleadoPanel.getInstance().update(response.getData());
			break;
		case BAJAPRODUCTO:
			BajaProductoPanel.getInstance().update(response.getData());
			break;
		case BAJAPROVEEDOR:
			BajaProveedorPanel.getInstance().update(response.getData());
			break;
		case CERRARVENTA:
			CerrarVentaPanel.getInstance().update(response.getData());
			break;
		case DEVOLVERVENTA:
			DevolverVentaPanel.getInstance().update(response.getData());
			break;
		case LISTARBEBIDAS:
			ListarBebidasPanel.getInstance().update(response.getData());
			break;
		case LISTARCLIENTESJPA:
			ListarClientesJPAPanel.getInstance().update(response.getData());
			break;
		case LISTARCOMIDAS:
			ListarComidasPanel.getInstance().update(response.getData());
			break;
		case LISTARDEPARTAMENTOS:
			ListarDepartamentosPanel.getInstance().update(response.getData());
			break;
		case LISTAREMPLEADOS:
			ListarEmpleadosPanel.getInstance().update(response.getData());
			break;
		case LISTAREMPLEADOSCOMPLETO:
			ListarEmpleadosCompletoPanel.getInstance().update(response.getData());
			break;
		case LISTAREMPLEADOSPARCIAL:
			ListarEmpleadosParcialPanel.getInstance().update(response.getData());
			break;
		case LISTAREMPLEADOSPORDEPARTAMENTO:
			ListarPorDepartamentoPanel.getInstance().update(response.getData());
			break;
		case LISTARPRODUCTOS:
			ListarProductosPanel.getInstance().update(response.getData());
			break;
		case LISTARPRODUCTOSPORPROVEEDOR:
			ListarPorProveedorPanel.getInstance().update(response.getData());
			break;
		case LISTARPRODUCTOSPORVENTA:
			ListarPorVentaPanel.getInstance().update(response.getData());
			break;
		case LISTARPROVEEDORES:
			ListarProveedoresPanel.getInstance().update(response.getData());
			break;
		case LISTARPROVEEDORESPORPRODUCTO:
			ListarProveedoresPorProductoPanel.getInstance().update(response.getData());
			break;
		case LISTARVENTAS:
			ListarVentasPanel.getInstance().update(response.getData());
			break;
		case LISTARVENTASPORCLIENTE:
			ListarVentasPorClientePanel.getInstance().update(response.getData());
			break;
		case LISTARVENTASPOREMPLEADO:
			ListarVentasPorEmpleadoPanel.getInstance().update(response.getData());
			break;
		case LISTARVENTASPORPRODUCTO:
			ListarVentasPorProductoPanel.getInstance().update(response.getData());
			break;
		case MODIFICARCLIENTEJPA:
			ModificarClienteJPAPanel.getInstance().update(response.getData());
			break;
		case MODIFICARDEPARTAMENTO:
			ModificarDepartamentoPanel.getInstance().update(response.getData());
			break;
		case MODIFICAREMPLEADO:
			ModificarEmpleadoPanel.getInstance().update(response.getData());
			break;
		case MODIFICARPRODUCTO:
			ModificarProductoPanel.getInstance().update(response.getData());
			break;
		case MODIFICARPROVEEDOR:
			ModificarProveedorPanel.getInstance().update(response.getData());
			break;
		case MOSTRARCLIENTEJPA:
			if (ModificarClienteJPAPanel.getInstance().isSelected())
				ModificarClienteJPAPanel.getInstance().update(response.getData());
			else
				MostrarClienteJPAPanel.getInstance().update(response.getData());
			break;
		case MOSTRARDEPARTAMENTO:
			if (ModificarDepartamentoPanel.getInstance().isSelected())
				ModificarDepartamentoPanel.getInstance().update(response.getData());
			else
				MostrarDepartamentoPanel.getInstance().update(response.getData());
			break;
		case MOSTRAREMPLEADO:
			if (ModificarEmpleadoPanel.getInstance().isSelected())
				ModificarEmpleadoPanel.getInstance().update(response.getData());
			else
				MostrarEmpleadoPanel.getInstance().update(response.getData());
			break;
		case MOSTRARPRODUCTO:
			if (ModificarProductoPanel.getInstance().isSelected())
				ModificarProductoPanel.getInstance().update(response.getData());
			else
				MostrarProductoPanel.getInstance().update(response.getData());
			break;
		case MOSTRARPROVEEDOR:
			if (ModificarProveedorPanel.getInstance().isSelected())
				ModificarProveedorPanel.getInstance().update(response.getData());
			else
				MostrarProveedorPanel.getInstance().update(response.getData());
			break;
		case MOSTRARVENTA:
			MostrarVentaPanel.getInstance().update(response.getData());
			break;
		case MOSTRARVENTAENDETALLE:
			MostrarVentaEnDetallePanel.getInstance().update(response.getData());
			break;
		case QUITARPROVEEDOR:
			QuitarProveedorPanel.getInstance().update(response.getData());
			break;
		default:
			break;
		}
	}

}