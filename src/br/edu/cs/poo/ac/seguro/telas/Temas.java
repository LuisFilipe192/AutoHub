package br.edu.cs.poo.ac.seguro.telas;

import javax.swing.UIManager;

public class Temas {
	private Temas() {
	}

	public static void aplicar() {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}