package br.com.totemti.onboarding.controllers;

import br.com.totemti.onboarding.commons.ResponseBase;
import br.com.totemti.onboarding.controllers.dto.*;
import br.com.totemti.onboarding.enumerators.TipoPessoa;
import br.com.totemti.onboarding.enumerators.TipoSituacao;
import br.com.totemti.onboarding.models.Email;
import br.com.totemti.onboarding.models.Endereco;
import br.com.totemti.onboarding.models.Pessoa;
import br.com.totemti.onboarding.models.Telefone;
import br.com.totemti.onboarding.services.*;
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
@RequestMapping(value = "/pessoas")
public class PessoaController {

    private final PessoaService pessoaService;

    private final PessoaEmailService pessoaEmailService;

    private final PessoaEnderecoService pessoaEnderecoService;

    private final PessoaTelefoneService pessoaTelefoneService;

    private final ExportService exportService;

    @PostMapping
    public ResponseEntity<ResponseBase<Pessoa>> create(@RequestBody CadastrarPessoaRequest request) {
        final var pessoa = pessoaService.create(request);
        final var responseBase = ResponseBase.of(pessoa, true);
        return ResponseEntity.ok(responseBase);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ResponseBase<Pessoa>> update(@PathVariable Long id, @RequestBody CadastrarPessoaRequest request) {
        final var pessoa = pessoaService.update(id, request);
        final var responseBase = ResponseBase.of(pessoa, true);
        return ResponseEntity.ok(responseBase);
    }

    @PutMapping(value = "/{id}/emails/{id}")
    public ResponseEntity<ResponseBase<Email>> updateEmail(@PathVariable Long id, @RequestBody CadastrarPessoaEmailDto request) {
        final var email = pessoaEmailService.update(id, request);
        final var responseBase = ResponseBase.of(email, true);
        return ResponseEntity.ok(responseBase);
    }

    @PutMapping(value = "/{id}/enderecos/{id}")
    public ResponseEntity<ResponseBase<Endereco>> updateEndereco(@PathVariable Long id, @RequestBody CadastrarPessoaEnderecoDto request) {
        final var endereco = pessoaEnderecoService.update(id, request);
        final var responseBase = ResponseBase.of(endereco, true);
        return ResponseEntity.ok(responseBase);
    }

    @PutMapping(value = "/{id}/telefones/{id}")
    public ResponseEntity<ResponseBase<Telefone>> updateTelefone(@PathVariable Long id, @RequestBody CadastrarPessoaTelefoneDto request) {
        final var telefone = pessoaTelefoneService.update(id, request);
        final var responseBase = ResponseBase.of(telefone, true);
        return ResponseEntity.ok(responseBase);
    }

    @GetMapping(value = "/nome")
    public ResponseEntity<List<PessoaComboDto>> findByNome(@RequestParam(required = false) String nome) {
        final var obj = pessoaService.findByNome(nome);
        return ResponseEntity.ok().body(obj);
    }


    @GetMapping(value = "/{id}")
    public ResponseEntity<PessoaShowDto> findbyId(@PathVariable Long id) {
        var obj = pessoaService.findByid(id);
        return ResponseEntity.ok().body(obj);
    }

    @GetMapping(value = "/{id}/emails")
    public ResponseEntity<Page<EmailShowDto>> findEmailByPerson(@PathVariable Long id, Pageable pageable) {
        var obj = pessoaEmailService.findEmailByPerson(id,pageable);
        return ResponseEntity.ok().body(obj);
    }

    @GetMapping(value = "/{id}/telefones")
    public ResponseEntity<Page<TelefoneShowDto>> findTelefoneByPerson(@PathVariable Long id, Pageable pageable){
        var obj = pessoaTelefoneService.findTelefoneByPerson(id,pageable);
        return ResponseEntity.ok().body(obj);
    }

    @GetMapping(value = "/{id}/enderecos")
    public ResponseEntity<Page<EnderecoShowDto>> findEnderecoByPerson(@PathVariable Long id, Pageable pageable){
        var obj = pessoaEnderecoService.findEnderecoByPerson(id,pageable);
        return ResponseEntity.ok().body(obj);
    }

    @GetMapping
    public ResponseEntity<ResponseBase<Page<Pessoa>>> index(
            @RequestParam(value = "tipoPessoa", required = false) TipoPessoa tipoPessoa,
            @RequestParam(value = "nome", required = false, defaultValue = "") String nome,
            @RequestParam(value = "situacao", required = false) TipoSituacao situacao,
            Pageable pageable) {

        final var pessoas = pessoaService.findAll(tipoPessoa, nome, situacao,pageable);
        final var responseBase = ResponseBase.of(pessoas, true);
        return ResponseEntity.ok(responseBase);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<ResponseBase<Void>> delete(@PathVariable Long id) {
        pessoaService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{id}/emails/{id}")
    public ResponseEntity<ResponseBase<Void>> deleteEmails(@PathVariable Long id) {
        pessoaEmailService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{id}/enderecos/{id}")
    public ResponseEntity<ResponseBase<Void>> deleteEndereco(@PathVariable Long id) {
        pessoaEnderecoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{id}/telefones/{id}")
    public ResponseEntity<ResponseBase<Void>> deleteTefelone(@PathVariable Long id) {
        pessoaTelefoneService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/relatorio")
    public ResponseEntity<InputStreamResource> exportPessoaPDF(@RequestParam(value = "tipoPessoa", required = false) TipoPessoa tipoPessoa,
                                                               @RequestParam(value = "nome", required = false) String nome) {
        final var pessoas = pessoaService.lista(tipoPessoa, nome);

        ByteArrayInputStream bais = exportService.pessoaPDFReport(pessoas);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=relatorioclientes.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bais));
    }

}
