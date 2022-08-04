package br.com.totemti.onboarding.repositories;


import br.com.totemti.onboarding.models.PessoaVenda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PessoaVendaRepository extends JpaRepository<PessoaVenda, Long> {

}


