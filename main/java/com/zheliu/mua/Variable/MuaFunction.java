package com.zheliu.mua.Variable;

import com.zheliu.mua.Function.Function;


public class MuaFunction implements MuaVariable {

    private Function function;

    public MuaFunction(Function function) {
        this.function = function;
    }

    public Function getFunction() {
        return function;
    }

    public void setFunction(Function function) {
        this.function = function;
    }

    @Override
    public String toString() {
        return "[Function]";
    }

    public String toRawString() {
        return toString();
    }
}
