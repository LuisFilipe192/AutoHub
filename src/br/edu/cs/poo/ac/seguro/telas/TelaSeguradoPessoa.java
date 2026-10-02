package br.edu.cs.poo.ac.seguro.telas;

import br.edu.cs.poo.ac.seguro.entidades.Endereco;
import br.edu.cs.poo.ac.seguro.entidades.SeguradoPessoa;
import br.edu.cs.poo.ac.seguro.mediators.SeguradoPessoaMediator;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public class TelaSeguradoPessoa extends JFrame {
    private final SeguradoPessoaMediator mediator = SeguradoPessoaMediator.getInstancia();
    private final JTextField txtCpf = AutoHubUI.campoTexto();
    private final JTextField txtNome = AutoHubUI.campoTexto();
    private final JTextField txtNascimento = AutoHubUI.campoTexto();
    private final JTextField txtRenda = AutoHubUI.campoTexto();
    private final JTextField txtLogradouro = AutoHubUI.campoTexto();
    private final JTextField txtCep = AutoHubUI.campoTexto();
    private final JTextField txtNumero = AutoHubUI.campoTexto();
    private final JTextField txtComplemento = AutoHubUI.campoTexto();
    private final JTextField txtPais = AutoHubUI.campoTexto();
    private final JTextField txtEstado = AutoHubUI.campoTexto();
    private final JTextField txtCidade = AutoHubUI.campoTexto();

    public TelaSeguradoPessoa() {
        super("AutoHub - Segurado Pessoa");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 720);
        setMinimumSize(new Dimension(850, 680));
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        add(AutoHubUI.cabecalho("Segurado Pessoa", "Cadastro e gerenciamento de clientes pessoa f\u00edsica"), BorderLayout.NORTH);

        JPanel pagina = AutoHubUI.pagina();
        JPanel dados = AutoHubUI.card("Dados pessoais", "Informe os dados principais do segurado");
        JPanel grid = new JPanel(new GridLayout(2, 2, 14, 14));
        grid.setOpaque(false);
        grid.add(AutoHubUI.campo("CPF", txtCpf));
        grid.add(AutoHubUI.campo("Nome completo", txtNome));
        grid.add(AutoHubUI.campo("Data de nascimento", txtNascimento));
        grid.add(AutoHubUI.campo("Renda mensal", txtRenda));
        dados.add(grid, BorderLayout.CENTER);

        JPanel endereco = AutoHubUI.card("Endere\u00e7o", "Localiza\u00e7\u00e3o residencial do segurado");
        JPanel e1 = new JPanel(new GridLayout(1, 3, 14, 0)); e1.setOpaque(false);
        e1.add(AutoHubUI.campo("Logradouro", txtLogradouro));
        e1.add(AutoHubUI.campo("CEP", txtCep));
        e1.add(AutoHubUI.campo("N\u00famero", txtNumero));
        JPanel e2 = new JPanel(new GridLayout(1, 3, 14, 0)); e2.setOpaque(false);
        e2.add(AutoHubUI.campo("Complemento", txtComplemento));
        e2.add(AutoHubUI.campo("Cidade", txtCidade));
        e2.add(AutoHubUI.campo("Estado", txtEstado));
        JPanel e3 = new JPanel(new GridLayout(1, 3, 14, 0)); e3.setOpaque(false);
        e3.add(AutoHubUI.campo("Pa\u00eds", txtPais));
        e3.add(new JPanel()); e3.add(new JPanel());
        e3.getComponent(1).setBackground(Color.WHITE); e3.getComponent(2).setBackground(Color.WHITE);
        endereco.add(e1, BorderLayout.CENTER);
        JPanel endBox = new JPanel(new GridLayout(3,1,0,12)); endBox.setOpaque(false);
        endBox.add(e1); endBox.add(e2); endBox.add(e3);
        endereco.add(endBox, BorderLayout.CENTER);

        JButton incluir = AutoHubUI.botao("Incluir", AutoHubUI.BLUE);
        JButton alterar = AutoHubUI.botao("Alterar", AutoHubUI.BLUE_DARK);
        JButton buscar = AutoHubUI.botao("Buscar", new Color(71, 85, 105));
        JButton excluir = AutoHubUI.botao("Excluir", AutoHubUI.DANGER);
        JButton limpar = AutoHubUI.botao("Limpar", new Color(100, 116, 139));
        incluir.addActionListener(e -> salvar(false));
        alterar.addActionListener(e -> salvar(true));
        buscar.addActionListener(e -> buscar());
        excluir.addActionListener(e -> excluir());
        limpar.addActionListener(e -> limpar());

        pagina.add(dados, BorderLayout.NORTH);
        pagina.add(endereco, BorderLayout.CENTER);
        pagina.add(AutoHubUI.rodape(incluir, alterar, buscar, excluir, limpar), BorderLayout.SOUTH);
        add(pagina, BorderLayout.CENTER);
    }

    private SeguradoPessoa obterDados() {
        Endereco endereco = new Endereco(txtLogradouro.getText(), txtCep.getText(), txtNumero.getText(), txtComplemento.getText(), txtPais.getText(), txtEstado.getText(), txtCidade.getText());
        LocalDate nascimento = LocalDate.parse(txtNascimento.getText());
        double renda = Double.parseDouble(txtRenda.getText().replace(",", "."));
        return new SeguradoPessoa(txtNome.getText(), endereco, nascimento, BigDecimal.ZERO, txtCpf.getText(), renda);
    }

    private void salvar(boolean alterar) {
        try {
            SeguradoPessoa seg = obterDados();
            String msg = alterar ? mediator.alterarSeguradoPessoa(seg) : mediator.incluirSeguradoPessoa(seg);
            if (msg != null) AutoHubUI.erro(this, msg); else AutoHubUI.sucesso(this, alterar ? "Segurado alterado com sucesso." : "Segurado inclu\u00eddo com sucesso.");
        } catch (Exception e) { AutoHubUI.erro(this, "Verifique os dados informados."); }
    }

    private void buscar() {
        SeguradoPessoa seg = mediator.buscarSeguradoPessoa(txtCpf.getText());
        if (seg == null) { AutoHubUI.erro(this, "Segurado n\u00e3o encontrado."); return; }
        preencher(seg); AutoHubUI.sucesso(this, "Dados carregados com sucesso.");
    }

    private void excluir() {
        String msg = mediator.excluirSeguradoPessoa(txtCpf.getText());
        if (msg != null) AutoHubUI.erro(this, msg); else { AutoHubUI.sucesso(this, "Segurado exclu\u00eddo com sucesso."); limpar(); }
    }

    private void preencher(SeguradoPessoa seg) {
        Endereco e = seg.getEndereco();
        txtNome.setText(AutoHubUI.texto(seg.getNome())); txtNascimento.setText(seg.getDataNascimento().toString()); txtRenda.setText(String.valueOf(seg.getRenda()));
        txtLogradouro.setText(AutoHubUI.texto(e.getLogradouro())); txtCep.setText(AutoHubUI.texto(e.getCep())); txtNumero.setText(AutoHubUI.texto(e.getNumero())); txtComplemento.setText(AutoHubUI.texto(e.getComplemento())); txtPais.setText(AutoHubUI.texto(e.getPais())); txtEstado.setText(AutoHubUI.texto(e.getEstado())); txtCidade.setText(AutoHubUI.texto(e.getCidade()));
    }

    private void limpar() { for (JTextField f : new JTextField[]{txtCpf,txtNome,txtNascimento,txtRenda,txtLogradouro,txtCep,txtNumero,txtComplemento,txtPais,txtEstado,txtCidade}) f.setText(""); txtCpf.requestFocus(); }

    public static void main(String[] args) { SwingUtilities.invokeLater(() -> new TelaSeguradoPessoa().setVisible(true)); }
}
