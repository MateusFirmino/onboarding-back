package br.com.totemti.onboarding.models;

import br.com.totemti.onboarding.enumerators.TipoProduto;
import br.com.totemti.onboarding.enumerators.TipoSituacao;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;


@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "produto")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRODUTO_SEQ_GENERATOR")
    @SequenceGenerator(name = "PRODUTO_SEQ_GENERATOR", sequenceName = "PRODUTO_SEQ", allocationSize = 1)
    @Column(name = "id_produto")
    private Long id;

    @Column(name = "pro_nome")
    private String nome;

    @Convert(converter = TipoProduto.Mapper.class)
    @Column(name = "pro_tipo")
    private TipoProduto tipoProduto;

    @Column(name = "pro_qtde")
    private Integer qtde;

    @Column(name = "pro_valor")
    private BigDecimal valor;

    @Column(name = "pro_valor_desconto")
    private BigDecimal valorDesconto;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @Column(name = "pro_data_cadastro")
    private LocalDate dataCadastro;

    @Column(name = "pro_usuario_cadastro")
    private String usuarioCadastro;

    @Convert(converter = TipoSituacao.Mapper.class)
    @Column(name = "pro_situacao")
    private TipoSituacao situacao;

    @ManyToOne
    @JoinColumn(name = "ID_PRODUTO_CATEGORIA", foreignKey = @ForeignKey(name = "FK_PRODUTO_CATEGORIA"))
    private Categoria categoria;

    @PreUpdate
    @PrePersist
    public void setup() {
        if (Objects.isNull(this.dataCadastro)) {
            this.dataCadastro = LocalDate.now();
        }
    }
}
