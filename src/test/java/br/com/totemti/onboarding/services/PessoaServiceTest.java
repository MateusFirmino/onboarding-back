package br.com.totemti.onboarding.services;

import br.com.totemti.onboarding.repositories.PessoaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PessoaServiceTest {

    @InjectMocks
    private PessoaService pessoaService;

    @Mock
    private PessoaRepository pessoaRepository;

    @BeforeEach
    void setUp() {
    }

//    @Test
//    void abacate() {
//        final var cadastrarPessoaFisicaDto = new CadastrarPessoaFisicaDto();
//        var id = pessoaService.create(cadastrarPessoaFisicaDto);
//        Assertions.assertThat(id).isNotNull();
//
//    }

}