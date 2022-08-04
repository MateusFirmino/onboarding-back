package br.com.totemti.onboarding.services;


import br.com.totemti.onboarding.controllers.dto.CadastrarPessoaEnderecoDto;
import br.com.totemti.onboarding.controllers.dto.EnderecoShowDto;
import br.com.totemti.onboarding.exceptions.RegistroNaoEncontradoException;
import br.com.totemti.onboarding.exceptions.RegraDeNegocioException;
import br.com.totemti.onboarding.models.Endereco;
import br.com.totemti.onboarding.models.Pessoa;
import br.com.totemti.onboarding.repositories.PessoaEnderecoRepository;
import br.com.totemti.onboarding.services.exceptions.DataIntegrityViolationException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class PessoaEnderecoService {

    private final PessoaEnderecoRepository pessoaEnderecoRepository;
    private final ModelMapper modelMapper;

    public Endereco create(CadastrarPessoaEnderecoDto cadastrarPessoaEnderecoDto, Pessoa pessoa) {
        final var pessoaEndereco = this.modelMapper.map(cadastrarPessoaEnderecoDto, Endereco.class);
        pessoaEndereco.setPessoa(pessoa);
        return this.pessoaEnderecoRepository.save(pessoaEndereco);
    }

    public Page<EnderecoShowDto> findEnderecoByPerson(Long id, Pageable pageable) {
        return pessoaEnderecoRepository.findEnderecoByPerson(id, pageable)
                .map(endereco -> modelMapper.map(endereco, EnderecoShowDto.class));
    }

    public void delete(Long id) {
        pessoaEnderecoRepository.findById(id);
        try {
            pessoaEnderecoRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Endereço não pode ser deletado! Cadastro possui registros ");
        }
    }

    public Endereco update(Long id, CadastrarPessoaEnderecoDto cadastrarPessoaEnderecoDto) {
        ifEnderecoDoesNotExistsThrowsException(id);

        Endereco endereco = findByid(id);

        endereco.setCep(cadastrarPessoaEnderecoDto.getCep());
        endereco.setLogradouro(cadastrarPessoaEnderecoDto.getLogradouro());
        endereco.setNumero(cadastrarPessoaEnderecoDto.getNumero());
        endereco.setBairro(cadastrarPessoaEnderecoDto.getBairro());
        endereco.setCidade(cadastrarPessoaEnderecoDto.getCidade());
        endereco.setUf(cadastrarPessoaEnderecoDto.getUf());
        endereco.setEnderecoPadrao(cadastrarPessoaEnderecoDto.getEnderecoPadrao());
        return pessoaEnderecoRepository.save(endereco);

    }

    public Endereco findByid(Long id) {
        Optional<Endereco> obj = pessoaEnderecoRepository.findById(id);
        return obj.orElseThrow(() -> new RegistroNaoEncontradoException("Endereco não encontrado! Id: " + id));
    }

    public void ifEnderecoDoesNotExistsThrowsException(Long id) {
        if (!pessoaEnderecoRepository.existsById(id)) {
            throw new RegraDeNegocioException("Endereco não cadastrado!");
        }
    }
}
