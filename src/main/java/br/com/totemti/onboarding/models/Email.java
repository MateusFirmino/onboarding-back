package br.com.totemti.onboarding.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "pessoa_email")
public class Email {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="PESSOA_EMAIL_SEQ_GENERATOR")
    @SequenceGenerator(name = "PESSOA_EMAIL_SEQ_GENERATOR", sequenceName = "PESSOA_EMAIL_SEQ", initialValue = 1, allocationSize = 1)
    @Column(name = "id_pessoa_email")
    private Long id;

    @Column(name = "pee_email")
    private String email;

    @Column(name = "pee_email_padrao")
    private String emailPadrao;

    @ManyToOne
    @JoinColumn(name = "ID_PESSOA", foreignKey = @ForeignKey(name = "PESSOA_EMAIL_FK"))
    private Pessoa pessoa;
}
