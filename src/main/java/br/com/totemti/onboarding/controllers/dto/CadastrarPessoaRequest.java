package br.com.totemti.onboarding.controllers.dto;

import br.com.totemti.onboarding.enumerators.TipoPessoa;
import br.com.totemti.onboarding.enumerators.TipoSituacao;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "tipoPessoa",visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = CadastrarPessoaFisicaRequest.class,name = "FISICA"),
        @JsonSubTypes.Type(value = CadastrarPessoaJuridicaRequest.class,name = "JURIDICA")
})
@Getter
@Setter
public class CadastrarPessoaRequest {

    private String nome;

    private String cpfCnpj;

    private TipoSituacao situacao;

    private TipoPessoa tipoPessoa;

    private List<CadastrarPessoaEmailDto> emails;

    private List<CadastrarPessoaTelefoneDto> telefones;

    private List<CadastrarPessoaEnderecoDto> enderecos;
}
