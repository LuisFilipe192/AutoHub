package br.edu.cs.poo.ac.seguro.telas;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.math.BigDecimal;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import br.edu.cs.poo.ac.seguro.daos.ApoliceDAO;
import br.edu.cs.poo.ac.seguro.daos.VeiculoDAO;
import br.edu.cs.poo.ac.seguro.entidades.Apolice;
import br.edu.cs.poo.ac.seguro.entidades.Veiculo;

public class TelaApolice extends JFrame {
	private JTextField txtNumero = new JTextField();
	private JTextField txtPlaca = new JTextField();
	private JTextField txtFranquia = new JTextField();
	private JTextField txtPremio = new JTextField();
	private JTextField txtMaximo = new JTextField();

	private JButton btnIncluir = new JButton("Incluir");
	private JButton btnAlterar = new JButton("Alterar");
	private JButton btnExcluir = new JButton("Excluir");
	private JButton btnBuscar = new JButton("Buscar");
	private JButton btnLimpar = new JButton("Limpar");

	private ApoliceDAO dao = new ApoliceDAO();
	private VeiculoDAO veiculoDAO = new VeiculoDAO();

	public TelaApolice() {
		setTitle("Apólice");
		setSize(500, 300);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);

		JPanel painel = new JPanel(new GridLayout(0, 2, 5, 5));
		painel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		painel.add(new JLabel("Número:"));
		painel.add(txtNumero);
		painel.add(new JLabel("Placa do veículo:"));
		painel.add(txtPlaca);
		painel.add(new JLabel("Valor da franquia:"));
		painel.add(txtFranquia);
		painel.add(new JLabel("Valor do prêmio:"));
		painel.add(txtPremio);
		painel.add(new JLabel("Valor máximo segurado:"));
		painel.add(txtMaximo);

		add(painel, BorderLayout.NORTH);

		JPanel painelBotoes = new JPanel();
		painelBotoes.add(btnIncluir);
		painelBotoes.add(btnAlterar);
		painelBotoes.add(btnExcluir);
		painelBotoes.add(btnBuscar);
		painelBotoes.add(btnLimpar);
		add(painelBotoes, BorderLayout.SOUTH);

		btnLimpar.addActionListener(e -> limparCampos());
		btnIncluir.addActionListener(e -> incluir());
		btnBuscar.addActionListener(e -> buscar());
		btnAlterar.addActionListener(e -> alterar());
		btnExcluir.addActionListener(e -> excluir());
	}

	private void limparCampos() {
		txtNumero.setText("");
		txtPlaca.setText("");
		txtFranquia.setText("");
		txtPremio.setText("");
		txtMaximo.setText("");
	}

	private BigDecimal lerValor(JTextField campo) {
		return new BigDecimal(campo.getText().trim().replace(",", "."));
	}

	private Apolice montarApolice() {
		if (txtNumero.getText().isBlank()) {
			JOptionPane.showMessageDialog(this, "Número deve ser informado");
			return null;
		}

		Veiculo veiculo = veiculoDAO.buscar(txtPlaca.getText().trim());
		if (veiculo == null) {
			JOptionPane.showMessageDialog(this, "Veículo não encontrado");
			return null;
		}

		Apolice apolice = new Apolice(veiculo, lerValor(txtFranquia), lerValor(txtPremio),
				lerValor(txtMaximo));
		apolice.setNumero(txtNumero.getText().trim());
		return apolice;
	}

	private void preencherCampos(Apolice apolice) {
		txtNumero.setText(apolice.getNumero());
		if (apolice.getVeiculo() != null) {
			txtPlaca.setText(apolice.getVeiculo().getPlaca());
		} else {
			txtPlaca.setText("");
		}
		txtFranquia.setText(String.valueOf(apolice.getValorFranquia()));
		txtPremio.setText(String.valueOf(apolice.getValorPremio()));
		txtMaximo.setText(String.valueOf(apolice.getValorMaximoSegurado()));
	}

	private void incluir() {
		try {
			Apolice apolice = montarApolice();
			if (apolice == null) {
				return;
			}
			if (dao.incluir(apolice)) {
				JOptionPane.showMessageDialog(this, "Apólice incluída com sucesso");
				limparCampos();
			} else {
				JOptionPane.showMessageDialog(this, "Número de apólice já existente");
			}
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "Valor inválido");
		}
	}

	private void buscar() {
		Apolice apolice = dao.buscar(txtNumero.getText().trim());
		if (apolice == null) {
			JOptionPane.showMessageDialog(this, "Apólice não encontrada");
		} else {
			preencherCampos(apolice);
		}
	}

	private void alterar() {
		try {
			Apolice apolice = montarApolice();
			if (apolice == null) {
				return;
			}
			if (dao.alterar(apolice)) {
				JOptionPane.showMessageDialog(this, "Apólice alterada com sucesso");
				limparCampos();
			} else {
				JOptionPane.showMessageDialog(this, "Número de apólice não existente");
			}
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "Valor inválido");
		}
	}

	private void excluir() {
		if (dao.excluir(txtNumero.getText().trim())) {
			JOptionPane.showMessageDialog(this, "Apólice excluída com sucesso");
			limparCampos();
		} else {
			JOptionPane.showMessageDialog(this, "Número de apólice não existente");
		}
	}

	public static void main(String[] args) {
		TelaApolice tela = new TelaApolice();
		tela.setVisible(true);
	}
}