package br.com.totemti.onboarding.repositories;

import br.com.totemti.onboarding.models.Email;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaEmailRepository extends JpaRepository<Email, Long> {
    @Query("select email from Pessoa pessoa " +
            "join pessoa.emails email " +
            "where pessoa.id = :id")
    Page<Email> findEmailByPerson(Long id, Pageable pageable);

    boolean existsEmailById(Long id);
}
