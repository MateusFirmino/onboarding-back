package br.com.totemti.onboarding.enumerators;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

public enum TipoTelefone {
    RESIDENCIAL("RESIDENCIAL"),
    CELULAR("CELULAR"),
    CONTATO("CONTATO");

    private final String type;

    TipoTelefone(String type) {

        this.type = type;
    }

    public static TipoTelefone toEnum(String tipo) {
        for (TipoTelefone tipoTelefone : TipoTelefone.values()) {
            if (tipoTelefone.getType().equalsIgnoreCase(tipo)) {
                return tipoTelefone;
            }
        }
        throw new IllegalArgumentException("Tipo não encontrado: " + tipo);
    }

    public String getType() {
        return type;
    }

    @Converter(autoApply = true)
    public static class Mapper implements AttributeConverter<TipoTelefone, String> {

        @Override
        public String convertToDatabaseColumn(TipoTelefone tipoTelefone) {
            return tipoTelefone != null ? tipoTelefone.getType() : null;
        }

        @Override
        public TipoTelefone convertToEntityAttribute(String tipo) {
            return TipoTelefone.toEnum(tipo);
        }
    }
}
