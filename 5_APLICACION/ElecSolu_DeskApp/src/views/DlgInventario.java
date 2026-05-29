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
	private JButton btnGestionar;
	private JButton btnCerrar;
	private JLabel lblBuscarNombre;

	public static void main(String[] args) {
		try {
			DlgInventario dialog = new DlgInventario();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DlgInventario() {
		setTitle("Mantenimiento de Inventario");
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

		// Agregada columna "Código"
		model = new DefaultTableModel(new Object[][] {}, new String[] { "Código", "Nombre", "Stock", "Precio" });

		tblInventario = new JTable();
		tblInventario.setModel(model);
		tblInventario.setEnabled(false);
		tblInventario.getTableHeader().setResizingAllowed(false);
		tblInventario.getTableHeader().setReorderingAllowed(false);

		// Ajuste de anchos para las 4 columnas
		tblInventario.getColumnModel().getColumn(0).setPreferredWidth(60); // Código
		tblInventario.getColumnModel().getColumn(1).setPreferredWidth(180); // Nombre
		tblInventario.getColumnModel().getColumn(2).setPreferredWidth(70); // Stock
		tblInventario.getColumnModel().getColumn(3).setPreferredWidth(70); // Precio

		scp.setViewportView(tblInventario);

		btnGestionar = new JButton("Gestionar");
		btnGestionar.addActionListener(this);
		btnGestionar.setBounds(464, 45, 110, 23);
		pnlContent.add(btnGestionar);

		btnCerrar = new JButton("Cerrar");
		btnCerrar.addActionListener(this);
		btnCerrar.setBounds(464, 78, 110, 23);
		pnlContent.add(btnCerrar);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnBuscar) {
			actionPerformedBtnBuscar(e);
		}
		if (e.getSource() == btnGestionar) {
			actionPerformedBtnGestionar(e);
		}
		if (e.getSource() == btnCerrar) {
			actionPerformedBtnCerrar(e);
		}
	}

	protected void actionPerformedBtnBuscar(ActionEvent e) {
		String nombre = txtNombre.getText().trim();

		if (nombre.isEmpty()) {
			Mensajes.mensajeError(this, "Debe ingresar un nombre para realizar la búsqueda.");
			txtNombre.requestFocus();
			return;
		}

		boolean encontrado = false;

		if (!encontrado) {
			Mensajes.mensajeError(this, "No se encontró ningún producto con el nombre ingresado.");
			txtNombre.setText("");
			txtNombre.requestFocus();
		} else {
			// Lógica para cargar filas en el modelo si se encuentra
		}
	}

	protected void actionPerformedBtnGestionar(ActionEvent e) {
		DlgMantenInventario dlg = new DlgMantenInventario();
		AppUtils.abrirDialogo(this, dlg);
	}

	protected void actionPerformedBtnCerrar(ActionEvent e) {
		dispose();
	}
}