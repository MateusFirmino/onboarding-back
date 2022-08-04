package br.com.totemti.onboarding.exceptions;

public class RegistroNaoEncontradoException extends RuntimeException {

    public RegistroNaoEncontradoException() {
        super("erro.naoEncontrado");
    }
    public RegistroNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}