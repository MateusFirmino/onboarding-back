package br.com.totemti.onboarding.enumerators;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

public enum TipoSituacaoVenda {
    ABERTO(1),
    CONCLUIDO(2),
    CANCELADO(3);

    private final Integer id;

    TipoSituacaoVenda(Integer id) {

        this.id = id;
    }

    public static TipoSituacaoVenda toEnum(Integer id) {
        for (TipoSituacaoVenda tipoSituacaoVenda : TipoSituacaoVenda.values()) {
            if (tipoSituacaoVenda.getId().equals(id)) {
                return tipoSituacaoVenda;
            }
        }
        throw new IllegalArgumentException("Tipo não encontrado: " + id);
    }

    public Integer getId() {
        return id;
    }

    @Converter(autoApply = true)
    public static class Mapper implements AttributeConverter<TipoSituacaoVenda, Integer> {


        @Override
        public Integer convertToDatabaseColumn(TipoSituacaoVenda tipoSituacaoVenda) {
            return tipoSituacaoVenda != null ? tipoSituacaoVenda.getId() : null;
        }

        @Override
        public TipoSituacaoVenda convertToEntityAttribute(Integer id) {
            return TipoSituacaoVenda.toEnum(id);
        }

    }
}
