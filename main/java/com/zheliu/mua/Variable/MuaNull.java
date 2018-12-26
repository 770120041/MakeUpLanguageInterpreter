package com.zheliu.mua.Variable;

/**
 * Used for function without return value
 */
public class MuaNull implements MuaVariable {

    @Override
    public String toString() {
        return "null";
    }

    public String toRawString() {
        return toString();
    }
}
