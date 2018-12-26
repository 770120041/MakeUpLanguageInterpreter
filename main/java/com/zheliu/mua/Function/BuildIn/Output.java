package com.zheliu.mua.Function.BuildIn;

import com.zheliu.mua.Exception.MuaException;
import com.zheliu.mua.Function.BuiltInFunc;
import com.zheliu.mua.Context;
import com.zheliu.mua.Variable.MuaNull;
import com.zheliu.mua.Variable.MuaVariable;

/**
 * Created by zcy on 27/11/2017.
 */
public class Output extends BuiltInFunc {

    private static Class[] argTypes = { MuaVariable.class };

    public Class[] getArgTypes() {
        return argTypes;
    }

    public MuaVariable run(Context ctx, MuaVariable[] args) throws MuaException {
        ctx.setOutputValue(args[0]);
        return new MuaNull();
    }
}
