package views;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import utils.Mensajes;

public class DlgRegistrarCliente extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private final JPanel pnlContent = new JPanel();

	private JTextField txtDni;
	private JTextField txtNombres;
	private JTextField txtApellidos;
	private JTextField txtNumero;
	private JTextField txtEmail;
	private JButton btnProcesar;
	private JButton btnCerrar;
	private JLabel lblDni;
	private JLabel lblNombres;
	private JLabel lblApellidos;
	private JLabel lblNumero;
	private JLabel lblEmail;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DlgRegistrarCliente dialog = new DlgRegistrarCliente();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DlgRegistrarCliente() {
		setTitle("Registrar Cliente");
		setResizable(false);
		setBounds(100, 100, 450, 245);
		getContentPane().setLayout(new BorderLayout());
		pnlContent.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(pnlContent, BorderLayout.CENTER);
		pnlContent.setLayout(null);

		// --- FILA 1: DNI (y=20) ---
		lblDni = new JLabel("DNI:");
		lblDni.setBounds(20, 20, 80, 20);
		pnlContent.add(lblDni);

		txtDni = new JTextField();
		txtDni.setBounds(110, 20, 190, 20);
		pnlContent.add(txtDni);
		txtDni.setColumns(10);

		// --- FILA 2: Nombres (y=55) ---
		lblNombres = new JLabel("Nombres:");
		lblNombres.setBounds(20, 55, 80, 20);
		pnlContent.add(lblNombres);

		txtNombres = new JTextField();
		txtNombres.setBounds(110, 55, 190, 20);
		pnlContent.add(txtNombres);
		txtNombres.setColumns(10);

		// --- FILA 3: Apellidos (y=90) ---
		lblApellidos = new JLabel("Apellidos:");
		lblApellidos.setBounds(20, 90, 80, 20);
		pnlContent.add(lblApellidos);

		txtApellidos = new JTextField();
		txtApellidos.setBounds(110, 90, 190, 20);
		pnlContent.add(txtApellidos);
		txtApellidos.setColumns(10);

		// --- FILA 4: Número (y=125) ---
		lblNumero = new JLabel("Número:");
		lblNumero.setBounds(20, 125, 80, 20);
		pnlContent.add(lblNumero);

		txtNumero = new JTextField();
		txtNumero.setBounds(110, 125, 190, 20);
		pnlContent.add(txtNumero);
		txtNumero.setColumns(10);

		// --- FILA 5: Email (y=160) ---
		lblEmail = new JLabel("Email:");
		lblEmail.setBounds(20, 160, 80, 20);
		pnlContent.add(lblEmail);

		txtEmail = new JTextField();
		txtEmail.setBounds(110, 160, 190, 20);
		pnlContent.add(txtEmail);
		txtEmail.setColumns(10);

		// --- COLUMNA DE BOTONES (x=320) ---
		btnProcesar = new JButton("Procesar");
		btnProcesar.addActionListener(this);
		btnProcesar.setBounds(320, 19, 100, 23);
		pnlContent.add(btnProcesar);

		btnCerrar = new JButton("Cerrar");
		btnCerrar.addActionListener(this);
		btnCerrar.setBounds(320, 54, 100, 23);
		pnlContent.add(btnCerrar);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnProcesar) {
			actionPerformedBtnProcesar(e);
		}
		if (e.getSource() == btnCerrar) {
			dispose();
		}
	}

	protected void actionPerformedBtnProcesar(ActionEvent e) {
		registrarCliente();
	}

	private void registrarCliente() {
		if (!getDni().matches("\\d{8}")) {
			Mensajes.mensajeError(this, "El DNI debe tener 8 dígitos numéricos.");
			setDni("");
			txtDni.requestFocus();
			return;
		}

		if (getNombres().isEmpty() || getApellidos().isEmpty()) {
			Mensajes.mensajeError(this, "Debe ingresar los nombres y apellidos del cliente.");
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
			txtEmail.requestFocus();
			return;
		}

		if (!getNumero().isEmpty() && !getNumero().matches("\\d{9}")) {
			Mensajes.mensajeError(this, "El número debe tener 9 dígitos.");
			setNumero("");
			txtNumero.requestFocus();
			return;
		}

		Mensajes.mensajeExito(this, "Cliente registrado exitosamente.", "Registro Exitoso");
		dispose();
	}

	// --- METODOS DE ACCESO (GETTERS Y SETTERS CORREGIDOS) ---

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
		txtApellidos.setText(apellidos);
	}

	public String getNumero() {
		return txtNumero.getText().trim();
	}

	public void setNumero(String numero) {
		txtNumero.setText(numero);
	}

	public String getEmail() {
		return txtEmail.getText().trim();
	}

	public void setEmail(String email) {
		txtEmail.setText(email);
	}
}