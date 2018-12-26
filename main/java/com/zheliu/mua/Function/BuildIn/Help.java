package com.zheliu.mua.Function.BuildIn;

import com.zheliu.mua.Context;
import com.zheliu.mua.Exception.MuaException;
import com.zheliu.mua.Function.BuiltInFunc;
import com.zheliu.mua.Variable.MuaVariable;

public class Help extends BuiltInFunc {
    @Override
    public Class[] getArgTypes() {
        return new Class[0];
    }

    @Override
    public MuaVariable run(Context ctx, MuaVariable[] args) throws MuaException {
        Usage usage =new Usage();
        return usage.run(ctx,args);
    }
}
