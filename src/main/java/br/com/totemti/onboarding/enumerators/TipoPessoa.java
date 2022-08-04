package br.com.totemti.onboarding.enumerators;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

public enum TipoPessoa {
    FISICA('F'), JURIDICA('J');

    private final Character type;

    TipoPessoa(Character type) {

        this.type = type;
    }

    public Character getType() {
        return type;
    }

    public static TipoPessoa toEnum(Character type) {
        for (TipoPessoa tipoPessoa : TipoPessoa.values()) {
            if (tipoPessoa.getType().equals(type)) {
                return tipoPessoa;
            }
        }
        throw new IllegalArgumentException("Tipo não encontrado: " + type);
    }

    @Converter(autoApply = true)
    public static class Mapper implements AttributeConverter<TipoPessoa, Character> {

        @Override
        public Character convertToDatabaseColumn(TipoPessoa tipoPessoa) {
            return tipoPessoa != null ? tipoPessoa.getType() : null;
        }

        @Override
        public TipoPessoa convertToEntityAttribute(Character pessoa) {
            return TipoPessoa.toEnum(pessoa);
        }
    }
}
