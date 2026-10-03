package br.edu.cs.poo.ac.seguro.mediators;

import java.math.BigDecimal;
import java.time.LocalDate;

import br.edu.cs.poo.ac.seguro.entidades.Endereco;

public class SeguradoMediator {

    private static SeguradoMediator instancia = new SeguradoMediator();

    private SeguradoMediator() {
    }

    public static SeguradoMediator getInstancia() {
        return instancia;
    }

    public String validarNome(String nome) {
        if (StringUtils.ehNuloOuBranco(nome)) {
            return "Nome deve ser informado";
        }

        if (nome.length() > 100) {
        	return "Tamanho do nome deve ser no m\u00e1ximo 100 caracteres";
        }

        return null;
    }

    public String validarEndereco(Endereco endereco) {
        if (endereco == null) {
        	return "Endere\u00e7o deve ser informado";
        }

        if (StringUtils.ehNuloOuBranco(endereco.getLogradouro())) {
            return "Logradouro deve ser informado";
        }

        if (StringUtils.ehNuloOuBranco(endereco.getCep())) {
            return "CEP deve ser informado";
        }

        if (endereco.getCep().length() != 8) {
            return "Tamanho do CEP deve ser 8 caracteres";
        }

        if (!StringUtils.temSomenteNumeros(endereco.getCep())) {
            return "CEP deve ter formato NNNNNNNN";
        }

        if (StringUtils.ehNuloOuBranco(endereco.getCidade())) {
            return "Cidade deve ser informada";
        }

        if (endereco.getCidade().length() > 100) {
            return "Tamanho da cidade deve ser no m\u00e1ximo 100 caracteres";
        }

        if (StringUtils.ehNuloOuBranco(endereco.getEstado())) {
            return "Sigla do estado deve ser informada";
        }

        if (endereco.getEstado().length() != 2) {
            return "Tamanho da sigla do estado deve ser 2 caracteres";
        }

        if (StringUtils.ehNuloOuBranco(endereco.getPais())) {
        	return "Pa\u00eds deve ser informado";
        }

        if (endereco.getPais().length() > 40) {
        	return "Tamanho do pa\u00eds deve ser no m\u00e1ximo 40 caracteres";
        }

        if (endereco.getNumero() != null && endereco.getNumero().length() > 20) {
        	return "Tamanho do n\u00famero deve ser no m\u00e1ximo 20 caracteres";
        }

        if (endereco.getComplemento() != null && endereco.getComplemento().length() > 30) {
        	return "Tamanho do complemento deve ser no m\u00e1ximo 30 caracteres";
        }

        return null;
    }

    public String validarDataCriacao(LocalDate dataCriacao) {
        if (dataCriacao == null) {
        	return "Data da cria\u00e7\u00e3o deve ser informada";
        }

        if (dataCriacao.isAfter(LocalDate.now())) {
        	return "Data da cria\u00e7\u00e3o deve ser menor ou igual \u00e0 data atual";
        }

        return null;
    }

    public BigDecimal ajustarDebitoBonus(BigDecimal bonus, BigDecimal valorDebito) {
        if (bonus.compareTo(valorDebito) < 0) {
            return bonus;
        }

        return valorDebito;
    }
}