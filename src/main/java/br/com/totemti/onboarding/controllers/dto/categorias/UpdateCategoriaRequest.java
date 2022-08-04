package br.com.totemti.onboarding.controllers.dto.categorias;

import br.com.totemti.onboarding.enumerators.TipoSituacao;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCategoriaRequest {

    private Long id;

    private String nome;

    private TipoSituacao situacao;

}
