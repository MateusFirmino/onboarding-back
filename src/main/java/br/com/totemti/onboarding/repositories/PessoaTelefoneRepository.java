package br.com.totemti.onboarding.repositories;

import br.com.totemti.onboarding.models.Email;
import br.com.totemti.onboarding.models.Telefone;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface PessoaTelefoneRepository extends JpaRepository<Telefone, Long> {
    @Query("select telefone from Pessoa pessoa " +
            "join pessoa.telefones telefone " +
            "where pessoa.id = :id")
    Page<Telefone> findTelefoneByPerson(Long id, Pageable pageable);
}
