package br.com.totemti.onboarding.repositories;

import br.com.totemti.onboarding.models.Endereco;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface PessoaEnderecoRepository extends JpaRepository<Endereco, Long> {
    @Query("select endereco from Pessoa pessoa " +
            "join pessoa.enderecos endereco " +
            "where pessoa.id = :id")
    Page<Endereco> findEnderecoByPerson(Long id, Pageable pageable);
}
