package br.com.totemti.onboarding.repositories;


import br.com.totemti.onboarding.controllers.dto.relatorio.RelatorioMovimentacaoDto;
import br.com.totemti.onboarding.models.VendaItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;


@Repository
public interface VendaItemRepository extends JpaRepository<VendaItem, Long> {

    @Query(value = "SELECT NEW br.com.totemti.onboarding.controllers.dto.relatorio.RelatorioMovimentacaoDto(" +
            "cv.dataVenda, p2.nome, vi.quantidade, vi.subtotal, p.nome, cv.usuarioVenda" +
            ") " +
            "FROM	VendaItem vi " +
            "JOIN	vi.pessoaVenda cv " +
            "JOIN	cv.pessoa p " +
            "JOIN	vi.produto p2 " +
            "WHERE  (cv.dataVenda >= :dataInicial " +
            "OR     :dataInicial is null) " +
            "AND    (cv.dataVenda <= :dataFinal " +
            "OR     :dataFinal is null) " +
            "AND    (vi.subtotal >= :valorMin " +
            "OR     :valorMin is null) " +
            "AND    (vi.subtotal <= :valorMax " +
            "OR     :valorMax is null) " +
            "AND    UPPER(cv.usuarioVenda) = UPPER('Admin') " +
            "AND    (p2.id = :idProduto " +
            "OR     :idProduto is null) " +
            "AND    (p.id = :idPessoa " +
            "OR     :idPessoa is null)" +
            "ORDER BY cv.dataVenda")
    Page<RelatorioMovimentacaoDto> listaMovimentacaoDiaria(@Param("dataInicial") LocalDate dataInicial, @Param("dataFinal") LocalDate dataFinal,
                                                           @Param("valorMin") BigDecimal valorMin, @Param("valorMax") BigDecimal valorMax,
                                                           @Param("idProduto") Long idProduto, @Param("idPessoa") Long idPessoa, Pageable pageable);

}



