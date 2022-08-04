package br.com.totemti.onboarding.services;

import br.com.totemti.onboarding.controllers.dto.*;
import br.com.totemti.onboarding.controllers.dto.relatorio.RelatorioPessoaDto;
import br.com.totemti.onboarding.enumerators.TipoPessoa;
import br.com.totemti.onboarding.enumerators.TipoSituacao;
import br.com.totemti.onboarding.exceptions.RegistroNaoEncontradoException;
import br.com.totemti.onboarding.exceptions.RegraDeNegocioException;
import br.com.totemti.onboarding.models.Pessoa;
import br.com.totemti.onboarding.repositories.PessoaRepository;
import br.com.totemti.onboarding.services.exceptions.DataIntegrityViolationException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PessoaService {

    private final PessoaRepository pessoaRepository;
    private final ModelMapper modelMapper;
    private final PessoaEmailService pessoaEmailService;
    private final PessoaEnderecoService pessoaEnderecoService;
    private final PessoaTelefoneService pessoaTelefoneService;

    @Transactional
    public Pessoa create(CadastrarPessoaRequest request) {
        if (request instanceof CadastrarPessoaFisicaRequest) {
            return createPessoaFisica((CadastrarPessoaFisicaRequest) request);
        } else if (request instanceof CadastrarPessoaJuridicaRequest) {
            return createPessoaJuridica((CadastrarPessoaJuridicaRequest) request);
        } else {
            throw new RegraDeNegocioException("Dados inválidos!");
        }
    }

    @Transactional
    public Pessoa update(Long id, CadastrarPessoaRequest request) {
        final var pessoa = this.pessoaRepository.findById(id).orElseThrow(() -> new RegistroNaoEncontradoException("Pessoa não encontrado! Id: " + id));
        updatePessoa(request, pessoa);

        if (request instanceof CadastrarPessoaFisicaRequest) {
            updatePessoaFisica((CadastrarPessoaFisicaRequest) request, pessoa);
        } else if (request instanceof CadastrarPessoaJuridicaRequest) {
            updatePessoaJuridica((CadastrarPessoaJuridicaRequest) request, pessoa);
        } else {
            throw new RegraDeNegocioException("Dados inválidos!");
        }
        return pessoaRepository.save(pessoa);
    }

    private void updatePessoaJuridica(CadastrarPessoaJuridicaRequest request, Pessoa pessoa) {
        Optional.ofNullable(request.getInscricaoEstadual()).ifPresent(pessoa::setInscricaoEstadual);
    }

    private void updatePessoaFisica(CadastrarPessoaFisicaRequest request, Pessoa pessoa) {
        Optional.ofNullable(request.getSexo()).ifPresent(pessoa::setSexo);
        Optional.ofNullable(request.getDataNascimento()).ifPresent(pessoa::setDataNascimento);
    }

    private void updatePessoa(CadastrarPessoaRequest request, Pessoa pessoa) {
        Optional.ofNullable(request.getNome()).ifPresent(pessoa::setNome);
        Optional.ofNullable(request.getCpfCnpj()).ifPresent(pessoa::setCpfCnpj);
        Optional.ofNullable(request.getSituacao()).ifPresent(pessoa::setSituacao);

        //Optional.ofNullable(request.getTipoPessoa()).ifPresent(pessoa::setTipoPessoa);
    }


    public Pessoa createPessoaFisica(CadastrarPessoaFisicaRequest cadastrarPessoaFisicaDto) {
        validate(cadastrarPessoaFisicaDto);

        return atribuirInformacoes(cadastrarPessoaFisicaDto);
    }

    public Pessoa createPessoaJuridica(CadastrarPessoaJuridicaRequest cadastrarPessoaJuridicaDto) {
        validate(cadastrarPessoaJuridicaDto);
        validatePessoaJuridica(cadastrarPessoaJuridicaDto);

        return atribuirInformacoes(cadastrarPessoaJuridicaDto);
    }

    private Pessoa atribuirInformacoes(CadastrarPessoaRequest cadastrarPessoaDto) {
        final var pessoa = this.modelMapper.map(cadastrarPessoaDto, Pessoa.class);
        final var pessoaSave = pessoaRepository.save(pessoa);

        atribuirEmails(cadastrarPessoaDto, pessoa, pessoaSave);
        atribuirEnderecos(cadastrarPessoaDto, pessoa, pessoaSave);
        atribuirTelefones(cadastrarPessoaDto, pessoa, pessoaSave);
        return pessoaSave;
    }

    private void atribuirTelefones(CadastrarPessoaRequest cadastrarPessoaDto, Pessoa pessoa, Pessoa pessoaSave) {

        final var telefones = cadastrarPessoaDto.getTelefones();
        if (telefones != null) {
            final var pessoaTelefoneSet = telefones.stream().map(pessoaTelefoneDto -> pessoaTelefoneService.create(pessoaTelefoneDto, pessoaSave)).collect(Collectors.toSet());
            pessoa.setTelefones(pessoaTelefoneSet);
        }
    }

    private void atribuirEnderecos(CadastrarPessoaRequest cadastrarPessoaDto, Pessoa pessoa, Pessoa pessoaSave) {
        final var enderecos = cadastrarPessoaDto.getEnderecos();
        if (enderecos != null) {
            final var pessoaEnderecoSet = enderecos.stream().map(pessoaEnderecoDto -> pessoaEnderecoService.create(pessoaEnderecoDto, pessoaSave)).collect(Collectors.toSet());
            pessoa.setEnderecos(pessoaEnderecoSet);
        }
    }

    private void atribuirEmails(CadastrarPessoaRequest cadastrarPessoaDto, Pessoa pessoa, Pessoa pessoaSave) {
        final var emails = cadastrarPessoaDto.getEmails();
        if (emails != null) {
            final var pessoaEmailSet = emails.stream().map(pessoaEmailDto -> pessoaEmailService.create(pessoaEmailDto, pessoaSave)).collect(Collectors.toSet());
            pessoa.setEmails(pessoaEmailSet);
        }
    }


    public void validate(CadastrarPessoaRequest cadastrarPessoaDto) {
        ifCpfCnpjExistsThrowsException(cadastrarPessoaDto.getCpfCnpj());
    }

    public void validatePessoaJuridica(CadastrarPessoaJuridicaRequest cadastrarPessoaJuridicaDto) {
        ifInscricaoEstadualThrowsException(cadastrarPessoaJuridicaDto.getInscricaoEstadual());
    }

    public void ifCpfCnpjExistsThrowsException(String cpfCnpj) {
        if (pessoaRepository.existsByCpfCnpj(cpfCnpj)) {
            throw new RegraDeNegocioException("Pessoa ja cadastrada! Cpf/Cnpj: " + cpfCnpj);
        }
    }

    public void ifInscricaoEstadualThrowsException(String inscricaoEstadual) {
        if (pessoaRepository.existsByInscricaoEstadual(inscricaoEstadual)) {
            throw new RegraDeNegocioException("Pessoa Juridica ja cadastrada! Inscrição Estadual: " + inscricaoEstadual);
        }
    }

    public Page<Pessoa> findAll(TipoPessoa tipoPessoa, String nome, TipoSituacao situacao, Pageable pageable) {
        Page<Pessoa> pessoas = pessoaRepository.findAll(tipoPessoa, nome, situacao, pageable);
        if (pessoas.isEmpty()) {
            throw new RegistroNaoEncontradoException("Não há registros!");
        }
        return pessoas;
    }

    public PessoaShowDto findByid(Long id) {
        Optional<Pessoa> obj = pessoaRepository.findById(id);

        return obj.map(pessoa -> {
            var dto = modelMapper.map(pessoa, PessoaShowDto.class);
            dto.setEmails(pessoa.getEmails().stream().map(email -> modelMapper.map(email, EmailShowDto.class)).collect(Collectors.toList()));
            dto.setEnderecos(pessoa.getEnderecos().stream().map(endereco -> modelMapper.map(endereco, EnderecoShowDto.class)).collect(Collectors.toList()));
            dto.setTelefones(pessoa.getTelefones().stream().map(telefone -> modelMapper.map(telefone, TelefoneShowDto.class)).collect(Collectors.toList()));
            return dto;
        }).orElseThrow(() -> new RegistroNaoEncontradoException("Pessoa não encontrado! Id: " + id));
    }


    public void delete(Long id) {
        pessoaRepository.findById(id);
        try {
            pessoaRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Pessoa não pode ser deletada! Cadastro possui registros ");
        }
    }


    public Optional<Pessoa> maybeFindById(Long id) {
        return this.pessoaRepository.findById(id);
    }

    public List<PessoaComboDto> findAllPessoaComboDto() {
        return this.pessoaRepository.findAllPessoaComboDto();
    }

    public List<PessoaComboDto> findByNome(String nome) {
        final var clientes = pessoaRepository.findByNome(nome);
        return clientes.stream().map(pessoa -> modelMapper.map(pessoa, PessoaComboDto.class)).collect(Collectors.toList());

    }

    public List<RelatorioPessoaDto> lista(TipoPessoa tipoPessoa, String nome) {
//        if(tipoPessoa == null){
//            tipoPessoa = TipoPessoa.valueOf(" ");
//        }
        if (nome == null) {
            nome = "";
        }
        return pessoaRepository.listaRelatorioPessoa(tipoPessoa, nome);
    }
}
