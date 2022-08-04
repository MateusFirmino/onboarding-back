package br.com.totemti.onboarding.controllers;

import br.com.totemti.onboarding.commons.ResponseBase;
import br.com.totemti.onboarding.controllers.dto.relatorio.RelatorioMovimentacaoDto;
import br.com.totemti.onboarding.services.ExportService;
import br.com.totemti.onboarding.services.VendaItemService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;

@AllArgsConstructor
@RestController
@RequestMapping("/vendas")
public class VendaItemController {
    private final VendaItemService vendaService;
    private final ExportService exportService;

    @GetMapping
    public ResponseEntity<ResponseBase<Page<RelatorioMovimentacaoDto>>> index(@RequestParam(value = "dataInicial", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate dataInicial,
                                                                              @RequestParam(value = "dataFinal", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate dataFinal,
                                                                              @RequestParam(value = "valorInicial", required = false) Integer valorInicial,
                                                                              @RequestParam(value = "valorFinal", required = false) Integer valorFinal,
                                                                              @RequestParam(value = "idProduto", required = false) Long idProduto,
                                                                              @RequestParam(value = "idPessoa", required = false) Long idPessoa,
                                                                              Pageable pageable) {
        final var movimentacaoDiaria = vendaService
                .geraRelatorio(dataInicial, dataFinal, valorInicial,
                        valorFinal, idProduto, idPessoa, pageable);
        return ResponseEntity.ok(ResponseBase.of(movimentacaoDiaria, true));
    }

    @GetMapping("/relatorio")
    public ResponseEntity<InputStreamResource> exportPessoaPDF(@RequestParam(value = "dataInicial", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate dataInicial,
                                                               @RequestParam(value = "dataFinal", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate dataFinal,
                                                               @RequestParam(value = "valorInicial", required = false) Integer valorInicial,
                                                               @RequestParam(value = "valorFinal", required = false) Integer valorFinal,
                                                               @RequestParam(value = "idProduto", required = false) Long idProduto,
                                                               @RequestParam(value = "idPessoa", required = false) Long idPessoa) {
        final var movimentacaoDiaria = vendaService
                .geraRelatorio(dataInicial, dataFinal, valorInicial,
                        valorFinal, idProduto, idPessoa, Pageable.unpaged());

        ByteArrayInputStream bais = exportService.vendasReportPDF(movimentacaoDiaria.getContent());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=relatoriovendas.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bais));
    }

}

