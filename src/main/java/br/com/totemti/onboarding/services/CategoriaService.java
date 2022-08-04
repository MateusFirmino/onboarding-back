package br.com.totemti.onboarding.services;


import br.com.totemti.onboarding.controllers.dto.categorias.CategoriaComboDto;
import br.com.totemti.onboarding.controllers.dto.categorias.CreateCategoriaRequest;
import br.com.totemti.onboarding.controllers.dto.categorias.ShowCategoriaResponse;
import br.com.totemti.onboarding.controllers.dto.categorias.UpdateCategoriaRequest;
import br.com.totemti.onboarding.enumerators.TipoSituacao;
import br.com.totemti.onboarding.exceptions.RegistroNaoEncontradoException;
import br.com.totemti.onboarding.exceptions.RegraDeNegocioException;
import br.com.totemti.onboarding.models.Categoria;
import br.com.totemti.onboarding.repositories.CategoriaRepository;
import br.com.totemti.onboarding.services.exceptions.DataIntegrityViolationException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    private final ModelMapper modelMapper;

    public Categoria findByid(Long id) {
        Optional<Categoria> obj = categoriaRepository.findById(id);
        return obj.orElseThrow(() -> new RegistroNaoEncontradoException("Categoria não encontrada! Id: " + id));
    }

    public Page<ShowCategoriaResponse> findAll(String nome, TipoSituacao situacao, Pageable pageable) {
        Page<Categoria> categorias = categoriaRepository.findAll(nome, situacao, pageable);
        if (categorias.isEmpty()) {
            throw new RegistroNaoEncontradoException("Não há registros!");
        }
        return categorias.map(categoria -> modelMapper.map(categoria, ShowCategoriaResponse.class));
    }

    public void delete(Long id) {
        categoriaRepository.findById(id);
        try {
            categoriaRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Categoria não pode ser deletada! O cadastro possui registros ");
        }
    }

    public Categoria create(CreateCategoriaRequest createCategoriaRequest) {
        ifNomeExistsThrowsException(createCategoriaRequest.getNome());

        final var categoria = this.modelMapper.map(createCategoriaRequest, Categoria.class);

        return categoriaRepository.save(categoria);
    }


    public void ifNomeExistsThrowsException(String nome) {
        if (categoriaRepository.existsByNome(nome)) {
            throw new RegraDeNegocioException("Categoria ja cadastrada! Nome: " + nome);
        }
    }

    public Categoria update(Long id, UpdateCategoriaRequest categoriaDto) {
        ifCategoriaDoesNotExistsThrowsException(id);

        Categoria obj = findByid(id);
        obj.setNome(categoriaDto.getNome());
        obj.setSituacao(categoriaDto.getSituacao());
        return categoriaRepository.save(obj);
    }

    public void ifCategoriaDoesNotExistsThrowsException(Long id) {
        if (!categoriaRepository.existsById(id)) {
            throw new RegraDeNegocioException("Categoria não cadastrada!");
        }
    }

    public List<CategoriaComboDto> findAllCategoriaComboDto() {
        return this.categoriaRepository.findAllCategoriaComboDto();
    }
}
