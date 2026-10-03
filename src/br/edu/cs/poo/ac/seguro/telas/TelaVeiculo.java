package br.edu.cs.poo.ac.seguro.telas;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import br.edu.cs.poo.ac.seguro.daos.VeiculoDAO;
import br.edu.cs.poo.ac.seguro.entidades.CategoriaVeiculo;
import br.edu.cs.poo.ac.seguro.entidades.SeguradoEmpresa;
import br.edu.cs.poo.ac.seguro.entidades.SeguradoPessoa;
import br.edu.cs.poo.ac.seguro.entidades.Veiculo;
import br.edu.cs.poo.ac.seguro.mediators.SeguradoEmpresaMediator;
import br.edu.cs.poo.ac.seguro.mediators.SeguradoPessoaMediator;

public class TelaVeiculo extends JFrame {
	private JTextField txtPlaca = new JTextField();
	private JTextField txtAno = new JTextField();
	private JComboBox<CategoriaVeiculo> cmbCategoria = new JComboBox<>(CategoriaVeiculo.values());
	private JRadioButton rbPessoa = new JRadioButton("Pessoa", true);
	private JRadioButton rbEmpresa = new JRadioButton("Empresa");
	private JTextField txtDocumento = new JTextField();

	private JButton btnIncluir = new JButton("Incluir");
	private JButton btnAlterar = new JButton("Alterar");
	private JButton btnExcluir = new JButton("Excluir");
	private JButton btnBuscar = new JButton("Buscar");
	private JButton btnLimpar = new JButton("Limpar");

	private VeiculoDAO dao = new VeiculoDAO();
	private SeguradoPessoaMediator mediatorPessoa = SeguradoPessoaMediator.getInstancia();
	private SeguradoEmpresaMediator mediatorEmpresa = SeguradoEmpresaMediator.getInstancia();

	public TelaVeiculo() {
		setTitle("Veículo");
		setSize(500, 300);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);

		ButtonGroup grupo = new ButtonGroup();
		grupo.add(rbPessoa);
		grupo.add(rbEmpresa);

		JPanel painelTipo = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		painelTipo.add(rbPessoa);
		painelTipo.add(rbEmpresa);

		JPanel painel = new JPanel(new GridLayout(0, 2, 5, 5));
		painel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		painel.add(new JLabel("Placa:"));
		painel.add(txtPlaca);
		painel.add(new JLabel("Ano:"));
		painel.add(txtAno);
		painel.add(new JLabel("Categoria:"));
		painel.add(cmbCategoria);
		painel.add(new JLabel("Proprietário:"));
		painel.add(painelTipo);
		painel.add(new JLabel("CPF/CNPJ do proprietário:"));
		painel.add(txtDocumento);

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
		txtPlaca.setText("");
		txtAno.setText("");
		txtDocumento.setText("");
		cmbCategoria.setSelectedIndex(0);
		rbPessoa.setSelected(true);
	}

	private Veiculo montarVeiculo() {
		if (txtPlaca.getText().isBlank()) {
			JOptionPane.showMessageDialog(this, "Placa deve ser informada");
			return null;
		}

		int ano = Integer.parseInt(txtAno.getText().trim());

		SeguradoPessoa pessoa = null;
		SeguradoEmpresa empresa = null;
		if (rbPessoa.isSelected()) {
			pessoa = mediatorPessoa.buscarSeguradoPessoa(txtDocumento.getText().trim());
		} else {
			empresa = mediatorEmpresa.buscarSeguradoEmpresa(txtDocumento.getText().trim());
		}

		if (pessoa == null && empresa == null) {
			JOptionPane.showMessageDialog(this, "Proprietário não encontrado");
			return null;
		}

		return new Veiculo(txtPlaca.getText().trim(), ano, empresa, pessoa,
				(CategoriaVeiculo) cmbCategoria.getSelectedItem());
	}

	private void preencherCampos(Veiculo veiculo) {
		txtPlaca.setText(veiculo.getPlaca());
		txtAno.setText(String.valueOf(veiculo.getAno()));
		cmbCategoria.setSelectedItem(veiculo.getCategoria());

		if (veiculo.getProprietarioPessoa() != null) {
			rbPessoa.setSelected(true);
			txtDocumento.setText(veiculo.getProprietarioPessoa().getCpf());
		} else if (veiculo.getProprietarioEmpresa() != null) {
			rbEmpresa.setSelected(true);
			txtDocumento.setText(veiculo.getProprietarioEmpresa().getCnpj());
		} else {
			rbPessoa.setSelected(true);
			txtDocumento.setText("");
		}
	}

	private void incluir() {
		try {
			Veiculo veiculo = montarVeiculo();
			if (veiculo == null) {
				return;
			}
			if (dao.incluir(veiculo)) {
				JOptionPane.showMessageDialog(this, "Veículo incluído com sucesso");
				limparCampos();
			} else {
				JOptionPane.showMessageDialog(this, "Placa já existente");
			}
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "Ano inválido");
		}
	}

	private void buscar() {
		Veiculo veiculo = dao.buscar(txtPlaca.getText().trim());
		if (veiculo == null) {
			JOptionPane.showMessageDialog(this, "Veículo não encontrado");
		} else {
			preencherCampos(veiculo);
		}
	}

	private void alterar() {
		try {
			Veiculo veiculo = montarVeiculo();
			if (veiculo == null) {
				return;
			}
			if (dao.alterar(veiculo)) {
				JOptionPane.showMessageDialog(this, "Veículo alterado com sucesso");
				limparCampos();
			} else {
				JOptionPane.showMessageDialog(this, "Placa não existente");
			}
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "Ano inválido");
		}
	}

	private void excluir() {
		if (dao.excluir(txtPlaca.getText().trim())) {
			JOptionPane.showMessageDialog(this, "Veículo excluído com sucesso");
			limparCampos();
		} else {
			JOptionPane.showMessageDialog(this, "Placa não existente");
		}
	}

	public static void main(String[] args) {
		Temas.aplicar();
		TelaVeiculo tela = new TelaVeiculo();
		tela.setVisible(true);
	}
}