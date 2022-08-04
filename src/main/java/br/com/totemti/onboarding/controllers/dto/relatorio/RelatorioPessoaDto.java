package br.com.totemti.onboarding.controllers.dto.relatorio;

import br.com.totemti.onboarding.enumerators.TipoPessoa;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RelatorioPessoaDto {

    private String nome;

    private TipoPessoa tipoPessoa;

    private String municipio;

    private String contato;

    private String email;


}
