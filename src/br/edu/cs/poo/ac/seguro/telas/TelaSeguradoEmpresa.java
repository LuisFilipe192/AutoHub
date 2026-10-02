package br.edu.cs.poo.ac.seguro.telas;

import br.edu.cs.poo.ac.seguro.entidades.Endereco;
import br.edu.cs.poo.ac.seguro.entidades.SeguradoEmpresa;
import br.edu.cs.poo.ac.seguro.mediators.SeguradoEmpresaMediator;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public class TelaSeguradoEmpresa extends JFrame {
    private final SeguradoEmpresaMediator mediator = SeguradoEmpresaMediator.getInstancia();
    private final JTextField txtCnpj = AutoHubUI.campoTexto();
    private final JTextField txtNome = AutoHubUI.campoTexto();
    private final JTextField txtAbertura = AutoHubUI.campoTexto();
    private final JTextField txtFaturamento = AutoHubUI.campoTexto();
    private final JCheckBox chkLocadora = AutoHubUI.check("Empresa locadora de ve\u00edculos");
    private final JTextField txtLogradouro = AutoHubUI.campoTexto();
    private final JTextField txtCep = AutoHubUI.campoTexto();
    private final JTextField txtNumero = AutoHubUI.campoTexto();
    private final JTextField txtComplemento = AutoHubUI.campoTexto();
    private final JTextField txtPais = AutoHubUI.campoTexto();
    private final JTextField txtEstado = AutoHubUI.campoTexto();
    private final JTextField txtCidade = AutoHubUI.campoTexto();

    public TelaSeguradoEmpresa() {
        super("AutoHub - Segurado Empresa");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); setSize(900, 730); setMinimumSize(new Dimension(850, 690)); setLocationRelativeTo(null); setLayout(new BorderLayout());
        add(AutoHubUI.cabecalho("Segurado Empresa", "Cadastro e gerenciamento de empresas seguradas"), BorderLayout.NORTH);
        JPanel pagina = AutoHubUI.pagina();
        JPanel dados = AutoHubUI.card("Dados da empresa", "Informe os dados cadastrais e financeiros");
        JPanel grid = new JPanel(new GridLayout(2, 2, 14, 14)); grid.setOpaque(false);
        grid.add(AutoHubUI.campo("CNPJ", txtCnpj)); grid.add(AutoHubUI.campo("Raz\u00e3o social", txtNome)); grid.add(AutoHubUI.campo("Data de abertura", txtAbertura)); grid.add(AutoHubUI.campo("Faturamento", txtFaturamento));
        dados.add(grid, BorderLayout.CENTER);
        JPanel check = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 8)); check.setOpaque(false); check.add(chkLocadora); dados.add(check, BorderLayout.SOUTH);
        JPanel endereco = AutoHubUI.card("Endere\u00e7o", "Localiza\u00e7\u00e3o da empresa");
        JPanel box = new JPanel(new GridLayout(3,1,0,12)); box.setOpaque(false);
        JPanel e1 = new JPanel(new GridLayout(1,3,14,0)); e1.setOpaque(false); e1.add(AutoHubUI.campo("Logradouro",txtLogradouro)); e1.add(AutoHubUI.campo("CEP",txtCep)); e1.add(AutoHubUI.campo("N\u00famero",txtNumero));
        JPanel e2 = new JPanel(new GridLayout(1,3,14,0)); e2.setOpaque(false); e2.add(AutoHubUI.campo("Complemento",txtComplemento)); e2.add(AutoHubUI.campo("Cidade",txtCidade)); e2.add(AutoHubUI.campo("Estado",txtEstado));
        JPanel e3 = new JPanel(new GridLayout(1,3,14,0)); e3.setOpaque(false); e3.add(AutoHubUI.campo("Pa\u00eds",txtPais)); e3.add(new JPanel()); e3.add(new JPanel());
        box.add(e1); box.add(e2); box.add(e3); endereco.add(box, BorderLayout.CENTER);
        JButton incluir=AutoHubUI.botao("Incluir",AutoHubUI.BLUE), alterar=AutoHubUI.botao("Alterar",AutoHubUI.BLUE_DARK), buscar=AutoHubUI.botao("Buscar",new Color(71,85,105)), excluir=AutoHubUI.botao("Excluir",AutoHubUI.DANGER), limpar=AutoHubUI.botao("Limpar",new Color(100,116,139));
        incluir.addActionListener(e->salvar(false)); alterar.addActionListener(e->salvar(true)); buscar.addActionListener(e->buscar()); excluir.addActionListener(e->excluir()); limpar.addActionListener(e->limpar());
        pagina.add(dados,BorderLayout.NORTH); pagina.add(endereco,BorderLayout.CENTER); pagina.add(AutoHubUI.rodape(incluir,alterar,buscar,excluir,limpar),BorderLayout.SOUTH); add(pagina,BorderLayout.CENTER);
    }
    private SeguradoEmpresa obterDados(){ Endereco e=new Endereco(txtLogradouro.getText(),txtCep.getText(),txtNumero.getText(),txtComplemento.getText(),txtPais.getText(),txtEstado.getText(),txtCidade.getText()); LocalDate abertura=LocalDate.parse(txtAbertura.getText()); double faturamento=Double.parseDouble(txtFaturamento.getText().replace(",",".")); return new SeguradoEmpresa(txtNome.getText(),e,abertura,BigDecimal.ZERO,txtCnpj.getText(),faturamento,chkLocadora.isSelected()); }
    private void salvar(boolean alterar){ try{ SeguradoEmpresa s=obterDados(); String msg=alterar?mediator.alterarSeguradoEmpresa(s):mediator.incluirSeguradoEmpresa(s); if(msg!=null)AutoHubUI.erro(this,msg); else AutoHubUI.sucesso(this,alterar?"Empresa alterada com sucesso.":"Empresa inclu\u00edda com sucesso."); }catch(Exception e){AutoHubUI.erro(this,"Verifique os dados informados.");} }
    private void buscar(){ SeguradoEmpresa s=mediator.buscarSeguradoEmpresa(txtCnpj.getText()); if(s==null){AutoHubUI.erro(this,"Empresa n\u00e3o encontrada.");return;} preencher(s); AutoHubUI.sucesso(this,"Dados carregados com sucesso."); }
    private void excluir(){ String msg=mediator.excluirSeguradoEmpresa(txtCnpj.getText()); if(msg!=null)AutoHubUI.erro(this,msg);else{AutoHubUI.sucesso(this,"Empresa exclu\u00edda com sucesso.");limpar();} }
    private void preencher(SeguradoEmpresa s){ Endereco e=s.getEndereco(); txtNome.setText(AutoHubUI.texto(s.getNome()));txtAbertura.setText(s.getDataAbertura().toString());txtFaturamento.setText(String.valueOf(s.getFaturamento()));chkLocadora.setSelected(s.isEhLocadoraDeVeiculos());txtLogradouro.setText(AutoHubUI.texto(e.getLogradouro()));txtCep.setText(AutoHubUI.texto(e.getCep()));txtNumero.setText(AutoHubUI.texto(e.getNumero()));txtComplemento.setText(AutoHubUI.texto(e.getComplemento()));txtPais.setText(AutoHubUI.texto(e.getPais()));txtEstado.setText(AutoHubUI.texto(e.getEstado()));txtCidade.setText(AutoHubUI.texto(e.getCidade())); }
    private void limpar(){for(JTextField f:new JTextField[]{txtCnpj,txtNome,txtAbertura,txtFaturamento,txtLogradouro,txtCep,txtNumero,txtComplemento,txtPais,txtEstado,txtCidade})f.setText("");chkLocadora.setSelected(false);txtCnpj.requestFocus();}
    public static void main(String[] args){SwingUtilities.invokeLater(()->new TelaSeguradoEmpresa().setVisible(true));}
}
