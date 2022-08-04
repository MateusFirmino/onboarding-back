package br.com.totemti.onboarding.services;

import br.com.totemti.onboarding.controllers.dto.CadastrarVendaItemDto;
import br.com.totemti.onboarding.controllers.dto.VendaItemDto;
import br.com.totemti.onboarding.controllers.dto.relatorio.RelatorioMovimentacaoDto;
import br.com.totemti.onboarding.exceptions.RegistroNaoEncontradoException;
import br.com.totemti.onboarding.models.PessoaVenda;
import br.com.totemti.onboarding.models.VendaItem;
import br.com.totemti.onboarding.repositories.VendaItemRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.expression.ParseException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@Service
public class VendaItemService {
    private final VendaItemRepository vendaItemRepository;
    private final ModelMapper modelMapper;
    private final ProdutoService produtoService;


    public VendaItem buscar(Long id) {
        return vendaItemRepository
                .findById(id)
                .orElseThrow(() -> new RegistroNaoEncontradoException("vendaItem.naoencontrado"));
    }

    public VendaItemDto salvar(CadastrarVendaItemDto cadastrarVendaItemDto, PessoaVenda pessoaVenda) {
        final var vendaItem = modelMapper.map(cadastrarVendaItemDto, VendaItem.class);
        vendaItem.setPessoaVenda(pessoaVenda);
        final var produto = produtoService.findById(cadastrarVendaItemDto.getIdProduto());
        vendaItem.setProduto(produto);
        return modelMapper.map(vendaItemRepository.save(vendaItem), VendaItemDto.class);
    }

    public void excluir(Long id) {
        VendaItem vendaItem = buscar(id);
        vendaItemRepository.delete(vendaItem);
    }

    public Page<RelatorioMovimentacaoDto> geraRelatorio(LocalDate dataInicial, LocalDate dataFinal, Integer valorMin,
                                                        Integer valorMax, Long idProduto, Long idPessoa, Pageable pageable) throws ParseException {
        BigDecimal novovalor;
        BigDecimal novomin;
        if (valorMax == null) {
            novovalor = null;
        } else {
            novovalor = BigDecimal.valueOf(valorMax.longValue());
        }
        if (valorMin == null) {
            novomin = null;
        } else {
            novomin = BigDecimal.valueOf(valorMin.longValue());
        }
        return vendaItemRepository
                .listaMovimentacaoDiaria(dataInicial, dataFinal, novomin, novovalor, idProduto, idPessoa, pageable);
    }
}

