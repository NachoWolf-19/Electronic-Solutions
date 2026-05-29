package views;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import utils.Mensajes;

public class DlgMantenClientes extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private final JPanel pnlContent = new JPanel();

	private JTextField txtDni;
	private JTextField txtNombres;
	private JTextField txtApellidos;
	private JTextField txtNumero;
	private JTextField txtEmail;
	private JComboBox<String> cboOperacion;
	private JButton btnProcesar;
	private JButton btnCerrar;
	private JButton btnBuscar;

	private JLabel lblOperacion;
	private JLabel lblDni;
	private JLabel lblNombres;
	private JLabel lblApellidos;
	private JLabel lblNumero;
	private JLabel lblEmail;

	public static void main(String[] args) {
		try {
			DlgMantenClientes dialog = new DlgMantenClientes();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DlgMantenClientes() {
		setResizable(false);
		setTitle("Mantenimiento de Clientes");
		setBounds(100, 100, 460, 275);
		getContentPane().setLayout(new BorderLayout());
		pnlContent.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(pnlContent, BorderLayout.CENTER);
		pnlContent.setLayout(null);

		lblOperacion = new JLabel("Operación:");
		lblOperacion.setBounds(20, 20, 90, 20);
		pnlContent.add(lblOperacion);

		cboOperacion = new JComboBox<String>();
		cboOperacion.addItem("Registrar");
		cboOperacion.addItem("Actualizar");
		cboOperacion.addItem("Eliminar");
		cboOperacion.setBounds(120, 20, 190, 20);
		cboOperacion.addActionListener(this);
		pnlContent.add(cboOperacion);

		lblDni = new JLabel("DNI:");
		lblDni.setBounds(20, 55, 90, 20);
		pnlContent.add(lblDni);

		txtDni = new JTextField();
		txtDni.setBounds(120, 55, 190, 20);
		pnlContent.add(txtDni);
		txtDni.setColumns(10);

		lblNombres = new JLabel("Nombres:");
		lblNombres.setBounds(20, 90, 90, 20);
		pnlContent.add(lblNombres);

		txtNombres = new JTextField();
		txtNombres.setBounds(120, 90, 190, 20);
		pnlContent.add(txtNombres);
		txtNombres.setColumns(10);

		lblApellidos = new JLabel("Apellidos:");
		lblApellidos.setBounds(20, 125, 90, 20);
		pnlContent.add(lblApellidos);

		txtApellidos = new JTextField();
		txtApellidos.setBounds(120, 125, 190, 20);
		pnlContent.add(txtApellidos);
		txtApellidos.setColumns(10);

		lblNumero = new JLabel("Número:");
		lblNumero.setBounds(20, 160, 90, 20);
		pnlContent.add(lblNumero);

		txtNumero = new JTextField();
		txtNumero.setBounds(120, 160, 190, 20);
		pnlContent.add(txtNumero);
		txtNumero.setColumns(10);

		lblEmail = new JLabel("Email:");
		lblEmail.setBounds(20, 195, 90, 20);
		pnlContent.add(lblEmail);

		txtEmail = new JTextField();
		txtEmail.setBounds(120, 195, 190, 20);
		pnlContent.add(txtEmail);
		txtEmail.setColumns(10);

		// --- Columna botones (x=330) ---
		btnProcesar = new JButton("Procesar");
		btnProcesar.addActionListener(this);
		btnProcesar.setBounds(330, 19, 100, 23);
		pnlContent.add(btnProcesar);

		btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(this);
		btnBuscar.setBounds(330, 54, 100, 23);
		pnlContent.add(btnBuscar);

		btnCerrar = new JButton("Cerrar");
		btnCerrar.addActionListener(this);
		btnCerrar.setBounds(330, 89, 100, 23);
		pnlContent.add(btnCerrar);

		activarEstadoCampos();
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == cboOperacion) {
			activarEstadoCampos();
		}
		if (e.getSource() == btnBuscar) {
			actionPerformedBtnBuscar(e);
		}
		if (e.getSource() == btnProcesar) {
			actionPerformedBtnProcesar(e);
		}
		if (e.getSource() == btnCerrar) {
			dispose();
		}
	}

	protected void actionPerformedBtnBuscar(ActionEvent e) {
		if (getDni().matches("\\d{8}")) {
			if (buscarCliente()) {
				btnProcesar.setEnabled(true);
			} else {
				Mensajes.mensajeError(this, "No se encontró el cliente con DNI: " + getDni());
				btnProcesar.setEnabled(false);
				txtDni.requestFocus();
				txtDni.setText("");
			}
		} else {
			Mensajes.mensajeError(this, "El DNI debe tener 8 dígitos.");
			txtDni.requestFocus();
			txtDni.setText("");
		}
	}

	protected void actionPerformedBtnProcesar(ActionEvent e) {
		int op = getOperacion();

		switch (op) {
		case 0:
			registrarCliente();
			break;
		case 1:
			actualizarCliente();
			break;
		default:
			eliminarCliente();
			break;
		}
	}

	private void activarEstadoCampos() {
		int op = getOperacion();
		boolean esRegistro = (op == 0);

		txtNombres.setEnabled(esRegistro);
		txtApellidos.setEnabled(esRegistro);
		txtNumero.setEnabled(esRegistro);
		txtEmail.setEnabled(esRegistro);

		btnBuscar.setEnabled(!esRegistro);
		btnProcesar.setEnabled(esRegistro);
	}

	private void registrarCliente() {
		if (!getDni().matches("\\d{8}")) {
			Mensajes.mensajeError(this, "El DNI debe tener 8 dígitos numéricos.");
			setDni("");
			return;
		}

		if (getNombres().matches(".*\\d.*") || getApellidos().matches(".*\\d.*")) {
			Mensajes.mensajeError(this, "Nombres o Apellidos no pueden contener números.");
			return;
		}

		if (getEmail().isEmpty() && getNumero().isEmpty()) {
			Mensajes.mensajeError(this, "Debe ingresar al menos un email o un número de teléfono.");
			return;
		}

		if (!getEmail().isEmpty() && !getEmail().contains("@")) {
			Mensajes.mensajeError(this, "El email debe contener un '@'.");
			setEmail("");
			return;
		}

		if (!getNumero().isEmpty() && !getNumero().matches("\\d{9}")) {
			Mensajes.mensajeError(this, "El número debe tener 9 dígitos.");
			setNumero("");
			return;
		}

		Mensajes.mensajeExito(this, "Cliente registrado exitosamente.", "Registro Exitoso");
	}

	private void actualizarCliente() {
	}

	private void eliminarCliente() {
	}

	private boolean buscarCliente() {
		return false;
	}

	public String getDni() {
		return txtDni.getText().trim();
	}

	public void setDni(String dni) {
		txtDni.setText(dni);
	}

	public String getNombres() {
		return txtNombres.getText().trim();
	}

	public void setNombres(String nombres) {
		txtNombres.setText(nombres);
	}

	public String getApellidos() {
		return txtApellidos.getText().trim();
	}

	public void setApellidos(String apellidos) {
		txtNombres.setText(apellidos);
	}

	public String getNumero() {
		return txtNumero.getText().trim();
	}

	public void setNumero(String Numero) {
		txtNumero.setText(Numero);
	}

	public String getEmail() {
		return txtEmail.getText().trim();
	}

	public void setEmail(String Email) {
		txtNumero.setText(Email);
	}

	public int getOperacion() {
		return cboOperacion.getSelectedIndex();
	}
}