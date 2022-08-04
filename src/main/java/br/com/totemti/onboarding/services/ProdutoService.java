package br.com.totemti.onboarding.services;


import br.com.totemti.onboarding.controllers.dto.CadastarProdutoDto;
import br.com.totemti.onboarding.controllers.dto.ProdutoShowDto;
import br.com.totemti.onboarding.controllers.dto.relatorio.RelatorioProdutoDto;
import br.com.totemti.onboarding.enumerators.TipoSituacao;
import br.com.totemti.onboarding.exceptions.RegistroNaoEncontradoException;
import br.com.totemti.onboarding.exceptions.RegraDeNegocioException;
import br.com.totemti.onboarding.models.Produto;
import br.com.totemti.onboarding.repositories.ProdutoRepository;
import br.com.totemti.onboarding.services.exceptions.DataIntegrityViolationException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ProdutoService {

    private final CategoriaService categoriaService;

    private final ProdutoRepository produtoRepository;

    private final ModelMapper modelMapper;

    public Produto findById(Long id) {
        Optional<Produto> obj = produtoRepository.findById(id);
        return obj.orElseThrow(() -> new RegistroNaoEncontradoException("Produto não encontrado! Id: " + id));
    }

    public List<ProdutoShowDto> findByNome(String nome) {
        final var produtos = produtoRepository.findByNome(nome);
        return produtos.stream().map(produto -> modelMapper.map(produto, ProdutoShowDto.class)).collect(Collectors.toList());

    }

    public Page<Produto> findAll(Long categoria, String nome, TipoSituacao tipoSituacao, Pageable pageable) {
        Page<Produto> produtos = produtoRepository.findAll(categoria, nome, tipoSituacao, pageable);
        if (produtos.isEmpty()) {
            throw new RegistroNaoEncontradoException("Não há registros!");
        }
        return produtos;
    }


    public void delete(Long id) {
        produtoRepository.findById(id);
        try {
            produtoRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Produto não pode ser deletado! O cadastro possui registros ");
        }
    }

    public Produto create(CadastarProdutoDto cadastarProdutoDto) {
        ifNomeExistsThrowsException(cadastarProdutoDto.getNome());

        final var produto = this.modelMapper.map(cadastarProdutoDto, Produto.class);
        // produto.setDataCadastro(LocalDate.now());
        final var categoria = categoriaService.findByid(cadastarProdutoDto.getCategoria().getId());
        produto.setCategoria(categoria);

        return produtoRepository.save(produto);
    }

    public Produto update(Long id, CadastarProdutoDto cadastarProdutoDto) {
        ifProdutoDoesNotExistsThrowsException(id);

        final var produto = this.modelMapper.map(cadastarProdutoDto, Produto.class);
        final var categoria = categoriaService.findByid(cadastarProdutoDto.getCategoria().getId());
        //  produto.setDataCadastro(LocalDate.now());

        produto.setId(id);
        produto.setCategoria(categoria);

        return produtoRepository.save(produto);
    }

    public void ifNomeExistsThrowsException(String nome) {
        if (produtoRepository.existsByNome(nome)) {
            throw new RegraDeNegocioException("Produto ja cadastrado! Nome: " + nome);
        }
    }

    public void ifProdutoDoesNotExistsThrowsException(Long id) {
        if (!produtoRepository.existsById(id)) {
            throw new RegraDeNegocioException("Produto não cadastrado!");
        }
    }

    public List<RelatorioProdutoDto> geraRelatorio(Long produtoCategoria, String nomeproduto, TipoSituacao situacao) {
        if (nomeproduto != null) {
            nomeproduto = nomeproduto.toUpperCase();
        } else {
            nomeproduto = "";
        }
        return produtoRepository
                .geraRelatorioProduto(produtoCategoria, nomeproduto, situacao);
    }
}
