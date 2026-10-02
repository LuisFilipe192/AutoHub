package br.edu.cs.poo.ac.seguro.telas;

import br.edu.cs.poo.ac.seguro.daos.ApoliceDAO;
import br.edu.cs.poo.ac.seguro.daos.VeiculoDAO;
import br.edu.cs.poo.ac.seguro.entidades.Apolice;
import br.edu.cs.poo.ac.seguro.entidades.Veiculo;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;

public class TelaApolice extends JFrame {
    private final ApoliceDAO dao=new ApoliceDAO(); private final VeiculoDAO veiculoDAO=new VeiculoDAO();
    private final JTextField txtNumero=AutoHubUI.campoTexto(),txtPlaca=AutoHubUI.campoTexto(),txtFranquia=AutoHubUI.campoTexto(),txtPremio=AutoHubUI.campoTexto(),txtMaximo=AutoHubUI.campoTexto();
    public TelaApolice(){
        super("AutoHub - Ap\u00f3lice");setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);setSize(820,560);setMinimumSize(new Dimension(760,520));setLocationRelativeTo(null);setLayout(new BorderLayout());add(AutoHubUI.cabecalho("Ap\u00f3lice","Gest\u00e3o das ap\u00f3lices de seguro"),BorderLayout.NORTH);
        JPanel pagina=AutoHubUI.pagina();JPanel dados=AutoHubUI.card("Dados da ap\u00f3lice","Valores e ve\u00edculo vinculado ao seguro");JPanel g=new JPanel(new GridLayout(3,2,14,14));g.setOpaque(false);g.add(AutoHubUI.campo("N\u00famero",txtNumero));g.add(AutoHubUI.campo("Placa do ve\u00edculo",txtPlaca));g.add(AutoHubUI.campo("Valor da franquia",txtFranquia));g.add(AutoHubUI.campo("Valor do pr\u00eamio",txtPremio));g.add(AutoHubUI.campo("Valor m\u00e1ximo segurado",txtMaximo));g.add(new JPanel());dados.add(g,BorderLayout.CENTER);
        JButton incluir=AutoHubUI.botao("Incluir",AutoHubUI.BLUE),alterar=AutoHubUI.botao("Alterar",AutoHubUI.BLUE_DARK),buscar=AutoHubUI.botao("Buscar",new Color(71,85,105)),excluir=AutoHubUI.botao("Excluir",AutoHubUI.DANGER),limpar=AutoHubUI.botao("Limpar",new Color(100,116,139));incluir.addActionListener(e->salvar(false));alterar.addActionListener(e->salvar(true));buscar.addActionListener(e->buscar());excluir.addActionListener(e->excluir());limpar.addActionListener(e->limpar());pagina.add(dados,BorderLayout.CENTER);pagina.add(AutoHubUI.rodape(incluir,alterar,buscar,excluir,limpar),BorderLayout.SOUTH);add(pagina,BorderLayout.CENTER);
    }
    private Apolice obterDados(){Veiculo v=veiculoDAO.buscar(txtPlaca.getText().trim());if(v==null)throw new IllegalArgumentException();Apolice a=new Apolice(v,new BigDecimal(txtFranquia.getText().replace(",",".")),new BigDecimal(txtPremio.getText().replace(",",".")),new BigDecimal(txtMaximo.getText().replace(",",".")));a.setNumero(txtNumero.getText().trim());return a;}
    private void salvar(boolean alterar){try{Apolice a=obterDados();boolean ok=alterar?dao.alterar(a):dao.incluir(a);if(ok)AutoHubUI.sucesso(this,alterar?"Ap\u00f3lice alterada com sucesso.":"Ap\u00f3lice inclu\u00edda com sucesso.");else AutoHubUI.erro(this,alterar?"Ap\u00f3lice n\u00e3o encontrada.":"N\u00famero de ap\u00f3lice j\u00e1 cadastrado.");}catch(Exception e){AutoHubUI.erro(this,"Verifique os valores e a placa informada.");}}
    private void buscar(){Apolice a=dao.buscar(txtNumero.getText().trim());if(a==null){AutoHubUI.erro(this,"Ap\u00f3lice n\u00e3o encontrada.");return;}txtPlaca.setText(a.getVeiculo()==null?"":a.getVeiculo().getPlaca());txtFranquia.setText(a.getValorFranquia().toString());txtPremio.setText(a.getValorPremio().toString());txtMaximo.setText(a.getValorMaximoSegurado().toString());AutoHubUI.sucesso(this,"Dados carregados com sucesso.");}
    private void excluir(){if(dao.excluir(txtNumero.getText().trim())){AutoHubUI.sucesso(this,"Ap\u00f3lice exclu\u00edda com sucesso.");limpar();}else AutoHubUI.erro(this,"Ap\u00f3lice n\u00e3o encontrada.");}
    private void limpar(){for(JTextField f:new JTextField[]{txtNumero,txtPlaca,txtFranquia,txtPremio,txtMaximo})f.setText("");txtNumero.requestFocus();}
    public static void main(String[] args){SwingUtilities.invokeLater(()->new TelaApolice().setVisible(true));}
}
