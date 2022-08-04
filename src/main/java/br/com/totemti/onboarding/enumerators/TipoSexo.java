package br.com.totemti.onboarding.enumerators;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

public enum TipoSexo {
    MASCULINO('M'), FEMININO('F');

    private final Character sexo;

    TipoSexo(Character sexo) {

        this.sexo = sexo;
    }

    public Character getSexo() {
        return sexo;
    }

    public static TipoSexo toEnum(Character sexo) {
        if(sexo == null){
            return null;
        }
        for (TipoSexo tipoSexo : TipoSexo.values()) {
            if (tipoSexo.getSexo().equals(sexo)) {
                return tipoSexo;
            }
        }
        throw new IllegalArgumentException("Tipo não encontrado: " + sexo);
    }

    @Converter(autoApply = true)
    public static class Mapper implements AttributeConverter<TipoSexo, Character> {

        @Override
        public Character convertToDatabaseColumn(TipoSexo tipoSexo) {
            return tipoSexo != null ? tipoSexo.getSexo() : null;
        }

        @Override
        public TipoSexo convertToEntityAttribute(Character y) {
            return TipoSexo.toEnum(y);
        }
    }
}