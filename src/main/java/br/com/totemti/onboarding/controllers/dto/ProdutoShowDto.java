package br.com.totemti.onboarding.controllers.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoShowDto {
    private Long id;

    private String nome;

    private Integer qtde;

    private BigDecimal valor;

    private BigDecimal valorDesconto;

}
