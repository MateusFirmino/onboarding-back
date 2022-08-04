package br.com.totemti.onboarding.controllers.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CadastrarPessoaEnderecoDto {

    private String cep;

    private String logradouro;

    private Integer numero;

    private String bairro;

    private String cidade;

    private String uf;

    private String enderecoPadrao;
}
