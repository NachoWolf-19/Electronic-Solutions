package utils;

import java.awt.Component;

import javax.swing.JOptionPane;

public class Mensajes {
	// Mensaje Error
	public static void mensajeError(Component parent, String msj) {
		JOptionPane.showMessageDialog(parent, msj, "Error!!", JOptionPane.ERROR_MESSAGE);
	}

	// Mensaje Alerta
	public static void mensajeAlerta(Component parent, String msj, String title) {
		JOptionPane.showMessageDialog(parent, msj, title, JOptionPane.WARNING_MESSAGE);
	}

	// Mensaje Confirmar
	public static boolean mensajeConfirmar(Component parent, String msj) {
		int result = JOptionPane.showConfirmDialog(parent, msj, "Confirmar", JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE);
		return result == JOptionPane.YES_OPTION;
	}

	// Mensaje Exito
	public static void mensajeExito(Component parent, String msj, String title) {
		JOptionPane.showMessageDialog(parent, msj, title, JOptionPane.INFORMATION_MESSAGE);
	}
}
