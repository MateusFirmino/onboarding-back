package br.com.totemti.onboarding.controllers.dto;

import br.com.totemti.onboarding.enumerators.TipoSexo;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CadastrarPessoaFisicaRequest extends CadastrarPessoaRequest {

    private TipoSexo sexo;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataNascimento;

}
