package Presentacion.Command;

import Presentacion.Command.ClienteJPA.AltaClienteJPACommand;
import Presentacion.Command.ClienteJPA.BajaClienteJPACommand;
import Presentacion.Command.ClienteJPA.ListarClientesJPACommand;
import Presentacion.Command.ClienteJPA.ModificarClienteJPACommand;
import Presentacion.Command.ClienteJPA.MostrarClienteJPACommand;
import Presentacion.Command.Departamento.AltaDepartamentoCommand;
import Presentacion.Command.Departamento.BajaDepartamentoCommand;
import Presentacion.Command.Departamento.ListarDepartamentosCommand;
import Presentacion.Command.Departamento.ModificarDepartamentoCommand;
import Presentacion.Command.Departamento.MostrarDepartamentoCommand;
import Presentacion.Command.Empleado.AltaEmpleadoCommand;
import Presentacion.Command.Empleado.BajaEmpleadoCommand;
import Presentacion.Command.Empleado.ListarEmpleadosCommand;
import Presentacion.Command.Empleado.ListarEmpleadosCompletoCommand;
import Presentacion.Command.Empleado.ListarEmpleadosParcialCommand;
import Presentacion.Command.Empleado.ListarEmpleadosPorDepartamentoCommand;
import Presentacion.Command.Empleado.ModificarEmpleadoCommand;
import Presentacion.Command.Empleado.MostrarEmpleadoCommand;
import Presentacion.Command.Producto.AltaProductoCommand;
import Presentacion.Command.Producto.AnadirProveedorCommand;
import Presentacion.Command.Producto.BajaProductoCommand;
import Presentacion.Command.Producto.ListarBebidasCommand;
import Presentacion.Command.Producto.ListarComidasCommand;
import Presentacion.Command.Producto.ListarProductosCommand;
import Presentacion.Command.Producto.ListarProductosPorProveedorCommand;
import Presentacion.Command.Producto.ListarProductosPorVentaCommand;
import Presentacion.Command.Producto.ModificarProductoCommand;
import Presentacion.Command.Producto.MostrarProductoCommand;
import Presentacion.Command.Producto.QuitarProveedorCommand;
import Presentacion.Command.Proveedor.AltaProveedorCommand;
import Presentacion.Command.Proveedor.BajaProveedorCommand;
import Presentacion.Command.Proveedor.ListarProveedoresCommand;
import Presentacion.Command.Proveedor.ListarProveedoresPorProductoCommand;
import Presentacion.Command.Proveedor.ModificarProveedorCommand;
import Presentacion.Command.Proveedor.MostrarProveedorCommand;
import Presentacion.Command.Venta.CerrarVentaCommand;
import Presentacion.Command.Venta.DevolverVentaCommand;
import Presentacion.Command.Venta.ListarVentasCommand;
import Presentacion.Command.Venta.ListarVentasPorClienteCommand;
import Presentacion.Command.Venta.ListarVentasPorEmpleadoCommand;
import Presentacion.Command.Venta.ListarVentasPorProductoCommand;
import Presentacion.Command.Venta.MostrarVentaCommand;
import Presentacion.Command.Venta.MostrarVentaEnDetalleCommand;

public class CommandFactoryImp extends CommandFactory {

