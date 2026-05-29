package utils;

import java.awt.Component;

import javax.swing.JDialog;

public class AppUtils {

	public static void abrirDialogo(Component parent, JDialog dlg) {
		dlg.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dlg.setLocationRelativeTo(parent);
		dlg.setModal(true);
		dlg.setVisible(true);
	}
}