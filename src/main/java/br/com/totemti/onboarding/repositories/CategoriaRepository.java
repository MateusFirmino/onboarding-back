package br.com.totemti.onboarding.repositories;

import br.com.totemti.onboarding.controllers.dto.categorias.CategoriaComboDto;
import br.com.totemti.onboarding.enumerators.TipoSituacao;
import br.com.totemti.onboarding.models.Categoria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    @Query("SELECT c FROM Categoria c " +
            "WHERE LOWER(c.nome) LIKE CONCAT('%', :nome, '%') " +
            "and (c.situacao = :situacao or :situacao is null) " +
            "order by c.nome")
    Page<Categoria> findAll(String nome, TipoSituacao situacao, Pageable pageable);

    @Query("SELECT c FROM Categoria c " +
            "WHERE c.nome LIKE CONCAT('%', :nome, '%')")
    Optional<Categoria> findByNome(String nome);

    boolean existsByNome(String nome);

    @Query("SELECT NEW br.com.totemti.onboarding.controllers.dto.categorias.CategoriaComboDto(categoria.id, categoria.nome) " +
            "FROM Categoria categoria ")
    List<CategoriaComboDto> findAllCategoriaComboDto();

//    @Query("SELECT c FROM Categoria c WHERE c.situacao = :A")
//    Optional<Categoria> validaSituacao(TipoSituacao situacao);


}
