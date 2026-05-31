package views;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import utils.AppUtils;
import utils.Mensajes;

public class DlgClientes extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private final JPanel pnlContent = new JPanel();

	private JTable tblClientes;
	private DefaultTableModel model;
	private JTextField txtDni;
	private JButton btnBuscar;
	private JButton btnRegistrar;
	private JButton btnCerrar;
	private JLabel lblBuscarDni;

	// ARRAY DE PRUEBA (Simula las filas de tu tabla 'clientes' en la base de datos)
	private Object[][] baseDatosMock = { { 1, "12345678", "Pérez", "Juan" }, { 2, "87654321", "Gómez", "María" },
			{ 3, "77665544", "López", "Carlos" }, { 4, "12345678", "Torres", "Ana" } };

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DlgClientes dialog = new DlgClientes();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DlgClientes() {
		setTitle("Clientes");
		setResizable(false);
		setBounds(100, 100, 600, 300);
		getContentPane().setLayout(new BorderLayout());
		pnlContent.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(pnlContent, BorderLayout.CENTER);
		pnlContent.setLayout(null);

		lblBuscarDni = new JLabel("Buscar por DNI:");
		lblBuscarDni.setBounds(10, 14, 95, 20);
		pnlContent.add(lblBuscarDni);

		txtDni = new JTextField();
		txtDni.setBounds(110, 14, 110, 20);
		pnlContent.add(txtDni);
		txtDni.setColumns(10);

		btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(this);
		btnBuscar.setBounds(230, 14, 85, 20);
		pnlContent.add(btnBuscar);

		JScrollPane scp = new JScrollPane();
		scp.setBounds(10, 45, 444, 200);
		pnlContent.add(scp);

		model = new DefaultTableModel(new Object[][] {}, new String[] { "ID", "DNI", "Apellido", "Nombre", "", "" }) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false; // Bloquea la edición manual
			}
		};

		tblClientes = new JTable();
		tblClientes.setModel(model);
		tblClientes.setEnabled(true);
		tblClientes.getTableHeader().setResizingAllowed(false);
		tblClientes.getTableHeader().setReorderingAllowed(false);

		// Ocultar la columna ID (índice 0)
		tblClientes.getColumnModel().getColumn(0).setMinWidth(0);
		tblClientes.getColumnModel().getColumn(0).setMaxWidth(0);
		tblClientes.getColumnModel().getColumn(0).setPreferredWidth(0);

		// Configurar anchos de las columnas visibles
		tblClientes.getColumnModel().getColumn(1).setPreferredWidth(80); // DNI
		tblClientes.getColumnModel().getColumn(2).setPreferredWidth(130); // Apellido
		tblClientes.getColumnModel().getColumn(3).setPreferredWidth(130); // Nombre
		tblClientes.getColumnModel().getColumn(4).setPreferredWidth(60); // Info
		tblClientes.getColumnModel().getColumn(5).setPreferredWidth(60); // Eliminar

		// Escuchador de clics optimizado para DOBLE CLIC
		tblClientes.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				int fila = tblClientes.getSelectedRow();
				int col = tblClientes.getSelectedColumn();

				if (fila != -1) {
					if (evt.getClickCount() == 2) {
						int idCliente = (int) tblClientes.getValueAt(fila, 0);
						String dniCliente = tblClientes.getValueAt(fila, 1).toString();

						if (col == 5) {
							eliminarCliente(idCliente, dniCliente);
						} else {
							infoCliente(idCliente);
						}
					}
				}
			}
		});

		scp.setViewportView(tblClientes);

		btnRegistrar = new JButton("Registrar");
		btnRegistrar.addActionListener(this);
		btnRegistrar.setBounds(464, 45, 110, 23);
		pnlContent.add(btnRegistrar);

		btnCerrar = new JButton("Cerrar");
		btnCerrar.addActionListener(this);
		btnCerrar.setBounds(464, 78, 110, 23);
		pnlContent.add(btnCerrar);

		listarClientes();
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnBuscar) {
			actionPerformedBtnBuscar(e);
		}
		if (e.getSource() == btnRegistrar) {
			actionPerformedBtnRegistrar(e);
		}
		if (e.getSource() == btnCerrar) {
			actionPerformedBtnCerrar(e);
		}
	}

	protected void actionPerformedBtnBuscar(ActionEvent e) {
		String dni = txtDni.getText().trim();

		// Si el campo está vacío, se limpia el filtro mostrando toda la tabla
		if (dni.isEmpty()) {
			listarClientes();
			return;
		}

		// Validación de formato numérico (solo si contiene texto)
		if (!dni.matches("\\d{8}")) {
			Mensajes.mensajeError(this, "El DNI debe estar compuesto únicamente por 8 dígitos numéricos.");
			txtDni.requestFocus();
			txtDni.setText("");
			return;
		}

		model.setRowCount(0);
		boolean encontrado = false;

		for (int i = 0; i < baseDatosMock.length; i++) {
			String dniMock = baseDatosMock[i][1].toString();

			if (dniMock.equals(dni)) {
				model.addRow(new Object[] { baseDatosMock[i][0], baseDatosMock[i][1], baseDatosMock[i][2],
						baseDatosMock[i][3], "ℹ️ Info", "❌" });
				encontrado = true;
			}
		}

		if (!encontrado) {
			Mensajes.mensajeError(this, "No se encontró ningún cliente con el DNI ingresado.");
			listarClientes();
			txtDni.setText("");
			txtDni.requestFocus();
		}
	}

	protected void actionPerformedBtnRegistrar(ActionEvent e) {
		DlgRegistrarCliente dlg = new DlgRegistrarCliente();
		AppUtils.abrirDialogo(this, dlg);
	}

	protected void actionPerformedBtnCerrar(ActionEvent e) {
		dispose();
	}

	private void listarClientes() {
		model.setRowCount(0);
		for (int i = 0; i < baseDatosMock.length; i++) {
			model.addRow(new Object[] { baseDatosMock[i][0], baseDatosMock[i][1], baseDatosMock[i][2],
					baseDatosMock[i][3], "ℹ️ Info", "❌" });
		}
	}

	protected void infoCliente(int idCliente) {
		DlgInfoCliente dlg = new DlgInfoCliente(idCliente);
		AppUtils.abrirDialogo(this, dlg);
	}

	protected void eliminarCliente(int idCliente, String dni) {
		boolean acepto = Mensajes.mensajeConfirmar(this,
				"¿Está seguro de que desea eliminar al cliente con DNI: " + dni + "?");

		if (acepto) {
			Mensajes.mensajeExito(this, "El cliente con DNI " + dni + " fue eliminado exitosamente.", "");
			listarClientes();
		}
	}
}