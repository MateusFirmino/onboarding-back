package br.com.totemti.onboarding.controllers.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EmailShowDto {

    private Long id;

    private String email;

    private String emailPadrao;

}
