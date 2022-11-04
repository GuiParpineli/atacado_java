package com.wf.apiwf.exceptions;

public class JwtIncorrectException extends Exception{

    public JwtIncorrectException(String message) {
        super(message);
    }
}
