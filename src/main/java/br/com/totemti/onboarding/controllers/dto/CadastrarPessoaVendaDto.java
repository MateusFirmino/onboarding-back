package br.com.totemti.onboarding.controllers.dto;

import br.com.totemti.onboarding.enumerators.TipoSituacaoVenda;
import br.com.totemti.onboarding.models.PessoaVenda;
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
public class CadastrarPessoaVendaDto {

    private Long id;

    private Long idPessoa;
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
    private List<CadastrarVendaItemDto> itens;

    public static CadastrarPessoaVendaDto of(PessoaVenda pessoaVenda) {
        return new CadastrarPessoaVendaDto(
                pessoaVenda.getId(),
                pessoaVenda.getPessoa().getId(),
                pessoaVenda.getUsuarioVenda(),
                pessoaVenda.getValorTotal(),
                pessoaVenda.getDescontoVenda(),
                pessoaVenda.getSituacao(),
                pessoaVenda.getObservacao(),
                pessoaVenda.getVendaItemAsDto()
        );
    }
}
