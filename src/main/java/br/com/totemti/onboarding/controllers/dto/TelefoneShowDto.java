package br.com.totemti.onboarding.controllers.dto;

import br.com.totemti.onboarding.enumerators.TipoTelefone;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TelefoneShowDto {

    private Long id;

    private String telefone;

    private TipoTelefone tipoTelefone;

    private String telefonePadrao;

}
