package br.com.totemti.onboarding.controllers.dto;

import br.com.totemti.onboarding.models.VendaItem;
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
public class CadastrarVendaItemDto {

    private Long id;

    @NotNull
    private Long idProduto;

    private Integer quantidade;

    private BigDecimal valor;

    private BigDecimal desconto;

    private BigDecimal subtotal;

    public static CadastrarVendaItemDto of(VendaItem vendaItem) {
        return new CadastrarVendaItemDto(
                vendaItem.getId(),
                vendaItem.getProduto().getId(),
                vendaItem.getQuantidade(),
                vendaItem.getValor(),
                vendaItem.getDesconto(),
                vendaItem.getSubtotal()
        );
    }
}
