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

public class DlgMantenInventario extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private final JPanel pnlContent = new JPanel();

	private JTextField txtCodigo;
	private JTextField txtNombre;
	private JTextField txtStock;
	private JTextField txtPrecio;
	private JComboBox<String> cboOperacion;
	private JButton btnProcesar;
	private JButton btnCerrar;
	private JButton btnBuscar;

	private JLabel lblOperacion;
	private JLabel lblCodigo;
	private JLabel lblNombre;
	private JLabel lblStock;
	private JLabel lblPrecio;

	public static void main(String[] args) {
		try {
			DlgMantenInventario dialog = new DlgMantenInventario();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DlgMantenInventario() {
		setResizable(false);
		setTitle("Mantenimiento de Inventario");
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

		lblCodigo = new JLabel("Código:");
		lblCodigo.setBounds(20, 55, 90, 20);
		pnlContent.add(lblCodigo);

		txtCodigo = new JTextField();
		txtCodigo.setBounds(120, 55, 190, 20);
		pnlContent.add(txtCodigo);
		txtCodigo.setColumns(10);

		lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(20, 90, 90, 20);
		pnlContent.add(lblNombre);

		txtNombre = new JTextField();
		txtNombre.setBounds(120, 90, 190, 20);
		pnlContent.add(txtNombre);
		txtNombre.setColumns(10);

		lblStock = new JLabel("Stock:");
		lblStock.setBounds(20, 125, 90, 20);
		pnlContent.add(lblStock);

		txtStock = new JTextField();
		txtStock.setBounds(120, 125, 190, 20);
		pnlContent.add(txtStock);
		txtStock.setColumns(10);

		lblPrecio = new JLabel("Precio:");
		lblPrecio.setBounds(20, 160, 90, 20);
		pnlContent.add(lblPrecio);

		txtPrecio = new JTextField();
		txtPrecio.setBounds(120, 160, 190, 20);
		pnlContent.add(txtPrecio);
		txtPrecio.setColumns(10);

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
		String codigo = getCodigo();

		if (codigo.isEmpty()) {
			Mensajes.mensajeError(this, "Debe ingresar un código para buscar.");
			return;
		}

		if (codigo.length() != 6) {
			Mensajes.mensajeError(this, "El código debe tener exactamente 6 caracteres.");
			txtCodigo.requestFocus();
			return;
		}

		if (buscarInventario()) {
			btnProcesar.setEnabled(true);
		} else {
			Mensajes.mensajeError(this, "No se encontró el producto con código: " + codigo);
			btnProcesar.setEnabled(false);
			txtCodigo.requestFocus();
			txtCodigo.setText("");
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

		// Configurar estados de los campos
		txtNombre.setEnabled(esRegistro);
		txtStock.setEnabled(esRegistro);
		txtPrecio.setEnabled(esRegistro);
		txtCodigo.setEnabled(!esRegistro); // Desactivar código si es registro

		// Limpiar y resetear estados al cambiar de operación
		if (esRegistro) {
			setCodigo("");
			btnProcesar.setEnabled(true); // Siempre habilitado para registrar
		} else {
			btnProcesar.setEnabled(false); // Obligatorio buscar primero
		}

		btnBuscar.setEnabled(!esRegistro);
	}

	private void registrarInventario() {
		if (getNombre().isEmpty()) {
			Mensajes.mensajeError(this, "El nombre del producto es obligatorio.");
			txtNombre.requestFocus();
			return;
		}

		try {
			int stock = Integer.parseInt(getStock());
			if (stock < 0) {
				Mensajes.mensajeError(this, "El stock no puede ser negativo.");
				setStock("");
				txtStock.requestFocus();
				return;
			}
		} catch (NumberFormatException e) {
			Mensajes.mensajeError(this, "El stock debe ser un número entero.");
			return;
		}

		try {
			double precio = Double.parseDouble(getPrecio());
			if (precio < 0) {
				Mensajes.mensajeError(this, "El precio no puede ser negativo.");
				setPrecio("");
				txtPrecio.requestFocus();
				return;
			}
		} catch (NumberFormatException e) {
			Mensajes.mensajeError(this, "El precio debe ser un número válido.");
			return;
		}

		Mensajes.mensajeExito(this, "Producto registrado exitosamente.", "Registro Exitoso");
	}

	private void actualizarInventario() {
	}

	private void eliminarInventario() {
	}

	private boolean buscarInventario() {
		return false;
	}

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
		return cboOperacion.getSelectedIndex();
	}
}