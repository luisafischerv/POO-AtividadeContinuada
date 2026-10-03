package br.edu.cs.poo.ac.seguro.telas;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

/**
 * Menu inicial: abre a tela de cada funcionalidade.
 */
public class TelaPrincipal extends JFrame {
	private static final long serialVersionUID = 1L;

	private JButton btnSeguradoPessoa = new JButton("Segurado Pessoa");
	private JButton btnSeguradoEmpresa = new JButton("Segurado Empresa");

	public TelaPrincipal() {
		super("Sistema de Seguro de Veículos");
		JPanel painel = new JPanel(new GridLayout(2, 1, 8, 8));
		painel.setBorder(new EmptyBorder(16, 16, 16, 16));
		painel.add(btnSeguradoPessoa);
		painel.add(btnSeguradoEmpresa);
		setContentPane(painel);

		btnSeguradoPessoa.addActionListener(e -> new TelaSeguradoPessoa().setVisible(true));
		btnSeguradoEmpresa.addActionListener(e -> new TelaSeguradoEmpresa().setVisible(true));

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		pack();
		setSize(Math.max(getWidth(), 320), getHeight());
		setLocationRelativeTo(null);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new TelaPrincipal().setVisible(true));
	}
}
