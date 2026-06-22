package dao;

import interfaces.ClienteDAO;
import interfaces.DetalleOrdenDAO;
import interfaces.OrdenDAO;
import interfaces.ReporteDAO;
import interfaces.RepuestoDAO;
import interfaces.TecnicoDAO;
import interfaces.UsuarioDAO;

public abstract class DAO {
	public abstract ClienteDAO getClienteDAO();

	public abstract RepuestoDAO getRepuestoDAO();

	public abstract TecnicoDAO getTecnicoDAO();

	public abstract OrdenDAO getOrdenDAO();

	public abstract DetalleOrdenDAO getDetalleOrdenDAO();

	public abstract ReporteDAO getReporteDAO();

	public abstract UsuarioDAO getUsuarioDAO();

	public static DAO getDaoFactory() {
		return new MySqlDAO();
	}
}
