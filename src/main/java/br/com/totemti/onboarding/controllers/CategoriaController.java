package br.com.totemti.onboarding.controllers;


import br.com.totemti.onboarding.commons.ResponseBase;
import br.com.totemti.onboarding.controllers.dto.categorias.CategoriaComboDto;
import br.com.totemti.onboarding.controllers.dto.categorias.CreateCategoriaRequest;
import br.com.totemti.onboarding.controllers.dto.categorias.ShowCategoriaResponse;
import br.com.totemti.onboarding.controllers.dto.categorias.UpdateCategoriaRequest;
import br.com.totemti.onboarding.enumerators.TipoSituacao;
import br.com.totemti.onboarding.models.Categoria;
import br.com.totemti.onboarding.services.CategoriaService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    @PostMapping
    public ResponseEntity<ResponseBase<Categoria>> create(@RequestBody CreateCategoriaRequest request) {
        final var categoria = categoriaService.create(request);
        final var responseBase = ResponseBase.of(categoria, true);
        return ResponseEntity.ok(responseBase);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ResponseBase<Categoria>> update(@PathVariable Long id, @RequestBody UpdateCategoriaRequest request) {
        final var categoria = categoriaService.update(id, request);
        final var responseBase = ResponseBase.of(categoria, true);
        return ResponseEntity.ok(responseBase);
    }


    @GetMapping(value = "/{id}")
    public ResponseEntity<Categoria> findbyId(@PathVariable Long id) {
        Categoria obj = categoriaService.findByid(id);
        return ResponseEntity.ok().body(obj);
    }

    @GetMapping(value = "/combo")
    public ResponseEntity<List<CategoriaComboDto>> findAllCategoriaComboDto() {
        final var obj = categoriaService.findAllCategoriaComboDto();
        return ResponseEntity.ok().body(obj);
    }

    @GetMapping
    public ResponseEntity<ResponseBase<Page<ShowCategoriaResponse>>> index(
            @RequestParam(value = "nome", required = false, defaultValue = "") String nome,
            @RequestParam(value = "situacao", required = false) TipoSituacao situacao,
            Pageable pageable) {

        Page<ShowCategoriaResponse> list = categoriaService.findAll(nome, situacao, pageable);
        final var responseBase = ResponseBase.of(list, true);
        return ResponseEntity.ok(responseBase);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<ResponseBase<Void>> delete(@PathVariable Long id) {
        categoriaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

