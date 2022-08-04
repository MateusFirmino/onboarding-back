package br.com.totemti.onboarding.controllers.dto.relatorio;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RelatorioProdutoDto {

    private String nome;

    private String categoria;

    private BigDecimal valor;


}
