package br.com.totemti.onboarding.services.exceptions;

public class ObejctNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ObejctNotFoundException() {
        super("erro.naoEncontrado");
    }
    public ObejctNotFoundException(String cause) {
        super(cause);
    }
}