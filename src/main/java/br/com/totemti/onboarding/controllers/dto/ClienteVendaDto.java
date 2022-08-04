package br.com.totemti.onboarding.controllers.dto;

import br.com.totemti.onboarding.enumerators.TipoSituacaoVenda;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClienteVendaDto {

    private Long id;

    @NotNull
    private PessoaShowDto pessoa;
    @NotNull
    private String usuarioVenda;
    @NotNull
    private BigDecimal valorTotal;
    @NotNull
    private BigDecimal descontoVenda;
    @NotNull
    private TipoSituacaoVenda situacao;

    private String observacao;

    @NotNull
    private List<VendaItemDto> vendaItem;
}
