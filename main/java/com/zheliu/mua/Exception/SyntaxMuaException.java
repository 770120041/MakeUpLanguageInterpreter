package com.zheliu.mua.Exception;


public class SyntaxMuaException extends MuaException {

    public SyntaxMuaException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return "Syntax MuaException: " + message;
    }
}
