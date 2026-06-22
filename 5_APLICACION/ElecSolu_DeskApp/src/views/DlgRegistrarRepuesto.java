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

public class DlgRegistrarRepuesto extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private final JPanel pnlContent = new JPanel();

	private JTextField txtCodigo;
	private JTextField txtNombre;
	private JTextField txtStock;
	private JTextField txtPrecio;
	private JButton btnProcesar;
	private JButton btnCerrar;
	private JLabel lblCodigo;
	private JLabel lblNombre;
	private JLabel lblStock;
	private JLabel lblPrecio;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DlgRegistrarRepuesto dialog = new DlgRegistrarRepuesto();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DlgRegistrarRepuesto() {
		setTitle("Registrar Repuesto");
		setResizable(false);
		setBounds(100, 100, 460, 210); // Altura ajustada simétricamente
		getContentPane().setLayout(new BorderLayout());
		pnlContent.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(pnlContent, BorderLayout.CENTER);
		pnlContent.setLayout(null);

		// --- FILA 1: Código (y=20) ---
		lblCodigo = new JLabel("Código:");
		lblCodigo.setBounds(20, 20, 90, 20);
		pnlContent.add(lblCodigo);

		txtCodigo = new JTextField();
		txtCodigo.setBounds(120, 20, 190, 20);
		pnlContent.add(txtCodigo);
		txtCodigo.setColumns(10);

		// --- FILA 2: Nombre (y=55) ---
		lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(20, 55, 90, 20);
		pnlContent.add(lblNombre);

		txtNombre = new JTextField();
		txtNombre.setBounds(120, 55, 190, 20);
		pnlContent.add(txtNombre);
		txtNombre.setColumns(10);

		// --- FILA 3: Stock (y=90) ---
		lblStock = new JLabel("Stock:");
		lblStock.setBounds(20, 90, 90, 20);
		pnlContent.add(lblStock);

		txtStock = new JTextField();
		txtStock.setBounds(120, 90, 190, 20);
		pnlContent.add(txtStock);
		txtStock.setColumns(10);

		// --- FILA 4: Precio (y=125) ---
		lblPrecio = new JLabel("Precio:");
		lblPrecio.setBounds(20, 125, 90, 20);
		pnlContent.add(lblPrecio);

		txtPrecio = new JTextField();
		txtPrecio.setBounds(120, 125, 190, 20);
		pnlContent.add(txtPrecio);
		txtPrecio.setColumns(10);

		// --- COLUMNA DE BOTONES (x=330) ---
		btnProcesar = new JButton("Procesar");
		btnProcesar.addActionListener(this);
		btnProcesar.setBounds(330, 19, 100, 23);
		btnProcesar.setEnabled(false); // Deshabilitado por defecto hasta cumplir condiciones
		pnlContent.add(btnProcesar);

		btnCerrar = new JButton("Cerrar");
		btnCerrar.addActionListener(this);
		btnCerrar.setBounds(330, 54, 100, 23);
		pnlContent.add(btnCerrar);

		activarEstadoCampos();
		configurarEscuchadoresDeTexto();
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
		int op = getOperacion();
		switch (op) {
		case 0:
			registrarInventario();
			break;
		case 1:
			actualizarInventario();
			break;
		default:
			eliminarInventario();
			break;
		}
	}

	private void activarEstadoCampos() {
		int op = getOperacion();
		boolean esRegistro = (op == 0);

		txtNombre.setEnabled(esRegistro);
		txtStock.setEnabled(esRegistro);
		txtPrecio.setEnabled(esRegistro);
		txtCodigo.setEnabled(!esRegistro); // Autogenerado/Desactivado si es un nuevo registro

		if (esRegistro) {
			setCodigo("AUTOGENERADO"); // Indicador visual clásico para el usuario
		}
	}

	private void configurarEscuchadoresDeTexto() {
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

		txtNombre.getDocument().addDocumentListener(dl);
		txtStock.getDocument().addDocumentListener(dl);
		txtPrecio.getDocument().addDocumentListener(dl);
	}

	private void evaluarCampos() {
		// Validaciones de formato estrictas en tiempo real
		boolean nombreValido = !getNombre().isEmpty();
		boolean stockValido = getStock().matches("\\d+"); // Solo enteros positivos
		boolean precioValido = getPrecio().matches("\\d+(\\.\\d+)?"); // Decimal válido

		// El botón de registro solo se activa si todo está correcto
		btnProcesar.setEnabled(nombreValido && stockValido && precioValido);
	}

	private void registrarInventario() {
		// Al estar todo validado en tiempo real, procesa directamente con seguridad
		Mensajes.mensajeExito(this, "Producto registrado exitosamente.", "Registro Exitoso");
		dispose();
	}

	private void actualizarInventario() {
	}

	private void eliminarInventario() {
	}

	// --- METODOS DE ACCESO (GETTERS Y SETTERS) ---

	public String getCodigo() {
		return txtCodigo.getText().trim();
	}

	public void setCodigo(String codigo) {
		txtCodigo.setText(codigo);
	}

	public String getNombre() {
		return txtNombre.getText().trim();
	}

	public void setNombre(String nombre) {
		txtNombre.setText(nombre);
	}

	public String getStock() {
		return txtStock.getText().trim();
	}

	public void setStock(String stock) {
		txtStock.setText(stock);
	}

	public String getPrecio() {
		return txtPrecio.getText().trim();
	}

	public void setPrecio(String precio) {
		txtPrecio.setText(precio);
	}

	public int getOperacion() {
		return 0; // 0 = Registrar
	}
}