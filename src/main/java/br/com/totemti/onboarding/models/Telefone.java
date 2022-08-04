package br.com.totemti.onboarding.models;

import br.com.totemti.onboarding.enumerators.TipoTelefone;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "pessoa_telefone")
public class Telefone {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="PESSOA_TELEFONE_SEQ_GENERATOR")
    @SequenceGenerator(name = "PESSOA_TELEFONE_SEQ_GENERATOR", sequenceName = "PESSOA_TELEFONE_SEQ", allocationSize = 1)
    @Column(name = "id_pessoa_telefone")
    private Long id;

    @Column(name = "pet_telefone")
    private String telefone;

    @Convert(converter = TipoTelefone.Mapper.class)
    @Column(name = "pet_tipo_telefone")
    private TipoTelefone tipoTelefone;

    @Column(name = "pet_telefone_padrao")
    private String telefonePadrao;

    @ManyToOne
    @JoinColumn(name = "ID_PESSOA", foreignKey = @ForeignKey(name = "PESSOA_TELEFONE_FK"))
    private Pessoa pessoa;

}
