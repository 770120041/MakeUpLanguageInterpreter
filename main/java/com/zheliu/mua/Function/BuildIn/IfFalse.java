package com.zheliu.mua.Function.BuildIn;

import com.zheliu.mua.Context;
import com.zheliu.mua.Exception.MuaException;
import com.zheliu.mua.Function.BuiltInFunc;
import com.zheliu.mua.Variable.MuaList;
import com.zheliu.mua.Variable.MuaNull;
import com.zheliu.mua.Variable.MuaVariable;

/**
 * Created by zcy on 27/09/2017.
 */
public class IfFalse extends BuiltInFunc {

    private static Class[] argTypes = {MuaList.class};

    public Class[] getArgTypes() {
        return argTypes;
    }

    public MuaVariable run(Context ctx, MuaVariable[] args) throws MuaException {
        if(!ctx.getTestFlag()){
            return ctx.run(((MuaList)args[0]).getValue(), 1);
        }
        return new MuaNull();
    }
}
