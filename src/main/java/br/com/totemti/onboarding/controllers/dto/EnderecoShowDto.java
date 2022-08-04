package br.com.totemti.onboarding.controllers.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EnderecoShowDto {

    private Long id;

    private String cep;

    private String logradouro;

    private Integer numero;

    private String bairro;

    private String cidade;

    private String uf;

    private String enderecoPadrao;

}
