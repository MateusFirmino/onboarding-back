package br.com.totemti.onboarding.controllers.dto;

import br.com.totemti.onboarding.controllers.dto.categorias.CreateCategoriaRequest;
import br.com.totemti.onboarding.enumerators.TipoProduto;
import br.com.totemti.onboarding.enumerators.TipoSituacao;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CadastarProdutoDto {

    private String nome;

    private TipoProduto tipoProduto;

    private Integer qtde;

    private BigDecimal valor;

    private BigDecimal valorDesconto;

    private String usuarioCadastro;

    private TipoSituacao situacao;

    private CreateCategoriaRequest categoria;

}
