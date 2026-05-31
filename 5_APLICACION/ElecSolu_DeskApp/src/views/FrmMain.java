package views;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
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

	private JScrollPane scp;
	private JLabel lblTecnico;
	private JComboBox<String> cboTecnico;

	private String rolUsuario;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrmMain frame = new FrmMain("Admin");
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
	public FrmMain(String rol) {
		this.rolUsuario = rol;

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

		scp = new JScrollPane();
		pnlMain.add(scp);

		// Estructura original de 5 columnas restablecida
		model = new DefaultTableModel(
				new Object[][] { { "Juan Pérez", "Laptop Asus", "05/06/2026", "Reparación", "Pendiente" },
						{ "María López", "Impresora HP", "02/06/2026", "Mantenimiento", "En Proceso" },
						{ "Carlos Gómez", "PC Gamer", "08/06/2026", "Reparación", "Terminado" } },
				new String[] { "Cliente", "Electrónico", "Fecha de finalización estimada", "Servicio", "Estado" }) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		tblListaOrdenesActivas = new JTable();
		tblListaOrdenesActivas.setModel(model);
		tblListaOrdenesActivas.setEnabled(true);
		tblListaOrdenesActivas.getTableHeader().setResizingAllowed(false);
		tblListaOrdenesActivas.getTableHeader().setReorderingAllowed(false);
		tblListaOrdenesActivas.getColumnModel().getColumn(0).setPreferredWidth(100);
		tblListaOrdenesActivas.getColumnModel().getColumn(1).setPreferredWidth(100);
		tblListaOrdenesActivas.getColumnModel().getColumn(2).setPreferredWidth(190);
		tblListaOrdenesActivas.getColumnModel().getColumn(3).setPreferredWidth(90);
		tblListaOrdenesActivas.getColumnModel().getColumn(4).setPreferredWidth(86);

		scp.setViewportView(tblListaOrdenesActivas);

		lblTecnico = new JLabel("Técnico:");
		lblTecnico.setBounds(10, 10, 60, 20);
		pnlMain.add(lblTecnico);

		cboTecnico = new JComboBox<>();
		cboTecnico.setBounds(75, 10, 130, 20);
		pboCargarTecnicosMock();
		cboTecnico.addActionListener(this); // Escuchador del combo activado
		pnlMain.add(cboTecnico);

		sederAccesos();
		configurarEventosTabla();
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == cboTecnico) {
			actionPerformedCboTecnico(e);
		}
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

	private void sederAccesos() {
		switch (rolUsuario) {
		case "Admin":
			accesoAdmin();
			break;
		case "Tecnico":
			accesoTecnico();
			break;
		case "Recepcionista":
			accesoRecepcionista();
			break;
		}
	}

	// --- CONFIGURACIÓN DE ROLES ---

	private void accesoAdmin() {
		lblTecnico.setText("Técnico:");
		lblTecnico.setVisible(true);
		cboTecnico.setVisible(true);
		scp.setBounds(10, 32, 566, 269);
	}

	private void accesoTecnico() {
		lblTecnico.setText("Técnico:");
		lblTecnico.setVisible(true);
		cboTecnico.setVisible(true);
		scp.setBounds(10, 32, 566, 269);
	}

	private void accesoRecepcionista() {
		lblTecnico.setVisible(false);
		cboTecnico.setVisible(false);
		scp.setBounds(10, 10, 566, 291);
	}

	// --- ACCIÓN DEL COMBOBOX (Llamada externa a Base de Datos) ---

	protected void actionPerformedCboTecnico(ActionEvent e) {
		String tecnicoSeleccionado = cboTecnico.getSelectedItem().toString();

		// Aquí invocas el método de tu manejador de BD/DAO pasando el filtro
		// seleccionado
		// modeloOrdenes.listarPorTecnico(tecnicoSeleccionado);

		System.out.println("Filtrando BD para el técnico: " + tecnicoSeleccionado);
	}

	// --- LOGICA DE INTERACCIÓN (Doble clic en tabla) ---

	private void configurarEventosTabla() {
		tblListaOrdenesActivas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent evt) {
				int filaSeleccionada = tblListaOrdenesActivas.getSelectedRow();

				if (filaSeleccionada != -1 && evt.getClickCount() == 2) {
					String tipoServicio = tblListaOrdenesActivas.getValueAt(filaSeleccionada, 3).toString();
					int idOrden = filaSeleccionada;

					if (tipoServicio.equalsIgnoreCase("Reparación")) {
						DlgInfoReparacion dlg = new DlgInfoReparacion(idOrden, rolUsuario);
						AppUtils.abrirDialogo(FrmMain.this, dlg);
					} else if (tipoServicio.equalsIgnoreCase("Mantenimiento")) {
						DlgInfoMantenimiento dlg = new DlgInfoMantenimiento(idOrden, rolUsuario);
						AppUtils.abrirDialogo(FrmMain.this, dlg);
					}
				}
			}
		});
	}

	private void pboCargarTecnicosMock() {
		cboTecnico.addItem("--- Todos ---");
		cboTecnico.addItem("Ricardo Gareca");
		cboTecnico.addItem("Jorge Fossati");
	}

	// --- ACCIONES DE MENÚ ---

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