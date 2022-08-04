package br.com.totemti.onboarding.controllers.dto.relatorio;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RelatorioMovimentacaoDto {

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate data;

    private String produto;

    private Integer qtd;

    private BigDecimal valor;

    private String cliente;

    private String vendedor;
}