package br.com.totemti.onboarding.models;

import br.com.totemti.onboarding.enumerators.TipoSituacao;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@AllArgsConstructor
@Table(name = "produto_categoria")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRODUTO_CATEGORIA_SEQ_GENERATOR")
//    @SequenceGenerator(name = "PRODUTO_CATEGORIA_SEQ_GENERATOR", sequenceName = "PRODUTO_CATEGORIA_SEQ", allocationSize = 1)
    @Column(name = "id_produto_categoria")
    private Long id;

    @Column(name = "prc_nome")
    private String nome;

    @Convert(converter = TipoSituacao.Mapper.class)
    @Column(name = "prc_situacao")
    private TipoSituacao situacao;

    @JsonIgnore
    @OneToMany (mappedBy = "categoria")
   // @JoinColumn(name = "ID_PRODUTO", foreignKey = @ForeignKey(name = "PRODUTO_CATEGORIA_FK"))
    private List<Produto> produtos;
}