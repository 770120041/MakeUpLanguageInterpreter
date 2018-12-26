package com.zheliu.mua.Variable;


public class MuaDouble implements MuaNumber {

    private double value;

    public MuaDouble(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return Double.toString(value);
    }

    public String toRawString() {
        return toString();
    }
}
