package io.github.rogerhowell.exceptions;

public class ParameterValidationException extends IllegalArgumentException {

    private static final long serialVersionUID = 5420688624254676471L;


    public ParameterValidationException() {
        super();
    }


    public ParameterValidationException(final String s) {
        super(s);
    }


    public ParameterValidationException(final String message, final Throwable cause) {
        super(message, cause);
    }


    public ParameterValidationException(final Throwable cause) {
        super(cause);
    }


}
