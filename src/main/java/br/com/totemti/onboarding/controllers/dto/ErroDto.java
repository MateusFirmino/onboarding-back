package br.com.totemti.onboarding.controllers.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ErroDto {

    private String erro;
    private String propriedade;

    public ErroDto(String erro) {
        this.erro = erro;
    }
}
