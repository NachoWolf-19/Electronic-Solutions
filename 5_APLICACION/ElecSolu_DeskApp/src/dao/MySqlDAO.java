package dao;

import controller.MySqlCategoriaDAO;
import controller.MySqlClienteDAO;
import controller.MySqlDetalleOrdenDAO;
import controller.MySqlOrdenDAO;
import controller.MySqlReporteDAO;
import controller.MySqlRepuestoDAO;
import controller.MySqlTecnicoDAO;
import controller.MySqlUsuarioDAO;
import interfaces.CategoriaDAO;
import interfaces.ClienteDAO;
import interfaces.DetalleOrdenDAO;
import interfaces.OrdenDAO;
import interfaces.ReporteDAO;
import interfaces.RepuestoDAO;
import interfaces.TecnicoDAO;
import interfaces.UsuarioDAO;

public class MySqlDAO extends DAO {

	@Override
	public ClienteDAO getClienteDAO() {
		return new MySqlClienteDAO();
	}

	@Override
	public RepuestoDAO getRepuestoDAO() {
		return new MySqlRepuestoDAO();
	}

	@Override
	public CategoriaDAO getCategoriaDAO() {
		return new MySqlCategoriaDAO();
	}

	@Override
	public TecnicoDAO getTecnicoDAO() {
		return new MySqlTecnicoDAO();
	}

	@Override
	public OrdenDAO getOrdenDAO() {
		return new MySqlOrdenDAO();
	}

	@Override
	public DetalleOrdenDAO getDetalleOrdenDAO() {
		return new MySqlDetalleOrdenDAO();
	}

	@Override
	public ReporteDAO getReporteDAO() {
		return new MySqlReporteDAO();
	}

	@Override
	public UsuarioDAO getUsuarioDAO() {
		return new MySqlUsuarioDAO();
	}
}
