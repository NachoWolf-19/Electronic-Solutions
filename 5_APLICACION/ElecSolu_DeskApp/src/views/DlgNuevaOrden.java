package views;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
		setTitle("Registro de Nueva Orden de Servicio");
		setModal(true);
		setResizable(false);
		setBounds(100, 100, 460, 360);
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
		cboServicio.addItem("Reparación");
		cboServicio.addItem("Mantenimiento");
		contentPanel.add(cboServicio);

		JLabel lblEquipo = new JLabel("Equipo / Instrumento:");
		lblEquipo.setBounds(30, 90, 130, 20);
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

		// 1. Validaciones de Campos Vacíos o no seleccionados
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

		// 2. Validación estricta de Formato Numérico del DNI (Exactamente 8 dígitos
		// numéricos)
		if (!dni.matches("\\d{8}")) {
			Mensajes.mensajeError(this, "El DNI debe contener exactamente 8 caracteres numéricos.");
			txtDni.requestFocus();
			return;
		}

		// 3. Simulación de búsqueda en base de datos / archivo de clientes existentes
		if (!simularBuscarClientePorDni(dni)) {
			Mensajes.mensajeError(this, "El DNI ingresado no se encuentra registrado en el sistema.");
			txtDni.requestFocus();
			return;
		}

		if (!Mensajes.mensajeConfirmar(this,
				"¿Desea procesar esta orden de servicio? Verifique correctamente los datos ingresados si no esta seguro.")) {
			return;
		}
		// 4. Apertura del diálogo de vista previa y persistencia de datos (DlgBoleta)
		dispose(); // Cierra el formulario actual de captura

		// Instanciación limpia pasando los parámetros recolectados
		DlgBoleta dlgBoleta = new DlgBoleta(dni, servicio, equipo, categoria, descripcion);
		AppUtils.abrirDialogo(null, dlgBoleta);
	}

	private void actionPerformedBtnCancelar(ActionEvent e) {
		dispose();
	}

	/**
	 * Simulación estricta de validación de identidad del cliente por DNI.
	 * Reemplazar por consultas DAO directas a MySQL en producción.
	 */
	private boolean simularBuscarClientePorDni(String dni) {
		// DNI de prueba válido simulado para desarrollo: "12345678" o "87654321"
		return dni.equals("12345678") || dni.equals("87654321");
	}
}