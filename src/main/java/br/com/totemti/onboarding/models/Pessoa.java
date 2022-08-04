package br.com.totemti.onboarding.models;

import br.com.totemti.onboarding.enumerators.TipoPessoa;
import br.com.totemti.onboarding.enumerators.TipoSexo;
import br.com.totemti.onboarding.enumerators.TipoSituacao;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;


@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "pessoa")
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="PESSOA_SEQ_GENERATOR")
    @SequenceGenerator(name = "PESSOA_SEQ_GENERATOR", sequenceName = "PESSOA_SEQ", allocationSize = 1)
    @Column(name = "id_pessoa")
    private Long id;

    @Column(name = "pes_nome")
    private String nome;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @Column(name = "pes_nascimento")
    private LocalDate dataNascimento;

    @Column(name = "pes_cpf_cnpj")
    private String cpfCnpj;

    @Convert(converter = TipoSexo.Mapper.class)
    @Column(name = "pes_sexo")
    private TipoSexo sexo;

    @Column(name = "pes_inscricao_estadual")
    private String inscricaoEstadual;

    @Convert(converter = TipoSituacao.Mapper.class)
    @Column(name = "pes_ativo")
    private TipoSituacao situacao;

    @Convert(converter = TipoPessoa.Mapper.class)
    @Column(name = "pes_tipo")
    private TipoPessoa tipoPessoa;

    @JsonIgnore
    @OneToMany(mappedBy = "pessoa")
    private Set<Telefone> telefones;

    @JsonIgnore
    @OneToMany(mappedBy = "pessoa")
    private Set<Endereco> enderecos;

    @JsonIgnore
    @OneToMany(mappedBy = "pessoa")
    private Set<Email> emails;
}
