package br.edu.cs.poo.ac.seguro.mediators;

import br.edu.cs.poo.ac.seguro.daos.SeguradoPessoaDAO;
import br.edu.cs.poo.ac.seguro.entidades.SeguradoPessoa;

public class SeguradoPessoaMediator {

    private static SeguradoPessoaMediator instancia = new SeguradoPessoaMediator();

    private SeguradoMediator seguradoMediator = SeguradoMediator.getInstancia();

    private SeguradoPessoaDAO seguradoPessoaDAO = new SeguradoPessoaDAO();

    private SeguradoPessoaMediator() {
    }

    public static SeguradoPessoaMediator getInstancia() {
        return instancia;
    }

    public String validarCpf(String cpf) {
        if (StringUtils.ehNuloOuBranco(cpf)) {
            return "CPF deve ser informado";
        }

        if (cpf.length() != 11) {
            return "CPF deve ter 11 caracteres";
        }

        if (!ValidadorCpfCnpj.ehCpfValido(cpf)) {
            return "CPF com dígito inválido";
        }

        return null;
    }

    public String validarRenda(double renda) {
        if (renda < 0) {
            return "Renda deve ser maior ou igual à zero";
        }

        return null;
    }

    public String incluirSeguradoPessoa(SeguradoPessoa seg) {
        String mensagem = validarSeguradoPessoa(seg);

        if (mensagem != null) {
            return mensagem;
        }

        if (!seguradoPessoaDAO.incluir(seg)) {
            return "CPF do segurado pessoa já existente";
        }

        return null;
    }

    public String alterarSeguradoPessoa(SeguradoPessoa seg) {
        String mensagem = validarSeguradoPessoa(seg);

        if (mensagem != null) {
            return mensagem;
        }

        if (!seguradoPessoaDAO.alterar(seg)) {
            return "CPF do segurado pessoa não existente";
        }

        return null;
    }

    public String excluirSeguradoPessoa(String cpf) {
        if (!seguradoPessoaDAO.excluir(cpf)) {
            return "CPF do segurado pessoa não existente";
        }

        return null;
    }

    public SeguradoPessoa buscarSeguradoPessoa(String cpf) {
        return seguradoPessoaDAO.buscar(cpf);
    }

    public String validarSeguradoPessoa(SeguradoPessoa seg) {

        String mensagem = seguradoMediator.validarNome(seg.getNome());

        if (mensagem != null) {
            return mensagem;
        }

        mensagem = seguradoMediator.validarEndereco(seg.getEndereco());

        if (mensagem != null) {
            return mensagem;
        }

        if (seg.getDataNascimento() == null) {
            return "Data do nascimento deve ser informada";
        }

        mensagem = validarCpf(seg.getCpf());

        if (mensagem != null) {
            return mensagem;
        }

        mensagem = validarRenda(seg.getRenda());

        if (mensagem != null) {
            return mensagem;
        }

        return null;
    }
}