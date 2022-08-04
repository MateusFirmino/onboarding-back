package br.com.totemti.onboarding.controllers.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CadastrarPessoaJuridicaRequest extends CadastrarPessoaRequest {

    private String inscricaoEstadual;

}
