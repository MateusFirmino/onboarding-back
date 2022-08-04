package br.com.totemti.onboarding.enumerators;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

public enum TipoSituacao {
    ATIVO('A'), INATIVO('I');

    private final Character situacao;

    TipoSituacao(Character situacao) {

        this.situacao = situacao;
    }

    public Character getType() {
        return situacao;
    }

    public static TipoSituacao toEnum(Character situacao) {
        for (TipoSituacao tipoSituacao : TipoSituacao.values()) {
            if (tipoSituacao.getType().equals(situacao)) {
                return tipoSituacao;
            }
        }
        throw new IllegalArgumentException("Tipo não encontrado: " + situacao);
    }

    @Converter(autoApply = true)
    public static class Mapper implements AttributeConverter<TipoSituacao, Character> {

        @Override
        public Character convertToDatabaseColumn(TipoSituacao tipoSituacao) {
            return tipoSituacao != null ? tipoSituacao.getType() : null;
        }

        @Override
        public TipoSituacao convertToEntityAttribute(Character situacao) {
            return TipoSituacao.toEnum(situacao);
        }
    }
}