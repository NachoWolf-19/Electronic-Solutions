package views;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import utils.AppUtils;
import utils.Mensajes;

public class DlgNuevaOrden extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();

	private JTextField txtDni;
	private JComboBox<String> cboServicio;
	private JTextField txtEquipo;
	private JComboBox<String> cboCategoria;
	private JTextArea txtDescripcion;

	private JTextField txtPrecio;
	private JTextField txtGarantia;
	private JTextField txtFechaEstimada; // Sigue siendo un JTextField para la captura de texto

	private JButton btnProcesar;
	private JButton btnCancelar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DlgNuevaOrden dialog = new DlgNuevaOrden();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DlgNuevaOrden() {
		setTitle("Registrar Nueva Orden");
		setModal(true);
		setResizable(false);
		setBounds(100, 100, 460, 460);
		setLocationRelativeTo(null);

		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		// --- COMPONENTES DEL FORMULARIO ---

		JLabel lblDni = new JLabel("DNI Cliente:");
		lblDni.setBounds(30, 20, 100, 20);
		contentPanel.add(lblDni);

		txtDni = new JTextField();
		txtDni.setBounds(140, 20, 150, 22);
		contentPanel.add(txtDni);

		JLabel lblServicio = new JLabel("Servicio:");
		lblServicio.setBounds(30, 55, 100, 20);
		contentPanel.add(lblServicio);

		cboServicio = new JComboBox<>();
		cboServicio.setBounds(140, 55, 260, 22);
		cboServicio.addItem("--- Seleccione ---");
		cboServicio.addItem("Mantenimiento");
		cboServicio.addItem("Reparación");
		contentPanel.add(cboServicio);

		JLabel lblEquipo = new JLabel("Equipo");
		lblEquipo.setBounds(30, 90, 100, 20);
		contentPanel.add(lblEquipo);

		txtEquipo = new JTextField();
		txtEquipo.setBounds(140, 90, 260, 22);
		contentPanel.add(txtEquipo);

		JLabel lblCategoria = new JLabel("Categoría:");
		lblCategoria.setBounds(30, 125, 100, 20);
		contentPanel.add(lblCategoria);

		cboCategoria = new JComboBox<>();
		cboCategoria.setBounds(140, 125, 260, 22);
		cboCategoria.addItem("--- Seleccione ---");
		cboCategoria.addItem("Teclados / Sintetizadores");
		cboCategoria.addItem("Audio Profesional / Consolas");
		cboCategoria.addItem("Guitarras / Bajos Electrónicos");
		cboCategoria.addItem("Cómputo / Laptops");
		contentPanel.add(cboCategoria);

		JLabel lblDescripcion = new JLabel("Descripción:");
		lblDescripcion.setBounds(30, 160, 100, 20);
		contentPanel.add(lblDescripcion);

		txtDescripcion = new JTextArea();
		txtDescripcion.setLineWrap(true);
		txtDescripcion.setWrapStyleWord(true);

		JScrollPane scpDescripcion = new JScrollPane(txtDescripcion);
		scpDescripcion.setBounds(140, 160, 260, 80);
		contentPanel.add(scpDescripcion);

		// --- COMPONENTES AGREGADOS ---

		JLabel lblPrecio = new JLabel("Precio (S/):");
		lblPrecio.setBounds(30, 255, 100, 20);
		contentPanel.add(lblPrecio);

		txtPrecio = new JTextField();
		txtPrecio.setBounds(140, 255, 150, 22);
		contentPanel.add(txtPrecio);

		JLabel lblGarantia = new JLabel("Garantía (meses):");
		lblGarantia.setBounds(30, 290, 110, 20);
		contentPanel.add(lblGarantia);

		txtGarantia = new JTextField();
		txtGarantia.setBounds(140, 290, 150, 22);
		contentPanel.add(txtGarantia);

		JLabel lblFecha = new JLabel("Fec. Estimada:");
		lblFecha.setBounds(30, 325, 110, 20);
		contentPanel.add(lblFecha);

		txtFechaEstimada = new JTextField();
		txtFechaEstimada.setToolTipText("Formato: DD/MM/AAAA");
		txtFechaEstimada.setBounds(140, 325, 150, 22);
		contentPanel.add(txtFechaEstimada);

		// --- BOTONES DE ACCIÓN INFERIORES ---
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				btnProcesar = new JButton("Procesar");
				btnProcesar.addActionListener(this);
				buttonPane.add(btnProcesar);
				getRootPane().setDefaultButton(btnProcesar);
			}
			{
				btnCancelar = new JButton("Cancelar");
				btnCancelar.addActionListener(this);
				buttonPane.add(btnCancelar);
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnProcesar) {
			actionPerformedBtnProcesar(e);
		}
		if (e.getSource() == btnCancelar) {
			actionPerformedBtnCancelar(e);
		}
	}

	private void actionPerformedBtnProcesar(ActionEvent e) {
		String dni = txtDni.getText().trim();
		String servicio = cboServicio.getSelectedItem().toString();
		String equipo = txtEquipo.getText().trim();
		String categoria = cboCategoria.getSelectedItem().toString();
		String descripcion = txtDescripcion.getText().trim();
		String precioStr = txtPrecio.getText().trim();
		String garantiaStr = txtGarantia.getText().trim();
		String fechaEstimadaStr = txtFechaEstimada.getText().trim();

		// 1. Validaciones de Campos Vacíos
		if (dni.isEmpty()) {
			Mensajes.mensajeError(this, "El campo DNI no puede estar vacío.");
			txtDni.requestFocus();
			return;
		}
		if (cboServicio.getSelectedIndex() == 0) {
			Mensajes.mensajeError(this, "Debe seleccionar un tipo de servicio válido.");
			cboServicio.requestFocus();
			return;
		}
		if (equipo.isEmpty()) {
			Mensajes.mensajeError(this, "El campo Equipo no puede estar vacío.");
			txtEquipo.requestFocus();
			return;
		}
		if (cboCategoria.getSelectedIndex() == 0) {
			Mensajes.mensajeError(this, "Debe seleccionar una categoría válida.");
			cboCategoria.requestFocus();
			return;
		}
		if (descripcion.isEmpty()) {
			Mensajes.mensajeError(this, "Debe ingresar una descripción del estado o falla del equipo.");
			txtDescripcion.requestFocus();
			return;
		}
		if (precioStr.isEmpty()) {
			Mensajes.mensajeError(this, "El campo Precio no puede estar vacío.");
			txtPrecio.requestFocus();
			return;
		}
		if (garantiaStr.isEmpty()) {
			Mensajes.mensajeError(this, "El campo Garantía no puede estar vacío.");
			txtGarantia.requestFocus();
			return;
		}
		if (fechaEstimadaStr.isEmpty()) {
			Mensajes.mensajeError(this, "Debe ingresar la fecha estimada de entrega.");
			txtFechaEstimada.requestFocus();
			return;
		}

		// 2. Validación de Formatos Numéricos
		if (!dni.matches("\\d{8}")) {
			Mensajes.mensajeError(this, "El DNI debe contener exactamente 8 caracteres numéricos.");
			txtDni.requestFocus();
			return;
		}

		if (!precioStr.matches("\\d+(\\.\\d+)?")) {
			Mensajes.mensajeError(this, "El precio debe ser un número válido (ejemplo: 150 o 89.50).");
			txtPrecio.requestFocus();
			return;
		}

		if (!garantiaStr.matches("\\d+")) {
			Mensajes.mensajeError(this, "Los meses de garantía deben ser un valor numérico entero.");
			txtGarantia.requestFocus();
			return;
		}

		// --- NUEVA VALIDACIÓN Y PARSEO DE FECHA ---
		DateTimeFormatter formateador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate fechaEstimada = null;

		try {
			// Intenta transformar el texto a una fecha real
			fechaEstimada = LocalDate.parse(fechaEstimadaStr, formateador);

			// Regla de negocio: La fecha de entrega no puede ser del pasado
			if (fechaEstimada.isBefore(LocalDate.now())) {
				Mensajes.mensajeError(this, "La fecha estimada no puede ser anterior al día de hoy.");
				txtFechaEstimada.requestFocus();
				return;
			}
		} catch (DateTimeParseException ex) {
			Mensajes.mensajeError(this, "Formato de fecha inválido. Use el formato DD/MM/AAAA (Ej: 24/07/2026).");
			txtFechaEstimada.requestFocus();
			return;
		}

		// 3. Simulación de búsqueda en base de datos
		if (!simularBuscarClientePorDni(dni)) {
			Mensajes.mensajeError(this, "El DNI ingresado no se encuentra registrado en el sistema.");
			txtDni.requestFocus();
			return;
		}

		if (!Mensajes.mensajeConfirmar(this,
				"¿Desea procesar esta orden de servicio? Verifique correctamente los datos ingresados.")) {
			return;
		}

		// 4. Apertura del diálogo de vista previa (DlgBoleta)
		dispose();

		double precio = Double.parseDouble(precioStr);
		int garantia = Integer.parseInt(garantiaStr);

		// Ahora enviamos el objeto 'fechaEstimada' de tipo LocalDate
		DlgBoleta dlgBoleta = new DlgBoleta(dni, servicio, equipo, categoria, descripcion, precio, garantia,
				fechaEstimada);
		AppUtils.abrirDialogo(null, dlgBoleta);
	}

	private void actionPerformedBtnCancelar(ActionEvent e) {
		dispose();
	}

	private boolean simularBuscarClientePorDni(String dni) {
		return dni.equals("12345678") || dni.equals("87654321");
	}
}