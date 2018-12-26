package com.zheliu.mua.Variable;


public class MuaInt implements MuaNumber {

    private int value;

    public MuaInt(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }

    public String toRawString() {
        return toString();
    }
}
