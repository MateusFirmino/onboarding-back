package br.com.totemti.onboarding.controllers;

import br.com.totemti.onboarding.commons.ResponseBase;
import br.com.totemti.onboarding.controllers.dto.CadastrarPessoaVendaDto;
import br.com.totemti.onboarding.controllers.dto.ClienteVendaDto;
import br.com.totemti.onboarding.services.ClienteVendaService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

@AllArgsConstructor
@RestController
@RequestMapping("/clientevenda")
public class PessoaVendaController {
    private final ClienteVendaService clienteVendaService;

    @GetMapping
    public ResponseEntity<ResponseBase<Page<CadastrarPessoaVendaDto>>> index(Pageable pageable) {
        final var list = clienteVendaService.listar(pageable);
        final var responseBase = ResponseBase.of(list, true);
        return ResponseEntity.ok(responseBase);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseBase<CadastrarPessoaVendaDto>> findById(@PathVariable Long id) {
        final var clienteVendaDto = clienteVendaService.findById(id);
        final var responseBase = ResponseBase.of(clienteVendaDto, true);
        return ResponseEntity.ok(responseBase);
    }

    @Transactional
    @PostMapping
    public ResponseEntity<ResponseBase<ClienteVendaDto>> create(@RequestBody @Validated CadastrarPessoaVendaDto cadastrarPessoaVendaDto) {
        final var novoClienteVendaDto = clienteVendaService.salvar(cadastrarPessoaVendaDto);
        final var responseBase = ResponseBase.of(novoClienteVendaDto, true);
        return ResponseEntity.ok(responseBase);
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        clienteVendaService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
