package br.edu.cs.poo.ac.seguro.mediators;

import br.edu.cs.poo.ac.seguro.daos.SeguradoEmpresaDAO;
import br.edu.cs.poo.ac.seguro.entidades.SeguradoEmpresa;

public class SeguradoEmpresaMediator {

    private static SeguradoEmpresaMediator instancia =
            new SeguradoEmpresaMediator();

    private SeguradoMediator seguradoMediator =
            SeguradoMediator.getInstancia();

    private SeguradoEmpresaDAO seguradoEmpresaDAO =
            new SeguradoEmpresaDAO();

    private SeguradoEmpresaMediator() {
    }

    public static SeguradoEmpresaMediator getInstancia() {
        return instancia;
    }

    public String validarCnpj(String cnpj) {
        if (StringUtils.ehNuloOuBranco(cnpj)) {
            return "CNPJ deve ser informado";
        }

        if (cnpj.length() != 14) {
            return "CNPJ deve ter 14 caracteres";
        }

        if (!ValidadorCpfCnpj.ehCnpjValido(cnpj)) {
            return "CNPJ com dígito inválido";
        }

        return null;
    }

    public String validarFaturamento(double faturamento) {
        if (faturamento <= 0) {
            return "Faturamento deve ser maior que zero";
        }

        return null;
    }

    public String incluirSeguradoEmpresa(SeguradoEmpresa seg) {
        String mensagem = validarSeguradoEmpresa(seg);

        if (mensagem != null) {
            return mensagem;
        }

        if (!seguradoEmpresaDAO.incluir(seg)) {
            return "CNPJ do segurado empresa já existente";
        }

        return null;
    }

    public String alterarSeguradoEmpresa(SeguradoEmpresa seg) {
        String mensagem = validarSeguradoEmpresa(seg);

        if (mensagem != null) {
            return mensagem;
        }

        if (!seguradoEmpresaDAO.alterar(seg)) {
            return "CNPJ do segurado empresa não existente";
        }

        return null;
    }

    public String excluirSeguradoEmpresa(String cnpj) {
        if (!seguradoEmpresaDAO.excluir(cnpj)) {
            return "CNPJ do segurado empresa não existente";
        }

        return null;
    }

    public SeguradoEmpresa buscarSeguradoEmpresa(String cnpj) {
        return seguradoEmpresaDAO.buscar(cnpj);
    }

    public String validarSeguradoEmpresa(SeguradoEmpresa seg) {

        String mensagem = seguradoMediator.validarNome(seg.getNome());

        if (mensagem != null) {
            return mensagem;
        }

        mensagem = seguradoMediator.validarEndereco(seg.getEndereco());

        if (mensagem != null) {
            return mensagem;
        }

        if (seg.getDataAbertura() == null) {
            return "Data da abertura deve ser informada";
        }

        mensagem = validarCnpj(seg.getCnpj());

        if (mensagem != null) {
            return mensagem;
        }

        mensagem = validarFaturamento(seg.getFaturamento());

        if (mensagem != null) {
            return mensagem;
        }

        return null;
    }
}