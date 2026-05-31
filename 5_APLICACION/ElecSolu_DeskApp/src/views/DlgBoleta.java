package views;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import utils.Mensajes;

public class DlgBoleta extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();

	private JTextArea txtBoleta;
	private JButton btnGuardar;
	private JButton btnCerrar;

	private String dni;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DlgBoleta dialog = new DlgBoleta("12345678", "Reparación", "Sintetizador Korg", "Teclados",
					"Falla en los potenciómetros");
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DlgBoleta(String dni, String servicio, String equipo, String categoria, String descripcion) {
		this.dni = dni;

		setTitle("Vista Previa de la Orden");
		setModal(true);
		setResizable(false);
		setBounds(100, 100, 480, 500);
		setLocationRelativeTo(null);

		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));

		// JTextArea configurado con fuente Monospaced para alinear texto correctamente
		// tipo ticket
		txtBoleta = new JTextArea();
		txtBoleta.setFont(new Font("Monospaced", Font.PLAIN, 13));
		txtBoleta.setEditable(false);

		JScrollPane scp = new JScrollPane(txtBoleta);
		contentPanel.add(scp, BorderLayout.CENTER);

		// --- BOTONES DE ACCIÓN ---
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				btnGuardar = new JButton("Guardar Archivo");
				btnGuardar.addActionListener(this);
				buttonPane.add(btnGuardar);
				getRootPane().setDefaultButton(btnGuardar);
			}
			{
				btnCerrar = new JButton("Cerrar");
				btnCerrar.addActionListener(this);
				buttonPane.add(btnCerrar);
			}
		}

		generarContenidoBoleta(servicio, equipo, categoria, descripcion);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnGuardar) {
			actionPerformedBtnGuardar(e);
		}
		if (e.getSource() == btnCerrar) {
			actionPerformedBtnCerrar(e);
		}
	}

	private void generarContenidoBoleta(String servicio, String equipo, String categoria, String descripcion) {
		// Simulación de número de orden correlativo y fecha del sistema actual
		int numOrdenSimulado = (int) (Math.random() * 9000 + 1000);
		String fechaActual = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());

		StringBuilder sb = new StringBuilder();
		sb.append("==================================================\n");
		sb.append("               BOLETA DE REGISTRO                 \n");
		sb.append("==================================================\n");
		sb.append(String.format(" Nro. Orden   : ORD-%d\n", numOrdenSimulado));
		sb.append(String.format(" Fecha Reg.   : %s\n", fechaActual));
		sb.append("--------------------------------------------------\n");
		sb.append(" DATOS DEL CLIENTE:\n");
		sb.append(String.format(" DNI Cliente  : %s\n", dni));
		sb.append("--------------------------------------------------\n");
		sb.append(" DETALLE DE LA ORDEN:\n");
		sb.append(String.format(" Servicio     : %s\n", servicio));
		sb.append(String.format(" Categoría    : %s\n", categoria));
		sb.append(String.format(" Equipo       : %s\n", equipo));
		sb.append("--------------------------------------------------\n");
		sb.append(" SÍNTOMAS / DESCRIPCIÓN DE LA FALLA:\n");
		sb.append(wrapText(descripcion, 46) + "\n");
		sb.append("==================================================\n");
		sb.append("    Documento interno para control de taller.     \n");
		sb.append("==================================================\n");

		txtBoleta.setText(sb.toString());
	}

	private void actionPerformedBtnGuardar(ActionEvent e) {
		String nombreArchivo = "boleta_ORD_" + dni + ".txt";

		// Escritura limpia y directa del JTextArea en un archivo plano .txt
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(nombreArchivo))) {
			txtBoleta.write(bw);
			Mensajes.mensajeExito(this, "Archivo guardado exitosamente como:\n" + nombreArchivo, "Éxito");
		} catch (IOException ex) {
			Mensajes.mensajeError(this, "Ocurrió un error al intentar guardar el archivo de texto.");
		}
	}

	private void actionPerformedBtnCerrar(ActionEvent e) {
		dispose();
	}

	/**
	 * Ajusta el texto de la descripción para que salte de línea limpiamente dentro
	 * de los márgenes de la boleta impresa.
	 */
	private String wrapText(String text, int limit) {
		StringBuilder sb = new StringBuilder(" ");
		int charCount = 0;
		for (String word : text.split(" ")) {
			if (charCount + word.length() > limit) {
				sb.append("\n ");
				charCount = 0;
			}
			sb.append(word).append(" ");
			charCount += word.length() + 1;
		}
		return sb.toString();
	}
}