package views;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.formdev.flatlaf.FlatDarkLaf;

import utils.Mensajes;

public class FrmLogin extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel pnlLogin;
	private JPasswordField pswContrasena;
	private JTextField txtUsuario;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		FlatDarkLaf.setup();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrmLogin frame = new FrmLogin();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FrmLogin() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		pnlLogin = new JPanel();
		pnlLogin.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(pnlLogin);
		pnlLogin.setLayout(null);
		setLocationRelativeTo(null);

		JLabel lblTitulo = new JLabel("INICIO DE SESIÓN");
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitulo.setBounds(125, 25, 200, 25);
		pnlLogin.add(lblTitulo);

		JLabel lblUsuario = new JLabel("Usuario");
		lblUsuario.setBounds(145, 75, 160, 20);
		pnlLogin.add(lblUsuario);

		txtUsuario = new JTextField();
		txtUsuario.setBounds(145, 100, 160, 25);
		pnlLogin.add(txtUsuario);
		txtUsuario.setColumns(10);

		JLabel lblContrasena = new JLabel("Contraseña");
		lblContrasena.setBounds(145, 135, 160, 20);
		pnlLogin.add(lblContrasena);

		pswContrasena = new JPasswordField();
		pswContrasena.setBounds(145, 160, 160, 25);
		pnlLogin.add(pswContrasena);

		JButton btnIngresar = new JButton("Ingresar");
		btnIngresar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				iniciarSesion();
			}
		});
		btnIngresar.setBounds(170, 205, 110, 30);
		pnlLogin.add(btnIngresar);
	}

	protected void iniciarSesion() {
		String usuario = txtUsuario.getText();
		String contrasena = new String(pswContrasena.getPassword());

		if (usuario.equals("admin") && contrasena.equals("123")) {
			FrmMain main = new FrmMain();

			main.setVisible(true);
			dispose();
		} else {
			Mensajes.mensajeError(this, "Usuario o contraseña incorrectos");

			txtUsuario.setText("");
			pswContrasena.setText("");
			txtUsuario.requestFocus();
		}
	}
}