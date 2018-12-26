package com.zheliu.mua.Variable;

//used as a literal, not a keyword
public class MuaLiteral implements MuaVariable {

    private String value;

    public MuaLiteral(String value) {
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
        return "\"" + toString();
    }
}
