package br.com.totemti.onboarding.controllers.dto;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VendaItemDto {

    private Long id;

    @NotNull
    private CadastarProdutoDto produto;

    private Integer quantidade;

    private BigDecimal valor;

    private BigDecimal desconto;

    private BigDecimal subtotal;

}
