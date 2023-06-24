package br.com.modelar.sislogmobile.exception;

public class BusinessException extends RuntimeException{
    private static final Long serialVersionUID = 1L;

    public BusinessException(String message) {
        super(message);
    }
}
