package com.zheliu.mua.Variable;

/*
    used as keyword or function names
 */
public class MuaWord implements MuaVariable {

    private String value;

    public MuaWord(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    public String toRawString() {
        return toString();
    }
}
