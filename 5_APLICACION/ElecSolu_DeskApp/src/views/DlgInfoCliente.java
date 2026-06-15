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
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import utils.Mensajes;

public class DlgInfoCliente extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private final JPanel pnlContent = new JPanel();

	private JTextField txtDni;
	private JTextField txtNombres;
	private JTextField txtApellidos;
	private JTextField txtNumero;
	private JTextField txtEmail;
	private JButton btnActualizar;
	private JButton btnCerrar;
	private JLabel lblDni;
	private JLabel lblNombres;
	private JLabel lblApellidos;
	private JLabel lblNumero;
	private JLabel lblEmail;

	private int idCliente;
	private String dniOriginal = "";
	private String nombresOriginal = "";
	private String apellidosOriginal = "";
	private String numeroOriginal = "";
	private String emailOriginal = "";

	// ARRAY DE PRUEBA COMPLETO (Simula tu tabla 'clientes' con todos sus campos)
	private Object[][] baseDatosMock = { { 1, "12345678", "Juan", "Pérez", "999888777", "juan.perez@gmail.com" },
			{ 2, "87654321", "María", "Gómez", "987654321", "maria.gomez@hotmail.com" },
			{ 3, "77665544", "Carlos", "López", "955443322", "carlos.lopez@outlook.com" },
			{ 4, "12345678", "Ana", "Torres", "911223344", "ana.torres@gmail.com" } };

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DlgInfoCliente dialog = new DlgInfoCliente(1);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DlgInfoCliente(int idCliente) {
		this.idCliente = idCliente;

		setTitle("Información del Cliente");
		setResizable(false);
		setBounds(100, 100, 450, 245);
		getContentPane().setLayout(new BorderLayout());
		pnlContent.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(pnlContent, BorderLayout.CENTER);
		pnlContent.setLayout(null);

		lblDni = new JLabel("DNI:");
		lblDni.setBounds(20, 20, 80, 20);
		pnlContent.add(lblDni);

		txtDni = new JTextField();
		txtDni.setBounds(110, 20, 190, 20);
		pnlContent.add(txtDni);
		txtDni.setColumns(10);

		lblNombres = new JLabel("Nombres:");
		lblNombres.setBounds(20, 55, 80, 20);
		pnlContent.add(lblNombres);

		txtNombres = new JTextField();
		txtNombres.setBounds(110, 55, 190, 20);
		pnlContent.add(txtNombres);
		txtNombres.setColumns(10);

		lblApellidos = new JLabel("Apellidos:");
		lblApellidos.setBounds(20, 90, 80, 20);
		pnlContent.add(lblApellidos);

		txtApellidos = new JTextField();
		txtApellidos.setBounds(110, 90, 190, 20);
		pnlContent.add(txtApellidos);
		txtApellidos.setColumns(10);

		lblNumero = new JLabel("Número:");
		lblNumero.setBounds(20, 125, 80, 20);
		pnlContent.add(lblNumero);

		txtNumero = new JTextField();
		txtNumero.setBounds(110, 125, 190, 20);
		pnlContent.add(txtNumero);
		txtNumero.setColumns(10);

		lblEmail = new JLabel("Email:");
		lblEmail.setBounds(20, 160, 80, 20);
		pnlContent.add(lblEmail);

		txtEmail = new JTextField();
		txtEmail.setBounds(110, 160, 190, 20);
		pnlContent.add(txtEmail);
		txtEmail.setColumns(10);

		btnActualizar = new JButton("Actualizar");
		btnActualizar.addActionListener(this);
		btnActualizar.setBounds(320, 19, 100, 23);
		btnActualizar.setEnabled(false);
		pnlContent.add(btnActualizar);

		btnCerrar = new JButton("Cerrar");
		btnCerrar.addActionListener(this);
		btnCerrar.setBounds(320, 54, 100, 23);
		pnlContent.add(btnCerrar);

		datosCliente();
		DocumentListenerTxt();
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnActualizar) {
			actionPerformedBtnActualizar(e);
		}
		if (e.getSource() == btnCerrar) {
			dispose();
		}
	}

	protected void actionPerformedBtnActualizar(ActionEvent e) {
		// Al estar todo validado en tiempo real por evaluarCambios(),
		// este método ejecuta directamente la acción de éxito.
		Mensajes.mensajeExito(this, "Cliente actualizado exitosamente.", "Actualización Exitosa");
		dispose();
	}

	private void datosCliente() {
		for (int i = 0; i < baseDatosMock.length; i++) {
			int idMock = (int) baseDatosMock[i][0];

			if (idMock == this.idCliente) {
				dniOriginal = baseDatosMock[i][1].toString();
				nombresOriginal = baseDatosMock[i][2].toString();
				apellidosOriginal = baseDatosMock[i][3].toString();
				numeroOriginal = baseDatosMock[i][4].toString();
				emailOriginal = baseDatosMock[i][5].toString();

				setDni(dniOriginal);
				setNombres(nombresOriginal);
				setApellidos(apellidosOriginal);
				setNumero(numeroOriginal);
				setEmail(emailOriginal);
				break;
			}
		}
	}

	private void DocumentListenerTxt() {
		DocumentListener dl = new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				evaluarCampos();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				evaluarCampos();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				evaluarCampos();
			}
		};

		txtDni.getDocument().addDocumentListener(dl);
		txtNombres.getDocument().addDocumentListener(dl);
		txtApellidos.getDocument().addDocumentListener(dl);
		txtNumero.getDocument().addDocumentListener(dl);
		txtEmail.getDocument().addDocumentListener(dl);
	}

	private void evaluarCampos() {
		// 1. Verificar si el usuario alteró algún campo respecto al original
		boolean huboCambios = !getDni().equals(dniOriginal) || !getNombres().equals(nombresOriginal)
				|| !getApellidos().equals(apellidosOriginal) || !getNumero().equals(numeroOriginal)
				|| !getEmail().equals(emailOriginal);

		// 2. Validaciones estrictas de formato en tiempo real
		boolean dniValido = getDni().matches("\\d{8}");
		boolean nombresValidos = !getNombres().isEmpty() && !getNombres().matches(".*\\d.*");
		boolean apellidosValidos = !getApellidos().isEmpty() && !getApellidos().matches(".*\\d.*");

		// Reglas para Teléfono y Email (Deben ser correctos si se llenan, y no estar
		// ambos vacíos)
		boolean numeroValido = getNumero().isEmpty() || getNumero().matches("\\d{9}");
		boolean emailValido = getEmail().isEmpty() || getEmail().contains("@");
		boolean tieneContacto = !getNumero().isEmpty() || !getEmail().isEmpty();

		boolean contactoValido = numeroValido && emailValido && tieneContacto;

		// 3. Evaluar combinación final
		boolean todoFormatoValido = dniValido && nombresValidos && apellidosValidos && contactoValido;

		// Solo se activa si hay cambios REALES y CUMPLE con todas las condiciones
		btnActualizar.setEnabled(huboCambios && todoFormatoValido);
	}

	// --- METODOS DE ACCESO (GETTERS Y SETTERS) ---

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