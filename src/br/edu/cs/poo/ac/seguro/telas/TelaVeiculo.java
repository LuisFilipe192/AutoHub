package br.edu.cs.poo.ac.seguro.telas;

import br.edu.cs.poo.ac.seguro.daos.SeguradoEmpresaDAO;
import br.edu.cs.poo.ac.seguro.daos.SeguradoPessoaDAO;
import br.edu.cs.poo.ac.seguro.daos.VeiculoDAO;
import br.edu.cs.poo.ac.seguro.entidades.CategoriaVeiculo;
import br.edu.cs.poo.ac.seguro.entidades.SeguradoEmpresa;
import br.edu.cs.poo.ac.seguro.entidades.SeguradoPessoa;
import br.edu.cs.poo.ac.seguro.entidades.Veiculo;

import javax.swing.*;
import java.awt.*;

public class TelaVeiculo extends JFrame {
    private final VeiculoDAO dao=new VeiculoDAO(); private final SeguradoPessoaDAO pessoaDAO=new SeguradoPessoaDAO(); private final SeguradoEmpresaDAO empresaDAO=new SeguradoEmpresaDAO();
    private final JTextField txtPlaca=AutoHubUI.campoTexto(), txtAno=AutoHubUI.campoTexto(), txtCpf=AutoHubUI.campoTexto(), txtCnpj=AutoHubUI.campoTexto();
    private final JComboBox<CategoriaVeiculo> cmbCategoria=AutoHubUI.combo();
    public TelaVeiculo(){
        super("AutoHub - Ve\u00edculo"); setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);setSize(820,560);setMinimumSize(new Dimension(760,520));setLocationRelativeTo(null);setLayout(new BorderLayout());
        add(AutoHubUI.cabecalho("Ve\u00edculo","Cadastro e gerenciamento da frota segurada"),BorderLayout.NORTH);
        for(CategoriaVeiculo c:CategoriaVeiculo.values())cmbCategoria.addItem(c);
        cmbCategoria.setRenderer(new DefaultListCellRenderer(){@Override public Component getListCellRendererComponent(JList<?> l,Object v,int i,boolean s,boolean f){super.getListCellRendererComponent(l,v,i,s,f);if(v instanceof CategoriaVeiculo)setText(((CategoriaVeiculo)v).getNome());return this;}});
        JPanel pagina=AutoHubUI.pagina(); JPanel dados=AutoHubUI.card("Dados do ve\u00edculo","Informe a identifica\u00e7\u00e3o e o propriet\u00e1rio");
        JPanel g=new JPanel(new GridLayout(2,2,14,14));g.setOpaque(false);g.add(AutoHubUI.campo("Placa",txtPlaca));g.add(AutoHubUI.campo("Ano",txtAno));g.add(AutoHubUI.campo("Categoria",cmbCategoria));
        JPanel prop=new JPanel(new BorderLayout(0,6));prop.setOpaque(false);JLabel l=new JLabel("CPF ou CNPJ do propriet\u00e1rio");l.setFont(AutoHubUI.fonte(12,Font.BOLD));l.setForeground(AutoHubUI.TEXT);prop.add(l,BorderLayout.NORTH);prop.add(AutoHubUI.linha(txtCpf,txtCnpj),BorderLayout.CENTER);g.add(prop);dados.add(g,BorderLayout.CENTER);
        JLabel info=new JLabel("Preencha apenas CPF ou CNPJ.");info.setForeground(AutoHubUI.MUTED);info.setFont(AutoHubUI.fonte(11,Font.PLAIN));dados.add(info,BorderLayout.SOUTH);
        JButton incluir=AutoHubUI.botao("Incluir",AutoHubUI.BLUE),alterar=AutoHubUI.botao("Alterar",AutoHubUI.BLUE_DARK),buscar=AutoHubUI.botao("Buscar",new Color(71,85,105)),excluir=AutoHubUI.botao("Excluir",AutoHubUI.DANGER),limpar=AutoHubUI.botao("Limpar",new Color(100,116,139));
        incluir.addActionListener(e->salvar(false));alterar.addActionListener(e->salvar(true));buscar.addActionListener(e->buscar());excluir.addActionListener(e->excluir());limpar.addActionListener(e->limpar());
        pagina.add(dados,BorderLayout.CENTER);pagina.add(AutoHubUI.rodape(incluir,alterar,buscar,excluir,limpar),BorderLayout.SOUTH);add(pagina,BorderLayout.CENTER);
    }
    private Veiculo obterDados(){int ano=Integer.parseInt(txtAno.getText());SeguradoPessoa pessoa=null;SeguradoEmpresa empresa=null;if(!txtCpf.getText().trim().isEmpty())pessoa=pessoaDAO.buscar(txtCpf.getText().trim());if(!txtCnpj.getText().trim().isEmpty())empresa=empresaDAO.buscar(txtCnpj.getText().trim());if(pessoa==null&&empresa==null)throw new IllegalArgumentException();return new Veiculo(txtPlaca.getText().trim(),ano,empresa,pessoa,(CategoriaVeiculo)cmbCategoria.getSelectedItem());}
    private void salvar(boolean alterar){try{Veiculo v=obterDados();boolean ok=alterar?dao.alterar(v):dao.incluir(v);if(ok)AutoHubUI.sucesso(this,alterar?"Ve\u00edculo alterado com sucesso.":"Ve\u00edculo inclu\u00eddo com sucesso.");else AutoHubUI.erro(this,alterar?"Ve\u00edculo n\u00e3o encontrado.":"Placa j\u00e1 cadastrada.");}catch(Exception e){AutoHubUI.erro(this,"Verifique placa, ano e propriet\u00e1rio.");}}
    private void buscar(){Veiculo v=dao.buscar(txtPlaca.getText().trim());if(v==null){AutoHubUI.erro(this,"Ve\u00edculo n\u00e3o encontrado.");return;}txtAno.setText(String.valueOf(v.getAno()));cmbCategoria.setSelectedItem(v.getCategoria());txtCpf.setText(v.getProprietarioPessoa()==null?"":v.getProprietarioPessoa().getCpf());txtCnpj.setText(v.getProprietarioEmpresa()==null?"":v.getProprietarioEmpresa().getCnpj());AutoHubUI.sucesso(this,"Dados carregados com sucesso.");}
    private void excluir(){if(dao.excluir(txtPlaca.getText().trim())){AutoHubUI.sucesso(this,"Ve\u00edculo exclu\u00eddo com sucesso.");limpar();}else AutoHubUI.erro(this,"Ve\u00edculo n\u00e3o encontrado.");}
    private void limpar(){txtPlaca.setText("");txtAno.setText("");txtCpf.setText("");txtCnpj.setText("");cmbCategoria.setSelectedIndex(0);txtPlaca.requestFocus();}
    public static void main(String[] args){SwingUtilities.invokeLater(()->new TelaVeiculo().setVisible(true));}
}
