package br.com.totemti.onboarding.models;

import br.com.totemti.onboarding.controllers.dto.CadastrarVendaItemDto;
import br.com.totemti.onboarding.enumerators.TipoSituacaoVenda;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "cliente_venda")
public class PessoaVenda {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CLIENTE_VENDA_SEQ_GENERATOR")
    @SequenceGenerator(name = "CLIENTE_VENDA_SEQ_GENERATOR",
            sequenceName = "CLIENTE_VENDA_SEQ",
            allocationSize = 1)

    @Column(name = "id_cliente_venda")
    private Long id;

    @Column(name = "clv_data_venda")
    private LocalDate dataVenda;

    @Column(name = "clv_usuario_venda")
    private String usuarioVenda;

    @Column(name = "clv_valor_total")
    private BigDecimal valorTotal;

    @Column(name = "clv_desconto_venda")
    private BigDecimal descontoVenda;

    @Column(name = "clv_situacao")
    private TipoSituacaoVenda situacao;

    @Column(name = "CLV_OBSERVACAO")
    private String observacao;

    @ManyToOne
    @JoinColumn(name = "id_pessoa",
            foreignKey = @ForeignKey(name = "CLIENTE_VENDA_PESSOA_FK"))
    private Pessoa pessoa;

    @OneToMany(mappedBy = "pessoaVenda")
    private Set<VendaItem> vendaItem;

    @PrePersist
    public void setup() {
        this.dataVenda = LocalDate.now();
    }

    @Transient
    public List<CadastrarVendaItemDto> getVendaItemAsDto() {
        return this.getVendaItem()
                .stream()
                .map(CadastrarVendaItemDto::of)
                .collect(Collectors.toList());
    }
}
