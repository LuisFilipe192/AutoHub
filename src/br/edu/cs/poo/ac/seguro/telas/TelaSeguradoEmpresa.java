package br.edu.cs.poo.ac.seguro.telas;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import br.edu.cs.poo.ac.seguro.entidades.Endereco;
import br.edu.cs.poo.ac.seguro.entidades.SeguradoEmpresa;
import br.edu.cs.poo.ac.seguro.mediators.SeguradoEmpresaMediator;


public class TelaSeguradoEmpresa extends JFrame {
	private JTextField txtCnpj = new JTextField();
	private JTextField txtNome = new JTextField();
	private JTextField txtDataAbertura = new JTextField();
	private JTextField txtFaturamento = new JTextField();
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
	
	private JCheckBox chkLocadora = new JCheckBox("É locadora de veículos");
	
	private DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	private SeguradoEmpresaMediator mediator = SeguradoEmpresaMediator.getInstancia();
	
	public TelaSeguradoEmpresa() {
		setTitle("Segurado Empresa");
		setSize(600, 500);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		JPanel painel = new JPanel (new GridLayout(0,2,5,5));
		painel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10,10));
		painel.add(new JLabel("CNPJ:"));
		painel.add(txtCnpj);
		painel.add(new JLabel("Nome:"));
		painel.add(txtNome);
		painel.add(new JLabel("Data de Abertura:"));
		painel.add(txtDataAbertura);
		painel.add(new JLabel("Faturamento:"));
		painel.add(txtFaturamento);
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
		painel.add(new JLabel(""));
		painel.add(chkLocadora);
		
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
	    txtCnpj.setText("");
	    txtNome.setText("");
	    txtDataAbertura.setText("");
	    txtFaturamento.setText("");
	    txtLogradouro.setText("");
	    txtCep.setText("");
	    txtNumero.setText("");
	    txtComplemento.setText("");
	    txtCidade.setText("");
	    txtEstado.setText("");
	    txtPais.setText("");
	    chkLocadora.setSelected(false);
	    
	}
	
	private SeguradoEmpresa montarSegurado() {
		Endereco endereco = new Endereco(txtLogradouro.getText(),txtCep.getText(), txtNumero.getText(),txtComplemento.getText(), txtPais.getText(), txtEstado.getText(),txtCidade.getText());
		
		LocalDate dataAbertura = null;
		if(!txtDataAbertura.getText().isBlank()) {
			dataAbertura = LocalDate.parse(txtDataAbertura.getText(),formato);
		}
		
		double faturamento = Double.parseDouble(txtFaturamento.getText().replace(",", "."));
		
		return new SeguradoEmpresa(txtNome.getText(), endereco, dataAbertura, BigDecimal.ZERO, txtCnpj.getText(), faturamento, chkLocadora.isSelected());
	}

	
	private void preencherCampos(SeguradoEmpresa seg) {
		txtCnpj.setText(seg.getCnpj());
		txtNome.setText(seg.getNome());
		txtDataAbertura.setText(seg.getDataAbertura().format(formato));
		txtFaturamento.setText(String.valueOf(seg.getFaturamento()));
		
		Endereco end = seg.getEndereco();
		txtLogradouro.setText(end.getLogradouro());
		txtCep.setText(end.getCep());
		txtNumero.setText(end.getNumero());
		txtComplemento.setText(end.getComplemento());
		txtCidade.setText(end.getCidade());
		txtEstado.setText(end.getEstado());
		txtPais.setText(end.getPais());
		chkLocadora.setSelected(seg.isEhLocadoraDeVeiculos());
	}
	
	
	private void incluir() {
		try {
			SeguradoEmpresa seg = montarSegurado();
			String mensagem = mediator.incluirSeguradoEmpresa(seg);
			if(mensagem == null) {
				JOptionPane.showMessageDialog(this, "Segurado Incluido com sucesso");
				limparCampos();
			}else {
				JOptionPane.showMessageDialog(this,mensagem);
			}
		} catch (DateTimeParseException e) {
			JOptionPane.showMessageDialog(this, "Data inválida. Use o formato dd/mm/aaaa");
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "Faturamento Inválido");
		}
	}
		
	private void buscar() {
	    SeguradoEmpresa seg = mediator.buscarSeguradoEmpresa(txtCnpj.getText());
	    if (seg == null) {
	        JOptionPane.showMessageDialog(this, "Segurado não encontrado");
	    } else {
	        preencherCampos(seg);
	    }
	}
	
	
	private void alterar() {
		try {
			SeguradoEmpresa seg = montarSegurado();
			String mensagem = mediator.alterarSeguradoEmpresa(seg);
			if(mensagem == null) {
				JOptionPane.showMessageDialog(this, "Segurado alterado com sucesso");
				limparCampos();
			}else {
				JOptionPane.showMessageDialog(this,mensagem);
			}
		} catch (DateTimeParseException e) {
			JOptionPane.showMessageDialog(this, "Data inválida. Use o formato dd/mm/aaaa");
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "Faturamento Inválido");
		}
	}
	
	private void excluir() {
	    String mensagem = mediator.excluirSeguradoEmpresa(txtCnpj.getText());
	    if (mensagem == null) {
	        JOptionPane.showMessageDialog(this, "Segurado excluido com sucesso");
	        limparCampos();
	    } else {
	    	JOptionPane.showMessageDialog(this, mensagem);
	    }
	}
	
	public static void main(String[] args) {
		TelaSeguradoEmpresa tela = new TelaSeguradoEmpresa();
		tela.setVisible(true);
	}
	
	
}
