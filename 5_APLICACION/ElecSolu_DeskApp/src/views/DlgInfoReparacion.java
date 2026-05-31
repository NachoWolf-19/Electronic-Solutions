package views;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import utils.Mensajes;

public class DlgInfoReparacion extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();

	// Campos superiores de la Orden
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

	// Componentes de las Tablas de Repuestos
	private JLabel lblDisponibles;
	private JScrollPane scpDisponibles;
	private JTable tblDisponibles;
	private DefaultTableModel modelDisponibles;

	private JButton btnAgregar;
	private JButton btnQuitar;

	private JLabel lblAnadidos;
	private JScrollPane scpAnadidos;
	private JTable tblAnadidos;
	private DefaultTableModel modelAnadidos;

	// Campo de cálculo final
	private JTextField txtTotalGeneral;
	private JButton btnGuardar;

	private String rolUsuario;
	private int idOrden;
	private String estadoActual;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DlgInfoReparacion dialog = new DlgInfoReparacion(202, "Tecnico");
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DlgInfoReparacion(int idOrdenSimulado, String rol) {
		this.idOrden = idOrdenSimulado;
		this.rolUsuario = rol;

		setTitle("Detalle de Tarea de Reparación N° " + idOrden);
		setModal(true);
		setResizable(false);
		setBounds(100, 100, 720, 540);
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

		JLabel lblPrecio = new JLabel("Precio Servicio:");
		lblPrecio.setBounds(370, 125, 110, 20);
		contentPanel.add(lblPrecio);

		txtPrecio = new JTextField();
		txtPrecio.setBounds(490, 125, 180, 22);
		// Listener para actualizar el Total General inmediatamente al escribir el
		// precio
		txtPrecio.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				calcularTotalGeneral();
			}
		});
		contentPanel.add(txtPrecio);

		JLabel lblEstado = new JLabel("Estado:");
		lblEstado.setBounds(370, 160, 110, 20);
		contentPanel.add(lblEstado);

		cboEstado = new JComboBox<>();
		cboEstado.setBounds(490, 160, 180, 22);
		contentPanel.add(cboEstado);

		// --- SECCIÓN INFERIOR: LOGÍSTICA DE REPUESTOS Y COSTOS ---

		// 1. Tabla Izquierda: Disponibles (Se añade columna P. Unitario)
		lblDisponibles = new JLabel("Componentes / Repuestos Disponibles:");
		lblDisponibles.setBounds(30, 205, 250, 20);
		contentPanel.add(lblDisponibles);

		modelDisponibles = new DefaultTableModel(new Object[][] {}, new String[] { "Componente", "Stock", "P. Unit" }) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		tblDisponibles = new JTable(modelDisponibles);
		tblDisponibles.getTableHeader().setResizingAllowed(false);
		tblDisponibles.getTableHeader().setReorderingAllowed(false);
		tblDisponibles.getColumnModel().getColumn(0).setPreferredWidth(130);
		tblDisponibles.getColumnModel().getColumn(1).setPreferredWidth(50);
		tblDisponibles.getColumnModel().getColumn(2).setPreferredWidth(70);

		scpDisponibles = new JScrollPane(tblDisponibles);
		scpDisponibles.setBounds(30, 230, 250, 180);
		contentPanel.add(scpDisponibles);

		// 2. Botones de Acción
		btnAgregar = new JButton("Añadir >>");
		btnAgregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				añadirComponente();
			}
		});
		btnAgregar.setBounds(295, 275, 115, 25);
		contentPanel.add(btnAgregar);

		btnQuitar = new JButton("<< Quitar");
		btnQuitar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				quitarComponente();
			}
		});
		btnQuitar.setBounds(295, 315, 115, 25);
		contentPanel.add(btnQuitar);

		// 3. Tabla Derecha: Asignados (Se añaden columnas P. Unit e Importe)
		lblAnadidos = new JLabel("Componentes Asignados a esta Tarea:");
		lblAnadidos.setBounds(420, 205, 250, 20);
		contentPanel.add(lblAnadidos);

		modelAnadidos = new DefaultTableModel(new Object[][] {},
				new String[] { "Componente", "Cant", "P. Unit", "Importe" }) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		tblAnadidos = new JTable(modelAnadidos);
		tblAnadidos.getTableHeader().setResizingAllowed(false);
		tblAnadidos.getTableHeader().setReorderingAllowed(false);
		tblAnadidos.getColumnModel().getColumn(0).setPreferredWidth(100);
		tblAnadidos.getColumnModel().getColumn(1).setPreferredWidth(40);
		tblAnadidos.getColumnModel().getColumn(2).setPreferredWidth(55);
		tblAnadidos.getColumnModel().getColumn(3).setPreferredWidth(55);

		scpAnadidos = new JScrollPane(tblAnadidos);
		scpAnadidos.setBounds(420, 230, 250, 180);
		contentPanel.add(scpAnadidos);

		// --- FILA DE TOTAL GENERAL ---
		JLabel lblTotal = new JLabel("TOTAL GENERAL (S/):");
		lblTotal.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblTotal.setBounds(370, 425, 120, 20);
		contentPanel.add(lblTotal);

		txtTotalGeneral = new JTextField();
		txtTotalGeneral.setFont(new Font("Tahoma", Font.BOLD, 12));
		txtTotalGeneral.setHorizontalAlignment(SwingConstants.RIGHT);
		txtTotalGeneral.setEditable(false);
		txtTotalGeneral.setBounds(490, 425, 180, 22);
		contentPanel.add(txtTotalGeneral);

		// Botones inferiores estándar
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				btnGuardar = new JButton("Guardar Cambios");
				btnGuardar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						guardarDatos();
					}
				});
				buttonPane.add(btnGuardar);
				getRootPane().setDefaultButton(btnGuardar);
			}
			{
				JButton cancelButton = new JButton("Cerrar");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				buttonPane.add(cancelButton);
			}
		}

		cargarDatosOrdenMock();
		cargarComponentesInventarioMock();
		evaluarPermisosYEstado();
		calcularTotalGeneral(); // Cálculo inicial de apertura
	}

	private void cargarDatosOrdenMock() {
		txtFechaRegistro.setText("29/05/2026 10:15:00");
		txtFechaEntrega.setText("05/06/2026");
		txtGarantia.setText("12");

		txtCliente.setText("Marina Silva");
		txtEquipo.setText("Sintetizador Korg");
		txtCategoria.setText("Teclados");
		txtServicio.setText("Reparación");
		txtTecnico.setText("Jorge Fossati");
		txtPrecio.setText("350.00");

		this.estadoActual = "En Taller";
	}

	private void cargarComponentesInventarioMock() {
		// Formato de carga: Componente, Stock, Precio Unitario
		modelDisponibles.addRow(new Object[] { "Potenciómetro B10K", 15, 15.00 });
		modelDisponibles.addRow(new Object[] { "Fusible de Protección 2A", 10, 5.00 });
		modelDisponibles.addRow(new Object[] { "Transformador de Línea 12V", 5, 45.00 });
		modelDisponibles.addRow(new Object[] { "Circuito Integrado TL072", 30, 8.50 });
		modelDisponibles.addRow(new Object[] { "Condensador Electrolítico 4700uF", 12, 12.00 });

		// Formato de asignados previos: Componente, Cantidad, Precio Unitario, Importe
		modelAnadidos.addRow(new Object[] { "Fusible de Protección 2A", 2, 5.00, 10.00 });
	}

	private void evaluarPermisosYEstado() {
		txtPrecio.setEditable(false);
		cboEstado.setEnabled(false);
		btnGuardar.setEnabled(false);
		cboEstado.addItem(estadoActual);

		boolean esTecnico = rolUsuario.equals("Tecnico");
		boolean estadoPermitido = !estadoActual.equals("Entregable") && !estadoActual.equals("Cerrado");
		boolean puedeModificarComponentes = esTecnico && estadoPermitido;

		if (puedeModificarComponentes) {
			lblDisponibles.setVisible(true);
			scpDisponibles.setVisible(true);
			btnAgregar.setVisible(true);
			btnQuitar.setVisible(true);

			lblAnadidos.setBounds(420, 205, 250, 20);
			scpAnadidos.setBounds(420, 230, 250, 180);
		} else {
			lblDisponibles.setVisible(false);
			scpDisponibles.setVisible(false);
			btnAgregar.setVisible(false);
			btnQuitar.setVisible(false);

			lblAnadidos.setBounds(30, 205, 640, 20);
			scpAnadidos.setBounds(30, 230, 640, 180);
		}

		switch (rolUsuario) {
		case "Tecnico":
			btnGuardar.setEnabled(true);
			txtPrecio.setEditable(true);
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
			cboEstado.setEnabled(true);
			cboEstado.removeAllItems();
			cboEstado.addItem("Registrado");
			cboEstado.addItem("En Taller");
			cboEstado.addItem("Entregable");
			cboEstado.addItem("Cerrado");
			cboEstado.setSelectedItem(estadoActual);
			break;

		default:
			break;
		}
	}

	// --- LÓGICA MATEMÁTICA Y DE CALCULO ---

	private void calcularTotalGeneral() {
		try {
			double precioServicio = 0.0;
			String strPrecio = txtPrecio.getText().trim();

			if (!strPrecio.isEmpty()) {
				precioServicio = Double.parseDouble(strPrecio);
			}

			double totalRepuestos = 0.0;
			for (int i = 0; i < modelAnadidos.getRowCount(); i++) {
				totalRepuestos += Double.parseDouble(modelAnadidos.getValueAt(i, 3).toString());
			}

			double totalGeneral = precioServicio + totalRepuestos;
			txtTotalGeneral.setText(String.format("%.2f", totalGeneral));

		} catch (NumberFormatException e) {
			txtTotalGeneral.setText("Precio inválido");
		}
	}

	private void añadirComponente() {
		int filaSeleccionada = tblDisponibles.getSelectedRow();

		if (filaSeleccionada == -1) {
			Mensajes.mensajeError(this, "Seleccione un componente de la tabla izquierda.");
			return;
		}

		String componente = tblDisponibles.getValueAt(filaSeleccionada, 0).toString();
		int stockDisponible = Integer.parseInt(tblDisponibles.getValueAt(filaSeleccionada, 1).toString());
		double precioUnitario = Double.parseDouble(tblDisponibles.getValueAt(filaSeleccionada, 2).toString());

		if (stockDisponible <= 0) {
			Mensajes.mensajeError(this, "No quedan existencias disponibles de este repuesto.");
			return;
		}

		String cantStr = JOptionPane.showInputDialog(this,
				"Ingrese la cantidad a utilizar para:\n" + componente + "\n(Disponibles: " + stockDisponible + ")",
				"Cantidad de Repuesto", JOptionPane.QUESTION_MESSAGE);

		if (cantStr == null)
			return;

		try {
			int cantidad = Integer.parseInt(cantStr.trim());
			if (cantidad <= 0) {
				Mensajes.mensajeError(this, "La cantidad debe ser mayor a cero.");
				return;
			}
			if (cantidad > stockDisponible) {
				Mensajes.mensajeError(this, "La cantidad ingresada supera el stock disponible actual.");
				return;
			}

			// Restar del stock de existencias (columna index 1)
			tblDisponibles.setValueAt(stockDisponible - cantidad, filaSeleccionada, 1);

			// Buscar si ya existe en la lista de añadidos para acumular cantidades e
			// importes
			boolean existeEnAsignados = false;
			for (int i = 0; i < modelAnadidos.getRowCount(); i++) {
				if (modelAnadidos.getValueAt(i, 0).toString().equals(componente)) {
					int cantActual = Integer.parseInt(modelAnadidos.getValueAt(i, 1).toString());
					int nuevaCantidad = cantActual + cantidad;
					double nuevoImporte = nuevaCantidad * precioUnitario;

					modelAnadidos.setValueAt(nuevaCantidad, i, 1);
					modelAnadidos.setValueAt(nuevoImporte, i, 3);
					existeEnAsignados = true;
					break;
				}
			}

			if (!existeEnAsignados) {
				double importe = cantidad * precioUnitario;
				modelAnadidos.addRow(new Object[] { componente, cantidad, precioUnitario, importe });
			}

			calcularTotalGeneral(); // Refrescar el cálculo completo

		} catch (NumberFormatException e) {
			Mensajes.mensajeError(this, "Por favor, ingrese un número entero válido.");
		}
	}

	private void quitarComponente() {
		int filaSeleccionada = tblAnadidos.getSelectedRow();

		if (filaSeleccionada == -1) {
			Mensajes.mensajeError(this, "Seleccione un componente de la tabla derecha para removerlo.");
			return;
		}

		String componente = tblAnadidos.getValueAt(filaSeleccionada, 0).toString();
		int cantidadAsignada = Integer.parseInt(tblAnadidos.getValueAt(filaSeleccionada, 1).toString());

		// Devolver las existencias al stock de la tabla izquierda
		for (int i = 0; i < modelDisponibles.getRowCount(); i++) {
			if (modelDisponibles.getValueAt(i, 0).toString().equals(componente)) {
				int stockActual = Integer.parseInt(modelDisponibles.getValueAt(i, 1).toString());
				modelDisponibles.setValueAt(stockActual + cantidadAsignada, i, 1);
				break;
			}
		}

		// Remover el registro y recalcular
		modelAnadidos.removeRow(filaSeleccionada);
		calcularTotalGeneral();
	}

	private void guardarDatos() {
		String nuevoEstado = cboEstado.getSelectedItem().toString();
		Mensajes.mensajeExito(this, "Repuestos y costos actualizados con éxito. Total: S/ " + txtTotalGeneral.getText(),
				"");
		dispose();
	}
}