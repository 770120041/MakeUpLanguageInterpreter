package com.zheliu.mua.Exception;


public class MuaException extends Throwable{
    String message;

    public MuaException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }


}
