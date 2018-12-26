package com.zheliu.mua.Function.BuildIn;

import com.zheliu.mua.Context;
import com.zheliu.mua.Exception.MuaException;
import com.zheliu.mua.Function.BuiltInFunc;
import com.zheliu.mua.Variable.MuaList;
import com.zheliu.mua.Variable.MuaVariable;


public class Run extends BuiltInFunc {

    private static Class[] argTypes = {MuaList.class};

    public Class[] getArgTypes() {
        return argTypes;
    }

    public MuaVariable run(Context ctx, MuaVariable[] args) throws MuaException {
        return ctx.run(((MuaList)args[0]).getValue(), 1);
    }
}

