package io.github.rogerhowell.validation;

public class ParameterValidationFailException extends IllegalArgumentException {

    private static final long serialVersionUID = 5420688624254676471L;


    public ParameterValidationFailException() {
        super();
    }


    public ParameterValidationFailException(final String s) {
        super(s);
    }


    public ParameterValidationFailException(final String message, final Throwable cause) {
        super(message, cause);
    }


    public ParameterValidationFailException(final Throwable cause) {
        super(cause);
    }


}
