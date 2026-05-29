package views;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import utils.AppUtils;

public class FrmMain extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JPanel pnlMain;
	private JMenuBar mnbMain;
	private JTable tblListaOrdenesActivas;
	private DefaultTableModel model;
	private JMenuItem mntmSalir;
	private JMenuItem mntmClientes;
	private JMenuItem mntmInventario;
	private JMenuItem mntmNuevaOrden;
	private JMenuItem mntmListaMantenimiento;
	private JMenuItem mntmListaReparaciones;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrmMain frame = new FrmMain();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FrmMain() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 370);
		pnlMain = new JPanel();
		pnlMain.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);

		mnbMain = new JMenuBar();
		setJMenuBar(mnbMain);
		mnbMain.setVisible(true);

		JMenu mnArchivo = new JMenu("Archivo");
		mnbMain.add(mnArchivo);

		mntmSalir = new JMenuItem("Salir");
		mntmSalir.addActionListener(this);
		mnArchivo.add(mntmSalir);

		JMenu mnMantenimiento = new JMenu("Gestión");
		mnbMain.add(mnMantenimiento);

		mntmClientes = new JMenuItem("Clientes");
		mntmClientes.addActionListener(this);
		mnMantenimiento.add(mntmClientes);

		mntmInventario = new JMenuItem("Inventario");
		mntmInventario.addActionListener(this);
		mnMantenimiento.add(mntmInventario);

		JMenu mnServicioSoporte = new JMenu("Servicio de Soporte");
		mnbMain.add(mnServicioSoporte);

		mntmNuevaOrden = new JMenuItem("Nueva Orden");
		mntmNuevaOrden.addActionListener(this);
		mnServicioSoporte.add(mntmNuevaOrden);

		mntmListaMantenimiento = new JMenuItem("Lista de Mantenimientos");
		mntmListaMantenimiento.addActionListener(this);
		mnServicioSoporte.add(mntmListaMantenimiento);

		mntmListaReparaciones = new JMenuItem("Lista de Reparaciones");
		mntmListaReparaciones.addActionListener(this);
		mnServicioSoporte.add(mntmListaReparaciones);

		JMenu mnReportes = new JMenu("Reportes");
		mnbMain.add(mnReportes);
		setContentPane(pnlMain);
		pnlMain.setLayout(null);

		JScrollPane scp = new JScrollPane();
		scp.setBounds(10, 10, 566, 291);
		pnlMain.add(scp);

		model = new DefaultTableModel(new Object[][] {},
				new String[] { "Cliente", "Electrónico", "Fecha de finalización estimada", "Servicio", "Estado" });

		tblListaOrdenesActivas = new JTable();
		tblListaOrdenesActivas.setModel(model);
		tblListaOrdenesActivas.setEnabled(false);
		tblListaOrdenesActivas.getTableHeader().setResizingAllowed(false);
		tblListaOrdenesActivas.getTableHeader().setReorderingAllowed(false);
		tblListaOrdenesActivas.getColumnModel().getColumn(0).setPreferredWidth(100); // Cliente
		tblListaOrdenesActivas.getColumnModel().getColumn(1).setPreferredWidth(100); // Electrónico
		tblListaOrdenesActivas.getColumnModel().getColumn(2).setPreferredWidth(190); // Fecha de finalización estimada
		tblListaOrdenesActivas.getColumnModel().getColumn(3).setPreferredWidth(90); // Servicio
		tblListaOrdenesActivas.getColumnModel().getColumn(4).setPreferredWidth(86); // Estado

		scp.setViewportView(tblListaOrdenesActivas);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == mntmSalir) {
			actionPerformedMntmSalir(e);
		}
		if (e.getSource() == mntmClientes) {
			actionPerformedMntmClientes(e);
		}
		if (e.getSource() == mntmInventario) {
			actionPerformedMntmInventario(e);
		}
		if (e.getSource() == mntmNuevaOrden) {
			actionPerformedMntmNuevaOrden(e);
		}
		if (e.getSource() == mntmListaMantenimiento) {
			actionPerformedMntmListaMantenimiento(e);
		}
		if (e.getSource() == mntmListaReparaciones) {
			actionPerformedMntmListaReparaciones(e);
		}
	}

	protected void actionPerformedMntmSalir(ActionEvent e) {
		System.exit(0);
	}

	protected void actionPerformedMntmClientes(ActionEvent e) {
		DlgClientes dlg = new DlgClientes();
		AppUtils.abrirDialogo(this, dlg);
	}

	protected void actionPerformedMntmInventario(ActionEvent e) {
		DlgInventario dlg = new DlgInventario();
		AppUtils.abrirDialogo(this, dlg);
	}

	protected void actionPerformedMntmNuevaOrden(ActionEvent e) {
		DlgNuevaOrden dlg = new DlgNuevaOrden();
		AppUtils.abrirDialogo(this, dlg);
	}

	protected void actionPerformedMntmListaMantenimiento(ActionEvent e) {
		DlgListaMantenimientos dlg = new DlgListaMantenimientos();
		AppUtils.abrirDialogo(this, dlg);
	}

	protected void actionPerformedMntmListaReparaciones(ActionEvent e) {
		DlgListaReparaciones dlg = new DlgListaReparaciones();
		AppUtils.abrirDialogo(this, dlg);
	}
}