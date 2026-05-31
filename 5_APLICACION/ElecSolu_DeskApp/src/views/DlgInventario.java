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

public class DlgInventario extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private final JPanel pnlContent = new JPanel();

	private JTable tblInventario;
	private DefaultTableModel model;
	private JTextField txtNombre;
	private JButton btnBuscar;
	private JButton btnRegistrar;
	private JButton btnCerrar;
	private JLabel lblBuscarNombre;

	// ARRAY DE PRUEBA (Simula las filas de tu tabla 'productos/inventario' en la
	// base de datos)
	private Object[][] baseDatosMock = { { 1, "PROD01", "Teclado Mecánico RGB", 15, 120.50 },
			{ 2, "PROD02", "Mouse Gaming Óptico", 30, 45.00 }, { 3, "PROD03", "Monitor 24'' Full HD", 12, 750.00 },
			{ 4, "PROD04", "Audífonos Marshall Over-Ear", 8, 420.00 } };

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DlgInventario dialog = new DlgInventario();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DlgInventario() {
		setTitle("Inventario");
		setResizable(false);
		setBounds(100, 100, 600, 300);
		getContentPane().setLayout(new BorderLayout());
		pnlContent.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(pnlContent, BorderLayout.CENTER);
		pnlContent.setLayout(null);

		lblBuscarNombre = new JLabel("Buscar por Nombre:");
		lblBuscarNombre.setBounds(10, 14, 130, 20);
		pnlContent.add(lblBuscarNombre);

		txtNombre = new JTextField();
		txtNombre.setBounds(145, 14, 110, 20);
		pnlContent.add(txtNombre);
		txtNombre.setColumns(10);

		btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(this);
		btnBuscar.setBounds(265, 14, 85, 20);
		pnlContent.add(btnBuscar);

		JScrollPane scp = new JScrollPane();
		scp.setBounds(10, 45, 444, 200);
		pnlContent.add(scp);

		// Modelo con ID oculto al inicio y las dos columnas de acción al final
		model = new DefaultTableModel(new Object[][] {},
				new String[] { "ID", "Código", "Nombre", "Stock", "Precio", "", "" }) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false; // Bloquea la edición manual directa en la celda
			}
		};

		tblInventario = new JTable();
		tblInventario.setModel(model);
		tblInventario.setEnabled(true);
		tblInventario.getTableHeader().setResizingAllowed(false);
		tblInventario.getTableHeader().setReorderingAllowed(false);

		// Ocultar la columna ID (índice 0)
		tblInventario.getColumnModel().getColumn(0).setMinWidth(0);
		tblInventario.getColumnModel().getColumn(0).setMaxWidth(0);
		tblInventario.getColumnModel().getColumn(0).setPreferredWidth(0);

		// Configurar anchos de las columnas visibles
		tblInventario.getColumnModel().getColumn(1).setPreferredWidth(65); // Código
		tblInventario.getColumnModel().getColumn(2).setPreferredWidth(149); // Nombre
		tblInventario.getColumnModel().getColumn(3).setPreferredWidth(55); // Stock
		tblInventario.getColumnModel().getColumn(4).setPreferredWidth(55); // Precio
		tblInventario.getColumnModel().getColumn(5).setPreferredWidth(60); // Info
		tblInventario.getColumnModel().getColumn(6).setPreferredWidth(60); // Eliminar

		// Escuchador de clics optimizado para DOBLE CLIC
		tblInventario.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				int fila = tblInventario.getSelectedRow();
				int col = tblInventario.getSelectedColumn();

				if (fila != -1) {
					if (evt.getClickCount() == 2) {
						int idProducto = (int) tblInventario.getValueAt(fila, 0);
						String codigoProd = tblInventario.getValueAt(fila, 1).toString();

						if (col == 6) {
							eliminarProducto(idProducto, codigoProd);
						} else {
							infoProducto(idProducto);
						}
					}
				}
			}
		});

		scp.setViewportView(tblInventario);

		btnRegistrar = new JButton("Registrar");
		btnRegistrar.addActionListener(this);
		btnRegistrar.setBounds(464, 45, 110, 23);
		pnlContent.add(btnRegistrar);

		btnCerrar = new JButton("Cerrar");
		btnCerrar.addActionListener(this);
		btnCerrar.setBounds(464, 78, 110, 23);
		pnlContent.add(btnCerrar);

		listarInventario();
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnBuscar) {
			actionPerformedBtnBuscar(e);
		}
		if (e.getSource() == btnRegistrar) {
			actionPerformedBtnGestionar(e);
		}
		if (e.getSource() == btnCerrar) {
			actionPerformedBtnCerrar(e);
		}
	}

	protected void actionPerformedBtnBuscar(ActionEvent e) {
		String nombre = txtNombre.getText().trim();

		// Si el campo está vacío, restablece la tabla completa
		if (nombre.isEmpty()) {
			listarInventario();
			return;
		}

		model.setRowCount(0);
		boolean encontrado = false;

		// Búsqueda por coincidencia de texto (búsqueda parcial)
		for (int i = 0; i < baseDatosMock.length; i++) {
			String nombreMock = baseDatosMock[i][2].toString();

			if (nombreMock.toLowerCase().contains(nombre.toLowerCase())) {
				model.addRow(new Object[] { baseDatosMock[i][0], baseDatosMock[i][1], baseDatosMock[i][2],
						baseDatosMock[i][3], baseDatosMock[i][4], "ℹ️ Info", "❌" });
				encontrado = true;
			}
		}

		if (!encontrado) {
			Mensajes.mensajeError(this, "No se encontró ningún producto con el nombre ingresado.");
			listarInventario();
			txtNombre.setText("");
			txtNombre.requestFocus();
		}
	}

	protected void actionPerformedBtnGestionar(ActionEvent e) {
		DlgRegistrarInventario dlg = new DlgRegistrarInventario();
		AppUtils.abrirDialogo(this, dlg);
	}

	protected void actionPerformedBtnCerrar(ActionEvent e) {
		dispose();
	}

	private void listarInventario() {
		model.setRowCount(0);
		for (int i = 0; i < baseDatosMock.length; i++) {
			model.addRow(new Object[] { baseDatosMock[i][0], baseDatosMock[i][1], baseDatosMock[i][2],
					baseDatosMock[i][3], baseDatosMock[i][4], "ℹ️ Info", "❌" });
		}
	}

	// Abre el diálogo de información pasando el ID seleccionado de forma segura
	protected void infoProducto(int idProducto) {
		DlgInfoInventario dlg = new DlgInfoInventario(idProducto);
		AppUtils.abrirDialogo(this, dlg);
	}

	protected void eliminarProducto(int idProducto, String codigo) {
		boolean acepto = Mensajes.mensajeConfirmar(this,
				"¿Está seguro de que desea eliminar el producto con código: " + codigo + "?");

		if (acepto) {
			Mensajes.mensajeExito(this, "El producto con código " + codigo + " fue eliminado exitosamente.", "");
			listarInventario();
		}
	}
}