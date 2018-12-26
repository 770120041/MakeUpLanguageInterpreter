package com.zheliu.mua.Function.BuildIn;

import com.zheliu.mua.Context;
import com.zheliu.mua.Exception.MuaException;
import com.zheliu.mua.Function.BuiltInFunc;
import com.zheliu.mua.Variable.MuaVariable;

/**
 * Created by zcy on 22/11/2017.
 */
public class MValueFunc extends BuiltInFunc {

    private static Class[] argTypes = {};

    public Class[] getArgTypes() {
        return argTypes;
    }

    MuaVariable value;

    public MValueFunc(MuaVariable value) {
        this.value = value;
    }

    public MuaVariable run(Context ctx, MuaVariable[] args) throws MuaException {
        return value;
    }

}