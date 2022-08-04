package br.com.totemti.onboarding.controllers.dto;

import br.com.totemti.onboarding.enumerators.TipoPessoa;
import br.com.totemti.onboarding.enumerators.TipoSexo;
import br.com.totemti.onboarding.enumerators.TipoSituacao;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PessoaShowDto {

    private Long id;

    private String nome;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataNascimento;

    private String cpfCnpj;

    private TipoSexo sexo;

    private String inscricaoEstadual;

    private TipoPessoa tipoPessoa;

    private TipoSituacao situacao;

    private List<EmailShowDto> emails;

    private List<EnderecoShowDto> enderecos;

    private List<TelefoneShowDto> telefones;


}
