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
	private JButton btnGestionar;
	private JButton btnCerrar;
	private JLabel lblBuscarDni;

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

		model = new DefaultTableModel(new Object[][] {}, new String[] { "DNI", "Apellido", "Nombre" });

		tblClientes = new JTable();
		tblClientes.setModel(model);
		tblClientes.setEnabled(false);
		tblClientes.getTableHeader().setResizingAllowed(false);
		tblClientes.getTableHeader().setReorderingAllowed(false);

		tblClientes.getColumnModel().getColumn(0).setPreferredWidth(94);
		tblClientes.getColumnModel().getColumn(1).setPreferredWidth(175);
		tblClientes.getColumnModel().getColumn(2).setPreferredWidth(175);

		scp.setViewportView(tblClientes);

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
		String dni = txtDni.getText().trim();

		if (!dni.matches("\\d{8}")) {
			Mensajes.mensajeError(this, "El DNI debe estar compuesto únicamente por 8 dígitos numéricos.");
			txtDni.requestFocus();
			txtDni.setText("");
			return;
		}

		boolean encontrado = false;

		if (!encontrado) {
			Mensajes.mensajeError(this, "No se encontró ningún cliente con el DNI ingresado.");
			txtDni.setText("");
			txtDni.requestFocus();
		} else {
			// Lógica para cargar filas en el modelo
		}
	}

	protected void actionPerformedBtnGestionar(ActionEvent e) {
		DlgMantenClientes dlg = new DlgMantenClientes();
		AppUtils.abrirDialogo(this, dlg);
	}

	protected void actionPerformedBtnCerrar(ActionEvent e) {
		dispose();
	}
}