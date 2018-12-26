package com.zheliu.mua.Variable;


public class MuaOperator implements MuaVariable {

    public static final char LTE = 'l';
    public static final char GTE = 'g';

    public char value;

    public MuaOperator(char value) {
        this.value = value;
    }

    public String toRawString() {
        return toString();
    }

    @Override
    public String toString() {
        return Character.toString(value);
    }
}
