package br.edu.cs.poo.ac.seguro.telas;

import br.edu.cs.poo.ac.seguro.daos.SinistroDAO;
import br.edu.cs.poo.ac.seguro.daos.VeiculoDAO;
import br.edu.cs.poo.ac.seguro.entidades.Sinistro;
import br.edu.cs.poo.ac.seguro.entidades.TipoSinistro;
import br.edu.cs.poo.ac.seguro.entidades.Veiculo;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TelaSinistro extends JFrame {
    private final SinistroDAO dao=new SinistroDAO(); private final VeiculoDAO veiculoDAO=new VeiculoDAO();
    private final JTextField txtNumero=AutoHubUI.campoTexto(),txtPlaca=AutoHubUI.campoTexto(),txtDataHoraSinistro=AutoHubUI.campoTexto(),txtDataHoraRegistro=AutoHubUI.campoTexto(),txtUsuario=AutoHubUI.campoTexto(),txtValor=AutoHubUI.campoTexto();
    private final JComboBox<TipoSinistro> cmbTipo=AutoHubUI.combo(); private final DateTimeFormatter formato=DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    public TelaSinistro(){
        super("AutoHub - Sinistro");setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);setSize(900,650);setMinimumSize(new Dimension(840,600));setLocationRelativeTo(null);setLayout(new BorderLayout());add(AutoHubUI.cabecalho("Sinistro","Registro e acompanhamento de ocorr\u00eancias"),BorderLayout.NORTH);
        for(TipoSinistro t:TipoSinistro.values())cmbTipo.addItem(t);cmbTipo.setRenderer(new DefaultListCellRenderer(){@Override public Component getListCellRendererComponent(JList<?> l,Object v,int i,boolean s,boolean f){super.getListCellRendererComponent(l,v,i,s,f);if(v instanceof TipoSinistro)setText(((TipoSinistro)v).getNome());return this;}});
        JPanel pagina=AutoHubUI.pagina();JPanel dados=AutoHubUI.card("Dados do sinistro","Registre a ocorr\u00eancia e seus detalhes");JPanel g=new JPanel(new GridLayout(3,2,14,14));g.setOpaque(false);g.add(AutoHubUI.campo("N\u00famero",txtNumero));g.add(AutoHubUI.campo("Placa do ve\u00edculo",txtPlaca));g.add(AutoHubUI.campo("Data/hora do sinistro",txtDataHoraSinistro));g.add(AutoHubUI.campo("Data/hora do registro",txtDataHoraRegistro));g.add(AutoHubUI.campo("Usu\u00e1rio do registro",txtUsuario));g.add(AutoHubUI.campo("Valor do sinistro",txtValor));dados.add(g,BorderLayout.CENTER);JPanel tipo=new JPanel(new BorderLayout(10,0));tipo.setOpaque(false);tipo.add(AutoHubUI.campo("Tipo de sinistro",cmbTipo),BorderLayout.CENTER);dados.add(tipo,BorderLayout.SOUTH);
        JLabel formatoInfo=new JLabel("Formato de data/hora: AAAA-MM-DDTHH:MM:SS");formatoInfo.setForeground(AutoHubUI.MUTED);formatoInfo.setFont(AutoHubUI.fonte(11,Font.PLAIN));
        JPanel centro=new JPanel(new BorderLayout(0,8));centro.setOpaque(false);centro.add(dados,BorderLayout.CENTER);centro.add(formatoInfo,BorderLayout.SOUTH);
        JButton incluir=AutoHubUI.botao("Incluir",AutoHubUI.BLUE),alterar=AutoHubUI.botao("Alterar",AutoHubUI.BLUE_DARK),buscar=AutoHubUI.botao("Buscar",new Color(71,85,105)),excluir=AutoHubUI.botao("Excluir",AutoHubUI.DANGER),limpar=AutoHubUI.botao("Limpar",new Color(100,116,139));incluir.addActionListener(e->salvar(false));alterar.addActionListener(e->salvar(true));buscar.addActionListener(e->buscar());excluir.addActionListener(e->excluir());limpar.addActionListener(e->limpar());pagina.add(centro,BorderLayout.CENTER);pagina.add(AutoHubUI.rodape(incluir,alterar,buscar,excluir,limpar),BorderLayout.SOUTH);add(pagina,BorderLayout.CENTER);
    }
    private Sinistro obterDados(){Veiculo v=veiculoDAO.buscar(txtPlaca.getText().trim());if(v==null)throw new IllegalArgumentException();LocalDateTime ds=LocalDateTime.parse(txtDataHoraSinistro.getText().trim(),formato);LocalDateTime dr=LocalDateTime.parse(txtDataHoraRegistro.getText().trim(),formato);BigDecimal valor=new BigDecimal(txtValor.getText().replace(",","."));Sinistro s=new Sinistro(v,ds,dr,txtUsuario.getText().trim(),valor,(TipoSinistro)cmbTipo.getSelectedItem());s.setNumero(txtNumero.getText().trim());return s;}
    private void salvar(boolean alterar){try{Sinistro s=obterDados();boolean ok=alterar?dao.alterar(s):dao.incluir(s);if(ok)AutoHubUI.sucesso(this,alterar?"Sinistro alterado com sucesso.":"Sinistro inclu\u00eddo com sucesso.");else AutoHubUI.erro(this,alterar?"Sinistro n\u00e3o encontrado.":"N\u00famero de sinistro j\u00e1 cadastrado.");}catch(Exception e){AutoHubUI.erro(this,"Verifique a placa, as datas e os valores informados.");}}
    private void buscar(){Sinistro s=dao.buscar(txtNumero.getText().trim());if(s==null){AutoHubUI.erro(this,"Sinistro n\u00e3o encontrado.");return;}txtPlaca.setText(s.getVeiculo()==null?"":s.getVeiculo().getPlaca());txtDataHoraSinistro.setText(s.getDataHoraSinistro().format(formato));txtDataHoraRegistro.setText(s.getDataHoraRegistro().format(formato));txtUsuario.setText(AutoHubUI.texto(s.getUsuarioRegistro()));txtValor.setText(s.getValorSinistro().toString());cmbTipo.setSelectedItem(s.getTipo());AutoHubUI.sucesso(this,"Dados carregados com sucesso.");}
    private void excluir(){if(dao.excluir(txtNumero.getText().trim())){AutoHubUI.sucesso(this,"Sinistro exclu\u00eddo com sucesso.");limpar();}else AutoHubUI.erro(this,"Sinistro n\u00e3o encontrado.");}
    private void limpar(){for(JTextField f:new JTextField[]{txtNumero,txtPlaca,txtDataHoraSinistro,txtDataHoraRegistro,txtUsuario,txtValor})f.setText("");cmbTipo.setSelectedIndex(0);txtNumero.requestFocus();}
    public static void main(String[] args){SwingUtilities.invokeLater(()->new TelaSinistro().setVisible(true));}
}
