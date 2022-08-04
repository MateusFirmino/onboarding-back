package br.com.totemti.onboarding.services;

import br.com.totemti.onboarding.controllers.dto.CadastrarPessoaTelefoneDto;
import br.com.totemti.onboarding.controllers.dto.TelefoneShowDto;
import br.com.totemti.onboarding.exceptions.RegistroNaoEncontradoException;
import br.com.totemti.onboarding.exceptions.RegraDeNegocioException;
import br.com.totemti.onboarding.models.Pessoa;
import br.com.totemti.onboarding.models.Telefone;
import br.com.totemti.onboarding.repositories.PessoaTelefoneRepository;
import br.com.totemti.onboarding.services.exceptions.DataIntegrityViolationException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class PessoaTelefoneService {

    private final PessoaTelefoneRepository pessoaTelefoneRepository;
    private final ModelMapper modelMapper;

    public Telefone create(CadastrarPessoaTelefoneDto cadastrarPessoaTelefoneDto, Pessoa pessoa) {
        final var pessoaTelefone = this.modelMapper.map(cadastrarPessoaTelefoneDto, Telefone.class);
        pessoaTelefone.setPessoa(pessoa);
        return this.pessoaTelefoneRepository.save(pessoaTelefone);
    }

    public Telefone findByid(Long id) {
        Optional<Telefone> obj = pessoaTelefoneRepository.findById(id);
        return obj.orElseThrow(() -> new RegistroNaoEncontradoException("Telefone não encontrado! Id: " + id));
    }

    public Page<TelefoneShowDto> findTelefoneByPerson(Long id, Pageable pageable) {
        return pessoaTelefoneRepository.findTelefoneByPerson(id,pageable)
                .map(telefone -> modelMapper.map(telefone, TelefoneShowDto.class));
    }

    public void delete(Long id) {
        pessoaTelefoneRepository.findById(id);
        try {
            pessoaTelefoneRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Telefone não pode ser deletado! Cadastro possui registros ");
        }
    }

    public Telefone update(Long id, CadastrarPessoaTelefoneDto request) {
        Telefone telefone = findByid(id);

        telefone.setTelefone(request.getTelefone());
        telefone.setTipoTelefone(request.getTipoTelefone());
        telefone.setTelefonePadrao(request.getTelefonePadrao());
        return pessoaTelefoneRepository.save(telefone);
    }

    public void ifTelefoneDoesNotExistsThrowsException(Long id) {
        if (!pessoaTelefoneRepository.existsById(id)) {
            throw new RegraDeNegocioException("Telefone não cadastrado!");
        }
    }
}
