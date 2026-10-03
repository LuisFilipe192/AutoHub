package br.edu.cs.poo.ac.seguro.telas;

import java.text.ParseException;

import javax.swing.JFormattedTextField;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

public class Campos {
	private Campos() {
	}

	public static JFormattedTextField comMascara(String mascara) {
		try {
			MaskFormatter formatador = new MaskFormatter(mascara);
			formatador.setPlaceholderCharacter('_');
			formatador.setValueContainsLiteralCharacters(false);
			JFormattedTextField campo = new JFormattedTextField(formatador);
			campo.setFocusLostBehavior(JFormattedTextField.COMMIT);
			return campo;
		} catch (ParseException e) {
			throw new IllegalArgumentException("Mascara invalida: " + mascara, e);
		}
	}

	public static String soDigitos(JTextField campo) {
		return campo.getText().replaceAll("\\D", "");
	}

	public static void definir(JFormattedTextField campo, String digitos) {
		if (digitos == null || digitos.isEmpty()) {
			campo.setValue(null);
		} else {
			campo.setValue(digitos);
		}
	}
}