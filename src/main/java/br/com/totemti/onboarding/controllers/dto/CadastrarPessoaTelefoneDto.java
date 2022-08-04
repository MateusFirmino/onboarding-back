package br.com.totemti.onboarding.controllers.dto;

import br.com.totemti.onboarding.enumerators.TipoTelefone;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CadastrarPessoaTelefoneDto {

    private String telefone;

    private TipoTelefone tipoTelefone;

    private String telefonePadrao;
}
