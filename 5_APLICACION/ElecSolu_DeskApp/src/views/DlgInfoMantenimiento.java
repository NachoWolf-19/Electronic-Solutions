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
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import utils.Mensajes;

public class DlgInfoMantenimiento extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();

	// Campos en columna
	private JTextField txtFechaRegistro;
	private JTextField txtCliente;
	private JTextField txtServicio;
	private JTextField txtEquipo;
	private JTextField txtCategoria;
	private JTextField txtTecnico;
	private JTextField txtFechaEntrega;
	private JTextField txtGarantia;
	private JTextField txtPrecio;
	private JComboBox<String> cboEstado;

	// Campos de texto grandes (JTextArea con Scroll)
	private JTextArea txtDescripcion;
	private JTextArea txtSolucionTecnica;

	private JButton btnGuardar;

	private String rolUsuario;
	private int idOrden;
	private String estadoActual;

	// Variables para respaldar el estado inicial de los campos editables
	private String precioInicial;
	private String descripcionInicial;
	private String solucionInicial;
	private String estadoInicial;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DlgInfoMantenimiento dialog = new DlgInfoMantenimiento(101, "Admin");
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DlgInfoMantenimiento(int idOrdenSimulado, String rol) {
		this.idOrden = idOrdenSimulado;
		this.rolUsuario = rol;

		setTitle("Detalle de Tarea de Mantenimiento N° " + idOrden);
		setModal(true);
		setResizable(false);
		setBounds(100, 100, 720, 480);
		setLocationRelativeTo(null);

		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		// --- COLUMNA 1 ---
		JLabel lblFechaReg = new JLabel("Fecha Registro:");
		lblFechaReg.setBounds(30, 20, 110, 20);
		contentPanel.add(lblFechaReg);

		txtFechaRegistro = new JTextField();
		txtFechaRegistro.setEditable(false);
		txtFechaRegistro.setBounds(150, 20, 160, 22);
		contentPanel.add(txtFechaRegistro);

		JLabel lblCliente = new JLabel("Cliente:");
		lblCliente.setBounds(30, 55, 110, 20);
		contentPanel.add(lblCliente);

		txtCliente = new JTextField();
		txtCliente.setEditable(false);
		txtCliente.setBounds(150, 55, 160, 22);
		contentPanel.add(txtCliente);

		JLabel lblEquipo = new JLabel("Equipo:");
		lblEquipo.setBounds(30, 90, 110, 20);
		contentPanel.add(lblEquipo);

		txtEquipo = new JTextField();
		txtEquipo.setEditable(false);
		txtEquipo.setBounds(150, 90, 160, 22);
		contentPanel.add(txtEquipo);

		JLabel lblCategoria = new JLabel("Categoría:");
		lblCategoria.setBounds(30, 125, 110, 20);
		contentPanel.add(lblCategoria);

		txtCategoria = new JTextField();
		txtCategoria.setEditable(false);
		txtCategoria.setBounds(150, 125, 160, 22);
		contentPanel.add(txtCategoria);

		JLabel lblServicio = new JLabel("Servicio:");
		lblServicio.setBounds(30, 160, 110, 20);
		contentPanel.add(lblServicio);

		txtServicio = new JTextField();
		txtServicio.setEditable(false);
		txtServicio.setBounds(150, 160, 160, 22);
		contentPanel.add(txtServicio);

		// --- COLUMNA 2 ---
		JLabel lblTecnico = new JLabel("Técnico:");
		lblTecnico.setBounds(370, 20, 110, 20);
		contentPanel.add(lblTecnico);

		txtTecnico = new JTextField();
		txtTecnico.setEditable(false);
		txtTecnico.setBounds(490, 20, 180, 22);
		contentPanel.add(txtTecnico);

		JLabel lblFechaEnt = new JLabel("F. Entrega Est.:");
		lblFechaEnt.setBounds(370, 55, 110, 20);
		contentPanel.add(lblFechaEnt);

		txtFechaEntrega = new JTextField();
		txtFechaEntrega.setEditable(false);
		txtFechaEntrega.setBounds(490, 55, 180, 22);
		contentPanel.add(txtFechaEntrega);

		JLabel lblGarantia = new JLabel("Meses Garantía:");
		lblGarantia.setBounds(370, 90, 110, 20);
		contentPanel.add(lblGarantia);

		txtGarantia = new JTextField();
		txtGarantia.setEditable(false);
		txtGarantia.setBounds(490, 90, 180, 22);
		contentPanel.add(txtGarantia);

		JLabel lblPrecio = new JLabel("Precio (S/):");
		lblPrecio.setBounds(370, 125, 110, 20);
		contentPanel.add(lblPrecio);

		txtPrecio = new JTextField();
		txtPrecio.setBounds(490, 125, 180, 22);
		contentPanel.add(txtPrecio);

		JLabel lblEstado = new JLabel("Estado:");
		lblEstado.setBounds(370, 160, 110, 20);
		contentPanel.add(lblEstado);

		cboEstado = new JComboBox<>();
		cboEstado.setBounds(490, 160, 180, 22);
		contentPanel.add(cboEstado);

		// --- SECCIÓN INFERIOR AMPLIA ---

		// 1. DESCRIPCIÓN
		JLabel lblDescripcion = new JLabel("Descripción del Problema:");
		lblDescripcion.setBounds(30, 200, 250, 20);
		contentPanel.add(lblDescripcion);

		txtDescripcion = new JTextArea();
		txtDescripcion.setLineWrap(true);
		txtDescripcion.setWrapStyleWord(true);

		JScrollPane scpDescripcion = new JScrollPane(txtDescripcion);
		scpDescripcion.setBounds(30, 225, 640, 60);
		contentPanel.add(scpDescripcion);

		// 2. SOLUCIÓN TÉCNICA
		JLabel lblSolucion = new JLabel("Solución Técnica Aplicada:");
		lblSolucion.setBounds(30, 295, 250, 20);
		contentPanel.add(lblSolucion);

		txtSolucionTecnica = new JTextArea();
		txtSolucionTecnica.setLineWrap(true);
		txtSolucionTecnica.setWrapStyleWord(true);

		JScrollPane scpSolucion = new JScrollPane(txtSolucionTecnica);
		scpSolucion.setBounds(30, 320, 640, 60);
		contentPanel.add(scpSolucion);

		// Botones inferiores
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				btnGuardar = new JButton("Guardar Cambios");
				btnGuardar.setEnabled(false);
				btnGuardar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						guardarCambios();
					}
				});
				buttonPane.add(btnGuardar);
				getRootPane().setDefaultButton(btnGuardar);
			}
			{
				JButton btnCerrar = new JButton("Cerrar");
				btnCerrar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				buttonPane.add(btnCerrar);
			}
		}

		infoOrden();
		evaluarRol();

		// Almacenar los valores de fábrica exactos con los que abrió la interfaz
		respaldarValoresIniciales();

		// Activar los escuchadores en tiempo real después de cargar la data inicial
		activarListenersMonitoreo();
	}

	private void infoOrden() {
		txtFechaRegistro.setText("28/05/2026 14:30:15");
		txtFechaEntrega.setText("02/06/2026");
		txtGarantia.setText("6");
		txtCliente.setText("Carlos Mendoza");
		txtEquipo.setText("Amplificador Marshall");
		txtCategoria.setText("Audio Profesional");
		txtServicio.setText("Mantenimiento");
		txtTecnico.setText("Jorge Fossati");
		txtPrecio.setText("120.00");

		txtDescripcion.setText(
				"El cliente reporta ruido excesivo en los potenciómetros de graves y medios al girar las perillas. Requiere limpieza interna profunda.");
		txtSolucionTecnica.setText("");

		this.estadoActual = "Registrado";
	}

	private void evaluarRol() {
		txtPrecio.setEditable(false);
		txtDescripcion.setEditable(false);
		txtSolucionTecnica.setEditable(false);
		cboEstado.setEnabled(false);

		cboEstado.addItem(estadoActual);

		switch (rolUsuario) {
		case "Tecnico":
			txtPrecio.setEditable(true);
			txtSolucionTecnica.setEditable(true);

			if (estadoActual.equals("Registrado")) {
				cboEstado.setEnabled(true);
				cboEstado.addItem("En Taller");
			} else if (estadoActual.equals("En Taller")) {
				cboEstado.setEnabled(true);
				cboEstado.addItem("Entregable");
			}
			break;

		case "Recepcionista":
			if (estadoActual.equals("Entregable")) {
				cboEstado.setEnabled(true);
				cboEstado.addItem("Cerrado");
			}
			break;

		case "Admin":
			txtPrecio.setEditable(true);
			txtDescripcion.setEditable(true);
			cboEstado.setEnabled(true);

			cboEstado.removeAllItems();
			cboEstado.addItem("Registrado");
			cboEstado.addItem("En Taller");
			cboEstado.addItem("Entregable");
			cboEstado.addItem("Cerrado");
			cboEstado.setSelectedItem(estadoActual);
			break;
		}
	}

	private void respaldarValoresIniciales() {
		precioInicial = txtPrecio.getText();
		descripcionInicial = txtDescripcion.getText();
		solucionInicial = txtSolucionTecnica.getText();
		estadoInicial = cboEstado.getSelectedItem() != null ? cboEstado.getSelectedItem().toString() : "";
	}

	private void activarListenersMonitoreo() {
		DocumentListener docListener = new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				verificarCambios();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				verificarCambios();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				verificarCambios();
			}
		};

		txtPrecio.getDocument().addDocumentListener(docListener);
		txtDescripcion.getDocument().addDocumentListener(docListener);
		txtSolucionTecnica.getDocument().addDocumentListener(docListener);

		cboEstado.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				verificarCambios();
			}
		});
	}

	private void verificarCambios() {
		String nuevoEstado = cboEstado.getSelectedItem() != null ? cboEstado.getSelectedItem().toString() : "";
		String solucion = txtSolucionTecnica.getText();
		String precio = txtPrecio.getText();
		String descripcion = txtDescripcion.getText();

		boolean hayModificacion = !precio.equals(precioInicial) || !descripcion.equals(descripcionInicial)
				|| !solucion.equals(solucionInicial) || !nuevoEstado.equals(estadoInicial);

		btnGuardar.setEnabled(hayModificacion);
	}

	private void guardarCambios() {
		String nuevoEstado = cboEstado.getSelectedItem() != null ? cboEstado.getSelectedItem().toString() : "";
		String solucion = txtSolucionTecnica.getText();
		String precioStr = txtPrecio.getText().trim();

		// CORREGIDO: Validación numérica y de signo para el precio
		try {
			double precio = Double.parseDouble(precioStr);
			if (precio < 0) {
				Mensajes.mensajeError(this, "El precio no puede ser un valor negativo.");
				txtPrecio.requestFocus();
				return;
			}
		} catch (NumberFormatException e) {
			Mensajes.mensajeError(this, "El precio debe ser un valor numerico.");
			txtPrecio.requestFocus();
			return;
		}

		// Validaciones específicas de reglas de negocio existentes
		if (rolUsuario.equals("Tecnico") && nuevoEstado.equals("Entregable") && solucion.trim().isEmpty()) {
			Mensajes.mensajeError(this, "Debe registrar la solución técnica para marcar como Entregable.");
			txtSolucionTecnica.requestFocus();
			return;
		}

		Mensajes.mensajeExito(this, "Cambios guardados con éxito. Estado: " + nuevoEstado, "");
		dispose();
	}
}