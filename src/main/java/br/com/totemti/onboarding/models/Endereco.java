package br.com.totemti.onboarding.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "pessoa_endereco")
public class Endereco {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="PESSOA_ENDERECO_SEQ_GENERATOR")
    @SequenceGenerator(name = "PESSOA_ENDERECO_SEQ_GENERATOR", sequenceName = "PESSOA_ENDERECO_SEQ", initialValue = 1, allocationSize = 1)
    @Column(name = "id_pessoa_endereco")
    private Long id;

    @Column(name = "pen_cep")
    private String cep;

    @Column(name = "pen_logradouro")
    private String logradouro;

    @Column(name = "pen_numero")
    private Integer numero;

    @Column(name = "pen_bairro")
    private String bairro;

    @Column(name = "pen_cidade")
    private String cidade;

    @Column(name = "pen_uf")
    private String uf;

    @Column(name = "pen_endereco_padrao")
    private String enderecoPadrao;

    @ManyToOne
    @JoinColumn(name = "id_pessoa")
    private Pessoa pessoa;
}
