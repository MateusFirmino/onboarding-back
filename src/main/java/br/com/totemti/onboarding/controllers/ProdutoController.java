package br.com.totemti.onboarding.controllers;


import br.com.totemti.onboarding.commons.ResponseBase;
import br.com.totemti.onboarding.controllers.dto.CadastarProdutoDto;
import br.com.totemti.onboarding.controllers.dto.ProdutoShowDto;
import br.com.totemti.onboarding.controllers.dto.relatorio.RelatorioProdutoDto;
import br.com.totemti.onboarding.enumerators.TipoSituacao;
import br.com.totemti.onboarding.models.Produto;
import br.com.totemti.onboarding.services.ExportService;
import br.com.totemti.onboarding.services.ProdutoService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;
    private final ExportService exportService;

    @PostMapping
    public ResponseEntity<ResponseBase<Produto>> create(@RequestBody CadastarProdutoDto request) {
        final var produto = produtoService.create(request);
        final var responseBase = ResponseBase.of(produto, true);
        return ResponseEntity.ok(responseBase);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ResponseBase<Produto>> update(@PathVariable Long id,@RequestBody CadastarProdutoDto request) {
        final var produto = produtoService.update(id, request);
        final var responseBase = ResponseBase.of(produto, true);
        return ResponseEntity.ok(responseBase);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Produto> findById(@PathVariable Long id) {
        Produto obj = produtoService.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @GetMapping(value = "/nome")
    public ResponseEntity<List<ProdutoShowDto>> findByNome(@RequestParam(required = false) String nome) {
        final var obj = produtoService.findByNome(nome);
        return ResponseEntity.ok().body(obj);
    }

    @GetMapping
    public ResponseEntity<ResponseBase<Page<Produto>>> index(
            @RequestParam(value = "categoria", required = false) Long idCategoria,
            @RequestParam(value = "nome", required = false, defaultValue = "") String nome,
            @RequestParam(value = "situacao", required = false) TipoSituacao situacao,
            Pageable pageable) {

        final var produtos = produtoService.findAll(idCategoria, nome, situacao, pageable);
        final var responseBase = ResponseBase.of(produtos, true);
        return ResponseEntity.ok(responseBase);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<ResponseBase<Void>> delete(@PathVariable Long id) {
        produtoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/relatorio")
    public ResponseEntity<InputStreamResource> exportProdutoPDF(@RequestParam(name = "idCategoria", required = false) Long idCategoria,
                                                                @RequestParam(name = "nome", required = false) String nome,
                                                                @RequestParam(name = "situacao", required = false) TipoSituacao situacao) {
        List<RelatorioProdutoDto> produtos = produtoService.geraRelatorio(idCategoria, nome, situacao);

        ByteArrayInputStream bais = exportService.produtoPDFReport(produtos);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=relatorioprodutos.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bais));
    }


}

