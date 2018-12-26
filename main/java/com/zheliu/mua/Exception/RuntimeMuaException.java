package com.zheliu.mua.Exception;


public class RuntimeMuaException extends MuaException {

    public RuntimeMuaException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return "Context MuaException: " + message;
    }
}
