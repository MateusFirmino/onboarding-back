package br.com.totemti.onboarding.enumerators;

import lombok.NoArgsConstructor;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

public enum TipoProduto {
    UNIDADE(1),
    CAIXA(2),
    PESO(3);

    private final Integer id;

    TipoProduto(Integer id) {

        this.id = id;
    }

    public static TipoProduto toEnum(Integer id) {
        for (TipoProduto tipoProduto : TipoProduto.values()) {
            if (tipoProduto.getId().equals(id)) {
                return tipoProduto;
            }
        }
        throw new IllegalArgumentException("Tipo não encontrado: " + id);
    }

    public Integer getId() {
        return id;
    }
    @Converter(autoApply = true)
    public static class Mapper implements AttributeConverter<TipoProduto, Integer> {


        @Override
        public Integer convertToDatabaseColumn(TipoProduto tipoProduto) {
            return tipoProduto != null ? tipoProduto.getId() : null;
        }

        @Override
        public TipoProduto convertToEntityAttribute(Integer id) {
            return TipoProduto.toEnum(id);
        }

    }
}