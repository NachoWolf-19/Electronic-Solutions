package views;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.Insets;
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
import javax.swing.table.TableCellRenderer;

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

	// ARRAY DE PRUEBA
	private Object[][] baseDatosMock = { { 1, "RP0001", "Placa Principal PCB - Smart TV 4K", 12, 180.00 },
			{ 2, "RP0002", "Batería de Litio 4000mAh (Smartphone)", 25, 45.50 },
			{ 3, "RP0003", "Set de Hélices de Repuesto (Dron)", 40, 25.00 },
			{ 4, "RP0004", "Módulo Memoria Flash NAND (Original)", 15, 65.00 } };

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

		model = new DefaultTableModel(new Object[][] {},
				new String[] { "ID", "Código", "Nombre", "Stock", "Precio", "Info", "Eliminar" }) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		tblInventario = new JTable();
		tblInventario.setModel(model);
		tblInventario.setEnabled(true);
		tblInventario.setRowHeight(24);
		tblInventario.getTableHeader().setResizingAllowed(false);
		tblInventario.getTableHeader().setReorderingAllowed(false);

		// Ocultar la columna ID (índice 0)
		tblInventario.getColumnModel().getColumn(0).setMinWidth(0);
		tblInventario.getColumnModel().getColumn(0).setMaxWidth(0);
		tblInventario.getColumnModel().getColumn(0).setPreferredWidth(0);

		// Configurar anchos de las columnas visibles (Total 444px)
		tblInventario.getColumnModel().getColumn(1).setPreferredWidth(65); // Código
		tblInventario.getColumnModel().getColumn(2).setPreferredWidth(139); // Nombre
		tblInventario.getColumnModel().getColumn(3).setPreferredWidth(50); // Stock
		tblInventario.getColumnModel().getColumn(4).setPreferredWidth(50); // Precio
		tblInventario.getColumnModel().getColumn(5).setPreferredWidth(65); // Cabecera Info
		tblInventario.getColumnModel().getColumn(6).setPreferredWidth(75); // Cabecera Eliminar

		// Asignar el renderizador de botón físico a las columnas de acción (5 y 6)
		tblInventario.getColumnModel().getColumn(5).setCellRenderer(new ButtonRenderer());
		tblInventario.getColumnModel().getColumn(6).setCellRenderer(new ButtonRenderer());

		// Escuchador de clics inteligente (1 clic para botones reales, 2 clics para el
		// resto de la fila)
		tblInventario.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				int fila = tblInventario.getSelectedRow();
				int col = tblInventario.getSelectedColumn();

				if (fila != -1) {
					int idProducto = (int) tblInventario.getValueAt(fila, 0);
					String codigoProd = tblInventario.getValueAt(fila, 1).toString();

					if (evt.getClickCount() == 1) {
						if (col == 5) {
							infoProducto(idProducto);
						} else if (col == 6) {
							eliminarProducto(idProducto, codigoProd);
						}
					} else if (evt.getClickCount() == 2 && col < 5) {
						infoProducto(idProducto);
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

	// Clase interna boton (info y eliminar)
	private class ButtonRenderer extends JPanel implements TableCellRenderer {
		private static final long serialVersionUID = 1L;
		private JButton button;

		public ButtonRenderer() {
			setLayout(new GridBagLayout());
			setOpaque(true);

			button = new JButton();
			button.setMargin(new Insets(0, 0, 0, 0));
			button.setPreferredSize(new Dimension(25, 18)); // CORREGIDO: Un poco menos ancho (45x18)

			add(button);
		}

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			button.setText(value != null ? value.toString() : "");

			if (isSelected) {
				setBackground(table.getSelectionBackground());
			} else {
				setBackground(table.getBackground());
			}

			return this;
		}
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

		if (nombre.isEmpty()) {
			listarInventario();
			return;
		}

		model.setRowCount(0);
		boolean encontrado = false;

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
					baseDatosMock[i][3], baseDatosMock[i][4], "ℹ️", "❌" });
		}
	}

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