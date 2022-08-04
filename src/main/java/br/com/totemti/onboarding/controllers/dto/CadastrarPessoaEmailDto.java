package br.com.totemti.onboarding.controllers.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CadastrarPessoaEmailDto {

    private String email;

    private String emailPadrao;

}
