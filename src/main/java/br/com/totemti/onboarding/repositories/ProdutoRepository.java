package br.com.totemti.onboarding.repositories;


import br.com.totemti.onboarding.controllers.dto.relatorio.RelatorioProdutoDto;
import br.com.totemti.onboarding.enumerators.TipoSituacao;
import br.com.totemti.onboarding.models.Produto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    @Query("SELECT pd FROM Produto pd " +
            "JOIN pd.categoria categoria " +
            "WHERE (categoria.id = :categoria or :categoria is null) " +
            "and LOWER(pd.nome) LIKE CONCAT('%', :nome, '%') " +
            "and (pd.situacao = :situacao or :situacao is null) " +
            " order by pd.nome")
    Page<Produto> findAll(Long categoria, String nome, TipoSituacao situacao, Pageable pageable);

    @Query("SELECT pd FROM Produto pd " +
            "WHERE LOWER(pd.nome) LIKE CONCAT('%', LOWER(:nome), '%') " +
            "or :nome is null")
    List<Produto> findByNome(String nome);

    boolean existsByNome(String nome);

    @Query("SELECT NEW br.com.totemti.onboarding.controllers.dto.relatorio.RelatorioProdutoDto(" +
            "p.nome, pc.nome, p.valor" +
            ") " +
            "FROM 	Produto p " +
            "JOIN 	p.categoria pc " +
            "WHERE 	(pc.id = :idCategoria " +
            "OR		:idCategoria is null) " +
            "AND	UPPER(p.nome) LIKE %:nomeproduto% " +
            "AND	(p.situacao = :situacao " +
            "OR 	:situacao is null) " +
            "ORDER BY p.nome ")
    List<RelatorioProdutoDto> geraRelatorioProduto(@Param("idCategoria") Long id,
                                                   @Param("nomeproduto") String nomeproduto,
                                                   @Param("situacao") TipoSituacao situacao);
}
