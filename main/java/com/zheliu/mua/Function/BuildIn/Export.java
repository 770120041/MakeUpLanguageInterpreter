package com.zheliu.mua.Function.BuildIn;

import com.zheliu.mua.Exception.MuaException;
import com.zheliu.mua.Exception.RuntimeMuaException;
import com.zheliu.mua.Function.BuiltInFunc;
import com.zheliu.mua.Context;
import com.zheliu.mua.Variable.MuaLiteral;
import com.zheliu.mua.Variable.MuaNull;
import com.zheliu.mua.Variable.MuaVariable;

public class Export extends BuiltInFunc {

    private static Class[] argTypes = {MuaLiteral.class};

    public Class[] getArgTypes() {
        return argTypes;
    }

    public MuaVariable run(Context ctx, MuaVariable[] args) throws MuaException {
        String name = ((MuaLiteral) args[0]).getValue();
        MuaVariable muaVariable = ctx.getSymbolTable().getSymbol(name);
        if( muaVariable == null ){
            throw new RuntimeMuaException(((MuaLiteral) args[0]).getValue() + " is undefined." );
        }
        ctx.getSymbolTable().setGlobalSymbol(name, muaVariable);
        return new MuaNull();
    }
}
