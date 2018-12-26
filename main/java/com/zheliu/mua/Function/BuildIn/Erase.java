package com.zheliu.mua.Function.BuildIn;

import com.zheliu.mua.Exception.MuaException;
import com.zheliu.mua.Function.BuiltInFunc;
import com.zheliu.mua.Context;
import com.zheliu.mua.Variable.MuaLiteral;
import com.zheliu.mua.Variable.MuaNull;
import com.zheliu.mua.Variable.MuaVariable;


public class Erase extends BuiltInFunc {

    private static Class[] argTypes = {MuaLiteral.class};

    public Class[] getArgTypes() {
        return argTypes;
    }

    public MuaVariable run(Context ctx, MuaVariable[] args) throws MuaException {
        ctx.getSymbolTable().removeSymbol(((MuaLiteral)args[0]).getValue());

        return new MuaNull();
    }
}

