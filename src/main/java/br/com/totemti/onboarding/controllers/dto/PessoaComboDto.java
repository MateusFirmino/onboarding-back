package br.com.totemti.onboarding.controllers.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PessoaComboDto {

    private Long id;

    private String nome;

    private String cpfCnpj;

}