	@Override
	public Command getCommand(Presentacion.Command.ContextEnum contextEnum) {
		if (map.containsKey(contextEnum))
			return map.get(contextEnum);

		switch (contextEnum) {
		case ALTACLIENTEJPA:
			map.put(ContextEnum.ALTACLIENTEJPA, new AltaClienteJPACommand());
			break;
		case BAJACLIENTEJPA:
			map.put(ContextEnum.BAJACLIENTEJPA, new BajaClienteJPACommand());
			break;
		case MODIFICARCLIENTEJPA:
			map.put(ContextEnum.MODIFICARCLIENTEJPA, new ModificarClienteJPACommand());
			break;
		case MOSTRARCLIENTEJPA:
			map.put(ContextEnum.MOSTRARCLIENTEJPA, new MostrarClienteJPACommand());
			break;
		case LISTARCLIENTESJPA:
			map.put(ContextEnum.LISTARCLIENTESJPA, new ListarClientesJPACommand());
			break;
		case ALTADEPARTAMENTO:
			map.put(ContextEnum.ALTADEPARTAMENTO, new AltaDepartamentoCommand());
			break;
		case BAJADEPARTAMENTO:
			map.put(ContextEnum.BAJADEPARTAMENTO, new BajaDepartamentoCommand());
			break;
		case MODIFICARDEPARTAMENTO:
			map.put(ContextEnum.MODIFICARDEPARTAMENTO, new ModificarDepartamentoCommand());
			break;
		case MOSTRARDEPARTAMENTO:
			map.put(ContextEnum.MOSTRARDEPARTAMENTO, new MostrarDepartamentoCommand());
			break;
		case LISTARDEPARTAMENTOS:
			map.put(ContextEnum.LISTARDEPARTAMENTOS, new ListarDepartamentosCommand());
			break;
		case ALTAEMPLEADO:
			map.put(ContextEnum.ALTAEMPLEADO, new AltaEmpleadoCommand());
			break;
		case BAJAEMPLEADO:
			map.put(ContextEnum.BAJAEMPLEADO, new BajaEmpleadoCommand());
			break;
		case MODIFICAREMPLEADO:
			map.put(ContextEnum.MODIFICAREMPLEADO, new ModificarEmpleadoCommand());
			break;
		case MOSTRAREMPLEADO:
			map.put(ContextEnum.MOSTRAREMPLEADO, new MostrarEmpleadoCommand());
			break;
		case LISTAREMPLEADOS:
			map.put(ContextEnum.LISTAREMPLEADOS, new ListarEmpleadosCommand());
			break;
		case LISTAREMPLEADOSCOMPLETO:
			map.put(ContextEnum.LISTAREMPLEADOSCOMPLETO, new ListarEmpleadosCompletoCommand());
			break;
		case LISTAREMPLEADOSPARCIAL:
			map.put(ContextEnum.LISTAREMPLEADOSPARCIAL, new ListarEmpleadosParcialCommand());
			break;
		case LISTAREMPLEADOSPORDEPARTAMENTO:
			map.put(ContextEnum.LISTAREMPLEADOSPORDEPARTAMENTO, new ListarEmpleadosPorDepartamentoCommand());
			break;
		case ALTAPRODUCTO:
			map.put(ContextEnum.ALTAPRODUCTO, new AltaProductoCommand());
			break;
		case BAJAPRODUCTO:
			map.put(ContextEnum.BAJAPRODUCTO, new BajaProductoCommand());
			break;
		case MODIFICARPRODUCTO:
			map.put(ContextEnum.MODIFICARPRODUCTO, new ModificarProductoCommand());
			break;
		case MOSTRARPRODUCTO:
			map.put(ContextEnum.MOSTRARPRODUCTO, new MostrarProductoCommand());
			break;
		case LISTARPRODUCTOS:
			map.put(ContextEnum.LISTARPRODUCTOS, new ListarProductosCommand());
			break;
		case LISTARCOMIDAS:
			map.put(ContextEnum.LISTARCOMIDAS, new ListarComidasCommand());
			break;
		case LISTARBEBIDAS:
			map.put(ContextEnum.LISTARBEBIDAS, new ListarBebidasCommand());
			break;
		case LISTARPRODUCTOSPORPROVEEDOR:
			map.put(ContextEnum.LISTARPRODUCTOSPORPROVEEDOR, new ListarProductosPorProveedorCommand());
			break;
		case LISTARPRODUCTOSPORVENTA:
			map.put(ContextEnum.LISTARPRODUCTOSPORVENTA, new ListarProductosPorVentaCommand());
			break;
		case ANADIRPROVEEDOR:
			map.put(ContextEnum.ANADIRPROVEEDOR, new AnadirProveedorCommand());
			 break;
		case QUITARPROVEEDOR:
			map.put(ContextEnum.QUITARPROVEEDOR, new QuitarProveedorCommand());
			break;
		case ALTAPROVEEDOR:
			map.put(ContextEnum.ALTAPROVEEDOR, new AltaProveedorCommand());
			break;
		case BAJAPROVEEDOR:
			map.put(ContextEnum.BAJAPROVEEDOR, new BajaProveedorCommand());
			break;
		case MODIFICARPROVEEDOR:
			map.put(ContextEnum.MODIFICARPROVEEDOR, new ModificarProveedorCommand());
			break;
		case MOSTRARPROVEEDOR:
			map.put(ContextEnum.MOSTRARPROVEEDOR, new MostrarProveedorCommand());
			break;
		case LISTARPROVEEDORES:
			map.put(ContextEnum.LISTARPROVEEDORES, new ListarProveedoresCommand());
			break;
		case LISTARPROVEEDORESPORPRODUCTO:
			map.put(ContextEnum.LISTARPROVEEDORESPORPRODUCTO, new ListarProveedoresPorProductoCommand());
			break;
		case CERRARVENTA:
			map.put(ContextEnum.CERRARVENTA, new CerrarVentaCommand());
			break;
		case MOSTRARVENTA:
			map.put(ContextEnum.MOSTRARVENTA, new MostrarVentaCommand());
			break;
		case MOSTRARVENTAENDETALLE:
			map.put(ContextEnum.MOSTRARVENTAENDETALLE, new MostrarVentaEnDetalleCommand());
			break;
		case LISTARVENTAS:
			map.put(ContextEnum.LISTARVENTAS, new ListarVentasCommand());
			break;
		case LISTARVENTASPORCLIENTE:
			map.put(ContextEnum.LISTARVENTASPORCLIENTE, new ListarVentasPorClienteCommand());
			break;
		case LISTARVENTASPORPRODUCTO:
			map.put(ContextEnum.LISTARVENTASPORPRODUCTO, new ListarVentasPorProductoCommand());
			break;
		case LISTARVENTASPOREMPLEADO:
			map.put(ContextEnum.LISTARVENTASPOREMPLEADO, new ListarVentasPorEmpleadoCommand());
			break;
		case DEVOLVERVENTA:
			map.put(ContextEnum.DEVOLVERVENTA, new DevolverVentaCommand());
			break;
		default:
			break;
		}
		return map.get(contextEnum);
	}

}
