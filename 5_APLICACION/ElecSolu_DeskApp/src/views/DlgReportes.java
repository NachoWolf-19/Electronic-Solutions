package views;

import java.awt.BorderLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import utils.Mensajes;

public class DlgReportes extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private final JPanel pnlContent = new JPanel();

	private JComboBox<String> cboTipoReporte;
	private JComboBox<String> cboMes;
	private JComboBox<String> cboAnio;
	private JTextArea txtReporte;
	private JButton btnGuardar;
	private JButton btnCerrar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DlgReportes dialog = new DlgReportes(0);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DlgReportes(int tipo) {
		setTitle("Reportes del Sistema");
		setResizable(false);
		setBounds(100, 100, 600, 430);
		getContentPane().setLayout(new BorderLayout());
		pnlContent.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(pnlContent, BorderLayout.CENTER);
		pnlContent.setLayout(null);

		// Selector de Tipo de Reporte
		JLabel lblReporte = new JLabel("Reporte:");
		lblReporte.setBounds(10, 14, 55, 20);
		pnlContent.add(lblReporte);

		// CORREGIDO: Items idénticos a los del menú de FrmMain
		cboTipoReporte = new JComboBox<>();
		cboTipoReporte.addItem("Ingresos por Servicio");
		cboTipoReporte.addItem("Productividad de Técnicos");
		cboTipoReporte.addItem("Repuestos Utilizados");
		cboTipoReporte.setBounds(65, 14, 190, 20);
		pnlContent.add(cboTipoReporte);

		// Selector de Mes
		JLabel lblMes = new JLabel("Mes:");
		lblMes.setBounds(265, 14, 30, 20);
		pnlContent.add(lblMes);

		cboMes = new JComboBox<>(new String[] { "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio",
				"Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre" });
		cboMes.setBounds(300, 14, 95, 20);
		pnlContent.add(cboMes);

		// Selector de Año
		JLabel lblAnio = new JLabel("Año:");
		lblAnio.setBounds(405, 14, 30, 20);
		pnlContent.add(lblAnio);

		cboAnio = new JComboBox<>(new String[] { "2024", "2025", "2026", "2027" });
		cboAnio.setBounds(440, 14, 65, 20);
		pnlContent.add(cboAnio);

		// Área de Texto con Scroll
		JScrollPane scp = new JScrollPane();
		scp.setBounds(10, 45, 444, 335);
		pnlContent.add(scp);

		txtReporte = new JTextArea();
		txtReporte.setEditable(false);
		txtReporte.setMargin(new Insets(8, 8, 8, 8));
		scp.setViewportView(txtReporte);

		// Botón Guardar Archivo
		btnGuardar = new JButton("Guardar");
		btnGuardar.addActionListener(this);
		btnGuardar.setBounds(464, 45, 110, 23);
		pnlContent.add(btnGuardar);

		// Botón Cerrar
		btnCerrar = new JButton("Cerrar");
		btnCerrar.addActionListener(this);
		btnCerrar.setBounds(464, 78, 110, 23);
		pnlContent.add(btnCerrar);

		// Seleccionar mes y año actual de la PC por defecto
		Calendar cal = Calendar.getInstance();
		int mesActual = cal.get(Calendar.MONTH);
		int anioActual = cal.get(Calendar.YEAR);

		cboMes.setSelectedIndex(mesActual);
		cboAnio.setSelectedItem(String.valueOf(anioActual));

		// Aplica el tipo inicial recibido desde FrmMain
		if (tipo >= 0 && tipo < cboTipoReporte.getItemCount()) {
			cboTipoReporte.setSelectedIndex(tipo);
		}

		// Listeners al final para evitar activaciones dobles durante la carga
		cboTipoReporte.addActionListener(this);
		cboMes.addActionListener(this);
		cboAnio.addActionListener(this);

		procesarSeleccionReporte();
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == cboTipoReporte || e.getSource() == cboMes || e.getSource() == cboAnio) {
			procesarSeleccionReporte();
		}
		if (e.getSource() == btnGuardar) {
			actionPerformedBtnGuardar(e);
		}
		if (e.getSource() == btnCerrar) {
			actionPerformedBtnCerrar(e);
		}
	}

	private void procesarSeleccionReporte() {
		int index = cboTipoReporte.getSelectedIndex();
		String mes = cboMes.getSelectedItem().toString();
		String anio = cboAnio.getSelectedItem().toString();

		// Mapeo exacto según el índice enviado de FrmMain
		switch (index) {
		case 0:
			reporteIngresosServicio(mes, anio);
			break;
		case 1:
			reporteProductividadTecnicos(mes, anio);
			break;
		case 2:
			reporteRepuestosUtilizados(mes, anio);
			break;
		}
	}

	// CORREGIDO: Métodos renombrados y formateados con la información correcta del
	// FrmMain

	private void reporteIngresosServicio(String mes, String anio) {
		txtReporte.setText("=========================================\n" + "         INGRESOS POR SERVICIO           \n"
				+ "=========================================\n" + "Periodo: " + mes + " " + anio + "\n\n"
				+ "Simulación de datos cargados de la BD...\n" + "Total ingresos mensuales: S/. 0.00\n"
				+ "Cantidad de transacciones: 0");
	}

	private void reporteProductividadTecnicos(String mes, String anio) {
		txtReporte.setText("=========================================\n" + "       PRODUCTIVIDAD DE TÉCNICOS         \n"
				+ "=========================================\n" + "Periodo: " + mes + " " + anio + "\n\n"
				+ "Simulación de datos cargados de la BD...\n" + "Órdenes atendidas por el personal técnico asignado.");
	}

	private void reporteRepuestosUtilizados(String mes, String anio) {
		txtReporte.setText("=========================================\n" + "         REPUESTOS UTILIZADOS            \n"
				+ "=========================================\n" + "Periodo: " + mes + " " + anio + "\n\n"
				+ "Simulación de datos cargados de la BD...\n"
				+ "No se encontraron repuestos registrados en este periodo.");
	}

	protected void actionPerformedBtnGuardar(ActionEvent e) {
		if (txtReporte.getText().trim().isEmpty()) {
			Mensajes.mensajeError(this, "No hay datos en el reporte para exportar.");
			return;
		}

		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Guardar Reporte como Archivo de Texto");

		String nombreSugerido = cboTipoReporte.getSelectedItem().toString().replace(" ", "_") + "_"
				+ cboMes.getSelectedItem().toString() + "_" + cboAnio.getSelectedItem().toString() + ".txt";
		fileChooser.setSelectedFile(new File(nombreSugerido));

		int seleccion = fileChooser.showSaveDialog(this);

		if (seleccion == JFileChooser.APPROVE_OPTION) {
			File archivo = fileChooser.getSelectedFile();

			try (FileWriter fw = new FileWriter(archivo)) {
				fw.write(txtReporte.getText());
				Mensajes.mensajeExito(this, "El reporte se guardó correctamente en:\n" + archivo.getAbsolutePath(),
						"Éxito");
			} catch (Exception ex) {
				Mensajes.mensajeError(this, "Ocurrió un error al intentar guardar el archivo.");
				ex.printStackTrace();
			}
		}
	}

	protected void actionPerformedBtnCerrar(ActionEvent e) {
		dispose();
	}
}