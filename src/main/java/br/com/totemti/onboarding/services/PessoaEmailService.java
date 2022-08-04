package br.com.totemti.onboarding.services;

import br.com.totemti.onboarding.controllers.dto.CadastrarPessoaEmailDto;
import br.com.totemti.onboarding.controllers.dto.EmailShowDto;
import br.com.totemti.onboarding.exceptions.RegistroNaoEncontradoException;
import br.com.totemti.onboarding.exceptions.RegraDeNegocioException;
import br.com.totemti.onboarding.models.Email;
import br.com.totemti.onboarding.models.Pessoa;
import br.com.totemti.onboarding.repositories.PessoaEmailRepository;
import br.com.totemti.onboarding.services.exceptions.DataIntegrityViolationException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class PessoaEmailService {
    private final PessoaEmailRepository pessoaEmailRepository;
    private final ModelMapper modelMapper;

    public Email create(CadastrarPessoaEmailDto cadastrarPessoaEmailDto, Pessoa pessoa) {
        final var pessoaEmail = this.modelMapper.map(cadastrarPessoaEmailDto, Email.class);
        final var existeEmail = pessoaEmailRepository.existsEmailById(pessoa.getId());
        if (existeEmail) {
            throw new RegraDeNegocioException("Email já cadastrado");
        }

        pessoaEmail.setPessoa(pessoa);
        return this.pessoaEmailRepository.save(pessoaEmail);
    }

    public Email update(Long id, CadastrarPessoaEmailDto cadastrarPessoaEmailDto) {
        ifEmailDoesNotExistsThrowsException(id);

        Email email = findByid(id);

        email.setEmail(cadastrarPessoaEmailDto.getEmail());
        email.setEmailPadrao(cadastrarPessoaEmailDto.getEmailPadrao());
        return pessoaEmailRepository.save(email);
    }

    public void ifEmailDoesNotExistsThrowsException(Long id) {
        if (!pessoaEmailRepository.existsById(id)) {
            throw new RegraDeNegocioException("Email não cadastrado!");
        }
    }


    public Email findByid(Long id) {
        Optional<Email> obj = pessoaEmailRepository.findById(id);
        return obj.orElseThrow(() -> new RegistroNaoEncontradoException("Email não encontrado! Id: " + id));
    }

    public Page<EmailShowDto> findEmailByPerson(Long id, Pageable pageable) {
        return pessoaEmailRepository.findEmailByPerson(id, pageable)
                .map(email -> modelMapper.map(email, EmailShowDto.class));
    }

    public void delete(Long id) {
        pessoaEmailRepository.findById(id);
        try {
            pessoaEmailRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Email não pode ser deletado! Cadastro possui registros ");
        }
    }
}
