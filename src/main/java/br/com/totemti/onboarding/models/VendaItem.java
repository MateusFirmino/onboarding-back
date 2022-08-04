package br.com.totemti.onboarding.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "venda_item")
public class VendaItem {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ITEM_VENDA_SEQ_GENERATOR")
    @SequenceGenerator(name = "ITEM_VENDA_SEQ_GENERATOR",
            sequenceName = "VENDA_ITEM_SEQ",
            allocationSize = 1)
    @Column(name = "id_venda_item")
    private Long id;

    @Column(name = "vei_qtde")
    private Integer quantidade;

    @Column(name = "vei_valor")
    private BigDecimal valor;

    @Column(name = "vei_desconto")
    private BigDecimal desconto;

    @Column(name = "vei_sub_total")
    private BigDecimal subtotal;

    @ManyToOne
    @JoinColumn(name = "id_produto",
            foreignKey = @ForeignKey(name = "VENDA_ITEM_PRODUTO_FK"))
    private Produto produto;

    @ManyToOne
    @JoinColumn(name = "id_cliente_venda",
            foreignKey = @ForeignKey(name = "CLIENTE_VENDA_ITEM_FK"))
    private PessoaVenda pessoaVenda;
}