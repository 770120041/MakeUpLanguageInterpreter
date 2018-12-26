package com.zheliu.mua.Function;

/*
    make it abstrct to have getName body
 */
public abstract class BuiltInFunc implements Function {

    public String getName() {
        return "BuiltInFunction: " + this.getClass().getSimpleName();
    }

}
