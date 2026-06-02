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

public class DlgInfoInventario extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private final JPanel pnlContent = new JPanel();

	private JTextField txtCodigo;
	private JTextField txtNombre;
	private JTextField txtStock;
	private JTextField txtPrecio;
	private JButton btnActualizar;
	private JButton btnCerrar;
	private JLabel lblCodigo;
	private JLabel lblNombre;
	private JLabel lblStock;
	private JLabel lblPrecio;

	private int idProducto;

	// Variables para respaldar los datos iniciales y comparar cambios
	private String codigoOriginal = "";
	private String nombreOriginal = "";
	private String stockOriginal = "";
	private String precioOriginal = "";

	// ARRAY DE PRUEBA COMPLETO
	private Object[][] baseDatosMock = { { 1, "PROD01", "Teclado Mecánico RGB", 15, 120.50 },
			{ 2, "PROD02", "Mouse Gaming Óptico", 30, 45.00 }, { 3, "PROD03", "Monitor 24'' Full HD", 12, 750.00 },
			{ 4, "PROD04", "Audífonos Marshall Over-Ear", 8, 420.00 } };

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DlgInfoInventario dialog = new DlgInfoInventario(1);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DlgInfoInventario(int idProducto) {
		this.idProducto = idProducto;

		setTitle("Información del Producto");
		setResizable(false);
		setBounds(100, 100, 450, 210);
		getContentPane().setLayout(new BorderLayout());
		pnlContent.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(pnlContent, BorderLayout.CENTER);
		pnlContent.setLayout(null);

		// --- FILA 1: Código (y=20) ---
		lblCodigo = new JLabel("Código:");
		lblCodigo.setBounds(20, 20, 80, 20);
		pnlContent.add(lblCodigo);

		txtCodigo = new JTextField();
		txtCodigo.setBounds(110, 20, 190, 20);
		pnlContent.add(txtCodigo);
		txtCodigo.setColumns(10);

		// --- FILA 2: Nombre (y=55) ---
		lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(20, 55, 80, 20);
		pnlContent.add(lblNombre);

		txtNombre = new JTextField();
		txtNombre.setBounds(110, 55, 190, 20);
		pnlContent.add(txtNombre);
		txtNombre.setColumns(10);

		// --- FILA 3: Stock (y=90) ---
		lblStock = new JLabel("Stock:");
		lblStock.setBounds(20, 90, 80, 20);
		pnlContent.add(lblStock);

		txtStock = new JTextField();
		txtStock.setBounds(110, 90, 190, 20);
		pnlContent.add(txtStock);
		txtStock.setColumns(10);

		// --- FILA 4: Precio (y=125) ---
		lblPrecio = new JLabel("Precio:");
		lblPrecio.setBounds(20, 125, 80, 20);
		pnlContent.add(lblPrecio);

		txtPrecio = new JTextField();
		txtPrecio.setBounds(110, 125, 190, 20);
		pnlContent.add(txtPrecio);
		txtPrecio.setColumns(10);

		// --- COLUMNA DE BOTONES (x=320) ---
		btnActualizar = new JButton("Actualizar");
		btnActualizar.addActionListener(this);
		btnActualizar.setBounds(320, 19, 100, 23);
		btnActualizar.setEnabled(false); // Inicia deshabilitado de fábrica
		pnlContent.add(btnActualizar);

		btnCerrar = new JButton("Cerrar");
		btnCerrar.addActionListener(this);
		btnCerrar.setBounds(320, 54, 100, 23);
		pnlContent.add(btnCerrar);

		cargarDatosDelProducto();
		configurarEscuchadoresDeTexto();
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
		String codigo = getCodigo();
		String nombre = getNombre();
		String stockStr = getStock();
		String precioStr = getPrecio();

		// 1. Validar campos vacíos obligatorios
		if (codigo.isEmpty()) {
			Mensajes.mensajeError(this, "El código del producto no puede estar vacío.");
			txtCodigo.requestFocus();
			return;
		}

		if (nombre.isEmpty()) {
			Mensajes.mensajeError(this, "El nombre del producto no puede estar vacío.");
			txtNombre.requestFocus();
			return;
		}

		// 2. Validación de Stock (Entero y no negativo)
		try {
			int stock = Integer.parseInt(stockStr);
			if (stock < 0) {
				Mensajes.mensajeError(this, "El stock no puede ser un valor negativo.");
				txtStock.requestFocus();
				return;
			}
		} catch (NumberFormatException ex) {
			Mensajes.mensajeError(this, "El stock debe ser un número entero válido.");
			txtStock.requestFocus();
			return;
		}

		// 3. Validación de Precio (Decimal y no negativo)
		try {
			double precio = Double.parseDouble(precioStr);
			if (precio < 0) {
				Mensajes.mensajeError(this, "El precio no puede ser un valor negativo.");
				txtPrecio.requestFocus();
				return;
			}
		} catch (NumberFormatException ex) {
			Mensajes.mensajeError(this, "El precio debe ser un número válido (ejemplo: 120.50).");
			txtPrecio.requestFocus();
			return;
		}

		Mensajes.mensajeExito(this, "Producto actualizado exitosamente.", "Actualización Exitosa");
		dispose();
	}

	private void cargarDatosDelProducto() {
		for (int i = 0; i < baseDatosMock.length; i++) {
			int idMock = (int) baseDatosMock[i][0];

			if (idMock == this.idProducto) {
				codigoOriginal = baseDatosMock[i][1].toString();
				nombreOriginal = baseDatosMock[i][2].toString();
				stockOriginal = baseDatosMock[i][3].toString();
				precioOriginal = baseDatosMock[i][4].toString();

				setCodigo(codigoOriginal);
				setNombre(nombreOriginal);
				setStock(stockOriginal);
				setPrecio(precioOriginal);
				break;
			}
		}
	}

	private void configurarEscuchadoresDeTexto() {
		DocumentListener dl = new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				evaluarCambios();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				evaluarCambios();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				evaluarCambios();
			}
		};

		txtCodigo.getDocument().addDocumentListener(dl);
		txtNombre.getDocument().addDocumentListener(dl);
		txtStock.getDocument().addDocumentListener(dl);
		txtPrecio.getDocument().addDocumentListener(dl);
	}

	// CORREGIDO: El botón ahora responde puramente a si la información difiere del
	// estado original
	private void evaluarCambios() {
		boolean huboCambios = !getCodigo().equals(codigoOriginal) || !getNombre().equals(nombreOriginal)
				|| !getStock().equals(stockOriginal) || !getPrecio().equals(precioOriginal);

		btnActualizar.setEnabled(huboCambios);
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
}