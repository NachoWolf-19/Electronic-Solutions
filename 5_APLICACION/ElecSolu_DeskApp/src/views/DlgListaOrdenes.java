package views;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import utils.AppUtils;

public class DlgListaOrdenes extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();

	private JTextField txtBuscarCliente;
	private JComboBox<String> cboFiltroServicio;
	private JTable tblOrdenes;
	private DefaultTableModel modelOrdenes;
	private JButton btnCerrar;

	private String rolUsuario;

	// Matriz Máster de datos simulados (Mock) para realizar los filtros en tiempo
	// real
	private final Object[][] masterData = {
			{ "01/06/2026", "Juan Pérez", "Laptop Asus", "Reparación", "05/06/2026", "Ver Info" },
			{ "30/05/2026", "María López", "Impresora HP", "Mantenimiento", "02/06/2026", "Ver Info" },
			{ "02/06/2026", "Carlos Gómez", "PC Gamer", "Reparación", "08/06/2026", "Ver Info" },
			{ "28/05/2026", "Marina Silva", "Sintetizador Korg", "Reparación", "04/06/2026", "Ver Info" },
			{ "01/06/2026", "Pedro Alva", "Consola Behringer", "Mantenimiento", "06/06/2026", "Ver Info" } };

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DlgListaOrdenes dialog = new DlgListaOrdenes(0, "Admin");
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DlgListaOrdenes(int tipo, String rolUsuario) {
		this.rolUsuario = rolUsuario;

		setTitle("Explorador Central de Órdenes de Servicio");
		setModal(true);
		setResizable(false);
		setBounds(100, 100, 750, 430);
		setLocationRelativeTo(null);

		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		// --- COMPONENTES DE BÚSQUEDA Y FILTRADO ---

		JLabel lblBuscar = new JLabel("Buscar Cliente:");
		lblBuscar.setBounds(20, 20, 100, 20);
		contentPanel.add(lblBuscar);

		txtBuscarCliente = new JTextField();
		txtBuscarCliente.setBounds(120, 20, 180, 22);
		contentPanel.add(txtBuscarCliente);

		// Evento de teclado para buscar de forma interactiva mientras se escribe
		txtBuscarCliente.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				filtrarYMostrarOrdenes();
			}
		});

		JLabel lblFiltrar = new JLabel("Servicio:");
		lblFiltrar.setBounds(330, 20, 60, 20);
		contentPanel.add(lblFiltrar);

		cboFiltroServicio = new JComboBox<>();
		cboFiltroServicio.setBounds(395, 20, 210, 22);
		cboFiltroServicio.addItem("--- Todas las Órdenes ---");
		cboFiltroServicio.addItem("Solo Mantenimientos");
		cboFiltroServicio.addItem("Solo Reparaciones");

		cboFiltroServicio.setSelectedIndex(tipo);
		cboFiltroServicio.addActionListener(this);
		contentPanel.add(cboFiltroServicio);

		// --- CONFIGURACIÓN DE TABLA ---

		JScrollPane scp = new JScrollPane();
		scp.setBounds(20, 60, 695, 260);
		contentPanel.add(scp);

		tblOrdenes = new JTable();
		tblOrdenes.getTableHeader().setResizingAllowed(false);
		tblOrdenes.getTableHeader().setReorderingAllowed(false);
		tblOrdenes.setRowHeight(25); // Más alto para que el botón luzca cómodo
		scp.setViewportView(tblOrdenes);

		// --- BOTÓN INFERIOR ---
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				btnCerrar = new JButton("Cerrar");
				btnCerrar.addActionListener(this);
				buttonPane.add(btnCerrar);
			}
		}

		configurarEventosTabla();
		filtrarYMostrarOrdenes();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == cboFiltroServicio) {
			filtrarYMostrarOrdenes();
		}
		if (e.getSource() == btnCerrar) {
			dispose();
		}
	}

	/**
	 * Filtra dinámicamente combinando el texto ingresado y el combo de servicio
	 */
	private void filtrarYMostrarOrdenes() {
		String textoBuscar = txtBuscarCliente.getText().trim().toLowerCase();
		int seleccionCombo = cboFiltroServicio.getSelectedIndex();

		String[] columnas = { "Fecha Reg.", "Cliente", "Equipo", "Servicio", "F. Entrega Est.", "Acción" };

		// Crear un modelo temporal vacío
		DefaultTableModel nuevoModelo = new DefaultTableModel(null, columnas) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false; // Desactiva edición directa en celdas
			}
		};

		// Evaluar fila por fila de la matriz global
		for (Object[] fila : masterData) {
			String cliente = fila[1].toString().toLowerCase();
			String servicio = fila[3].toString();

			// Validar coincidencia de nombre de cliente
			boolean coincideCliente = cliente.contains(textoBuscar);

			// Validar coincidencia del filtro por combo
			boolean coincideCombo = (seleccionCombo == 0)
					|| (seleccionCombo == 1 && servicio.equalsIgnoreCase("Mantenimiento"))
					|| (seleccionCombo == 2 && servicio.equalsIgnoreCase("Reparación"));

			if (coincideCliente && coincideCombo) {
				nuevoModelo.addRow(fila);
			}
		}

		tblOrdenes.setModel(nuevoModelo);

		// Dimensionar ancho de columnas de forma balanceada
		tblOrdenes.getColumnModel().getColumn(0).setPreferredWidth(90);
		tblOrdenes.getColumnModel().getColumn(1).setPreferredWidth(130);
		tblOrdenes.getColumnModel().getColumn(2).setPreferredWidth(130);
		tblOrdenes.getColumnModel().getColumn(3).setPreferredWidth(110);
		tblOrdenes.getColumnModel().getColumn(4).setPreferredWidth(110);
		tblOrdenes.getColumnModel().getColumn(5).setPreferredWidth(100);

		// RENDERIZADOR: Transforma el texto de la última columna en un JButton real en
		// pantalla
		tblOrdenes.getColumnModel().getColumn(5).setCellRenderer(new TableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				JButton btn = new JButton(value != null ? value.toString() : "Ver Info");
				btn.setFont(table.getFont());
				return btn;
			}
		});
	}

	/**
	 * Controla los clics en la tabla. Detecta si presionaron la columna del botón.
	 */
	private void configurarEventosTabla() {
		tblOrdenes.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent evt) {
				int filaSeleccionada = tblOrdenes.getSelectedRow();
				int columnaSeleccionada = tblOrdenes.getSelectedColumn();

				// Si se hace clic válido en cualquier celda de la columna 5 (Acción/Info)
				if (filaSeleccionada != -1 && columnaSeleccionada == 5) {
					String tipoServicio = tblOrdenes.getValueAt(filaSeleccionada, 3).toString();
					int idOrdenSimulado = 800 + filaSeleccionada;

					if (tipoServicio.equalsIgnoreCase("Reparación")) {
						DlgInfoReparacion dlg = new DlgInfoReparacion(idOrdenSimulado, rolUsuario);
						AppUtils.abrirDialogo(DlgListaOrdenes.this, dlg);
					} else if (tipoServicio.equalsIgnoreCase("Mantenimiento")) {
						DlgInfoMantenimiento dlg = new DlgInfoMantenimiento(idOrdenSimulado, rolUsuario);
						AppUtils.abrirDialogo(DlgListaOrdenes.this, dlg);
					}

					// Refrescar al cerrar el gestor por si hubo mutación de estados
					filtrarYMostrarOrdenes();
				}
			}
		});
	}
}