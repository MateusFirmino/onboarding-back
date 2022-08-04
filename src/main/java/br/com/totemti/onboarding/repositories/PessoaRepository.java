package br.com.totemti.onboarding.repositories;

import br.com.totemti.onboarding.controllers.dto.PessoaComboDto;
import br.com.totemti.onboarding.controllers.dto.relatorio.RelatorioPessoaDto;
import br.com.totemti.onboarding.enumerators.TipoPessoa;
import br.com.totemti.onboarding.enumerators.TipoSituacao;
import br.com.totemti.onboarding.models.Pessoa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

    @Query("SELECT p FROM Pessoa p WHERE p.cpfCnpj LIKE CONCAT('%', :cpfCnpj, '%')")
    Optional<Pessoa> findByCpfCnpj(String cpfCnpj);

    boolean existsByCpfCnpj(String cpfCnpj);

    boolean existsByInscricaoEstadual(String inscricaoEstadual);

    @Query("SELECT p FROM Pessoa p " +
            "WHERE LOWER(p.nome) LIKE CONCAT('%', LOWER(:nome), '%') " +
            "or :nome is null")
    List<Pessoa> findByNome(String nome);

    @Query("SELECT p FROM Pessoa p " +
            "WHERE (p.tipoPessoa = :tipoPessoa or :tipoPessoa is null) " +
            "and LOWER(p.nome) LIKE CONCAT('%', :nome, '%') " +
            "and (p.situacao = :situacao or :situacao is null) " +
            "order by p.nome")
    Page<Pessoa> findAll(TipoPessoa tipoPessoa, String nome, TipoSituacao situacao, Pageable pageable);

    @Query("SELECT NEW br.com.totemti.onboarding.controllers.dto.PessoaComboDto(pessoa.id, pessoa.nome, pessoa.cpfCnpj) " +
            "FROM Pessoa pessoa ")
    List<PessoaComboDto> findAllPessoaComboDto();

    @Query("SELECT NEW br.com.totemti.onboarding.controllers.dto.relatorio.RelatorioPessoaDto(" +
            "p.nome, p.tipoPessoa, CONCAT(pe2.cidade, ' - ', pe2.uf), pt.telefone, pe.email" +
            ") " +
            "FROM	Pessoa p " +
            "JOIN	p.emails pe " +
            "JOIN	p.enderecos pe2 " +
            "JOIN	p.telefones pt " +
            "WHERE  pe.emailPadrao = 'S' " +
            "AND 	pe2.enderecoPadrao = 'S' " +
            "AND 	pt.telefonePadrao = 'S' " +
            "AND    (p.tipoPessoa = :tipoPessoa or :tipoPessoa is null) " +
            "AND    (UPPER(p.nome) LIKE CONCAT('%', upper(:nome) , '%') or :nome is null)  " +
            "ORDER BY p.nome")
    List<RelatorioPessoaDto> listaRelatorioPessoa(@Param("tipoPessoa") TipoPessoa tipoPessoa, @Param("nome") String nome);
}
