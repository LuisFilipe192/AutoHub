package br.edu.cs.poo.ac.seguro.telas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

import br.edu.cs.poo.ac.seguro.entidades.Endereco;
import br.edu.cs.poo.ac.seguro.entidades.SeguradoPessoa;
import br.edu.cs.poo.ac.seguro.mediators.SeguradoPessoaMediator;


public class TelaSeguradoPessoa extends JFrame {
	private JTextField txtCpf = new JTextField();
	private JTextField txtNome = new JTextField();
	private JTextField txtDataNascimento = new JTextField();
	private JTextField txtRenda = new JTextField();
	private JTextField txtLogradouro = new JTextField();
	private JTextField txtCep = new JTextField();
	private JTextField txtNumero = new JTextField();
	private JTextField txtComplemento = new JTextField();
	private JTextField txtCidade = new JTextField();
	private JTextField txtEstado = new JTextField();
	private JTextField txtPais = new JTextField();
	
	private JButton btnIncluir = new JButton("Incluir");
	private JButton btnAlterar = new JButton("Alterar");
	private JButton btnExcluir = new JButton("Excluir");
	private JButton btnBuscar = new JButton("Buscar");
	private JButton btnLimpar = new JButton("Limpar");
	
	private DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	private SeguradoPessoaMediator mediator = SeguradoPessoaMediator.getInstancia();
	
	public TelaSeguradoPessoa() {
		setTitle("Segurado Pessoa");
		setSize(600, 500);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		JPanel painel = new JPanel (new GridLayout(0,2,5,5));
		painel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10,10));
		painel.add(new JLabel("CPF:"));
		painel.add(txtCpf);
		painel.add(new JLabel("Nome:"));
		painel.add(txtNome);
		painel.add(new JLabel("Data de Nascimento:"));
		painel.add(txtDataNascimento);
		painel.add(new JLabel("Renda:"));
		painel.add(txtRenda);
		painel.add(new JLabel("Logradouro:"));
		painel.add(txtLogradouro);
		painel.add(new JLabel("Cep:"));
		painel.add(txtCep);
		painel.add(new JLabel("Número:"));
		painel.add(txtNumero);
		painel.add(new JLabel("Complemento:"));
		painel.add(txtComplemento);
		painel.add(new JLabel("Cidade:"));
		painel.add(txtCidade);
		painel.add(new JLabel("Estado:"));
		painel.add(txtEstado);
		painel.add(new JLabel("País:"));
		painel.add(txtPais);
		
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
	    txtCpf.setText("");
	    txtNome.setText("");
	    txtDataNascimento.setText("");
	    txtRenda.setText("");
	    txtLogradouro.setText("");
	    txtCep.setText("");
	    txtNumero.setText("");
	    txtComplemento.setText("");
	    txtCidade.setText("");
	    txtEstado.setText("");
	    txtPais.setText("");
	    
	}
	
	private SeguradoPessoa montarSegurado() {
		Endereco endereco = new Endereco(txtLogradouro.getText(),txtCep.getText(), txtNumero.getText(),txtComplemento.getText(), txtPais.getText(), txtEstado.getText(),txtCidade.getText());
		
		LocalDate dataNascimento = null;
		if(!txtDataNascimento.getText().isBlank()) {
			dataNascimento = LocalDate.parse(txtDataNascimento.getText(),formato);
		}
		
		double renda = Double.parseDouble(txtRenda.getText().replace(",", "."));
		
		return new SeguradoPessoa(txtNome.getText(), endereco, dataNascimento, BigDecimal.ZERO, txtCpf.getText(), renda);
	}

	
	private void preencherCampos(SeguradoPessoa seg) {
		txtCpf.setText(seg.getCpf());
		txtNome.setText(seg.getNome());
		txtDataNascimento.setText(seg.getDataNascimento().format(formato));
		txtRenda.setText(String.valueOf(seg.getRenda()));
		
		Endereco end = seg.getEndereco();
		txtLogradouro.setText(end.getLogradouro());
		txtCep.setText(end.getCep());
		txtNumero.setText(end.getNumero());
		txtComplemento.setText(end.getComplemento());
		txtCidade.setText(end.getCidade());
		txtEstado.setText(end.getEstado());
		txtPais.setText(end.getPais());
	}
	
	
	private void incluir() {
		try {
			SeguradoPessoa seg = montarSegurado();
			String mensagem = mediator.incluirSeguradoPessoa(seg);
			if(mensagem == null) {
				JOptionPane.showMessageDialog(this, "Segurado Incluido com sucesso");
				limparCampos();
			}else {
				JOptionPane.showMessageDialog(this,mensagem);
			}
		} catch (DateTimeParseException e) {
			JOptionPane.showMessageDialog(this, "Data inválida. Use o formato dd/mm/aaaa");
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "Renda Inválida");
		}
	}
		
	private void buscar() {
	    SeguradoPessoa seg = mediator.buscarSeguradoPessoa(txtCpf.getText());
	    if (seg == null) {
	        JOptionPane.showMessageDialog(this, "Segurado não encontrado");
	    } else {
	        preencherCampos(seg);
	    }
	}
	
	
	private void alterar() {
		try {
			SeguradoPessoa seg = montarSegurado();
			String mensagem = mediator.alterarSeguradoPessoa(seg);
			if(mensagem == null) {
				JOptionPane.showMessageDialog(this, "Segurado alterado com sucesso");
				limparCampos();
			}else {
				JOptionPane.showMessageDialog(this,mensagem);
			}
		} catch (DateTimeParseException e) {
			JOptionPane.showMessageDialog(this, "Data inválida. Use o formato dd/mm/aaaa");
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "Renda Inválida");
		}
	}
	
	private void excluir() {
	    String mensagem = mediator.excluirSeguradoPessoa(txtCpf.getText());
	    if (mensagem == null) {
	        JOptionPane.showMessageDialog(this, "Segurado excluido com sucesso");
	        limparCampos();
	    } else {
	    	JOptionPane.showMessageDialog(this, mensagem);
	    }
	}
	
	public static void main(String[] args) {
		Temas.aplicar();
		TelaSeguradoPessoa tela = new TelaSeguradoPessoa();
		tela.setVisible(true);
	}
	
	
}
