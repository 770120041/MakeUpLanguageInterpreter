package com.zheliu.mua.Variable;

public class MuaBoolean implements MuaVariable {

    private boolean value;

    public MuaBoolean(boolean value) {
        this.value = value;
    }

    public boolean getValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return Boolean.toString(value);
    }

    public String toRawString() {
        return toString();
    }
}
