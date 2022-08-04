package br.com.totemti.onboarding.services;

import br.com.totemti.onboarding.controllers.dto.CadastrarPessoaVendaDto;
import br.com.totemti.onboarding.controllers.dto.CadastrarVendaItemDto;
import br.com.totemti.onboarding.controllers.dto.ClienteVendaDto;
import br.com.totemti.onboarding.controllers.dto.VendaItemDto;
import br.com.totemti.onboarding.exceptions.RegistroNaoEncontradoException;
import br.com.totemti.onboarding.exceptions.RegraDeNegocioException;
import br.com.totemti.onboarding.models.PessoaVenda;
import br.com.totemti.onboarding.repositories.PessoaVendaRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ClienteVendaService {
    private final PessoaVendaRepository pessoaVendaRepository;
    private final VendaItemService vendaItemService;

    private final PessoaService pessoaService;

    private final ModelMapper modelMapper;

    public CadastrarPessoaVendaDto findById(Long id) {
        PessoaVenda pessoaVenda = findByIdOrElseThrow(id);
        return modelMapper.map(pessoaVenda, CadastrarPessoaVendaDto.class);
    }

    public ClienteVendaDto salvar(CadastrarPessoaVendaDto cadastrarPessoaVendaDto) {
        final var clienteVenda = modelMapper.map(cadastrarPessoaVendaDto, PessoaVenda.class);
        final var pessoa = pessoaService.maybeFindById(cadastrarPessoaVendaDto.getIdPessoa())
                .orElseThrow(() -> new RegistroNaoEncontradoException("Pessoa não encontrada"));
        clienteVenda.setPessoa(pessoa);
        pessoaVendaRepository.save(clienteVenda);
        final var itens = salvarVendaItens(clienteVenda, cadastrarPessoaVendaDto.getItens());

        final var venda = modelMapper.map(clienteVenda, ClienteVendaDto.class);
        venda.setVendaItem(itens);
        return venda;
    }

    private List<VendaItemDto> salvarVendaItens(PessoaVenda pessoaVenda, List<CadastrarVendaItemDto> vendaItem) {
        ifVendaItemIsEmptyThrowsException(vendaItem);
        return vendaItem.stream()
                .map(cadaVenda -> vendaItemService.salvar(cadaVenda, pessoaVenda))
                .collect(Collectors.toList());
    }

    private void ifVendaItemIsEmptyThrowsException(List<CadastrarVendaItemDto> vendaItem) {
        if (vendaItem == null || vendaItem.isEmpty()) {
            throw new RegraDeNegocioException("Informe pelo menos uma venda");
        }
    }

    public void excluir(Long id) {
        PessoaVenda pessoaVenda = findByIdOrElseThrow(id);
        pessoaVendaRepository.delete(pessoaVenda);
    }

    public Page<CadastrarPessoaVendaDto> listar(Pageable pageable) {
        return pessoaVendaRepository.findAll(pageable).map(CadastrarPessoaVendaDto::of);
    }

    private PessoaVenda findByIdOrElseThrow(Long id) {
        return pessoaVendaRepository.findById(id).orElseThrow(() -> new RegistroNaoEncontradoException("Venda não encontrada !"));
    }

}
