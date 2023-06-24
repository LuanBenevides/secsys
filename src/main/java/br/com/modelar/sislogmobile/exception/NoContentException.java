package br.com.modelar.sislogmobile.exception;

public class NoContentException extends RuntimeException{
    private static final Long serialVersionUID = 1L;

    public NoContentException(String message) {
        super(message);
    }
}
