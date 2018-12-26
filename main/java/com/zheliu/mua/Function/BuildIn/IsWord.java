package com.zheliu.mua.Function.BuildIn;

import com.zheliu.mua.Context;
import com.zheliu.mua.Exception.MuaException;
import com.zheliu.mua.Function.BuiltInFunc;
import com.zheliu.mua.Variable.MuaBoolean;
import com.zheliu.mua.Variable.MuaLiteral;
import com.zheliu.mua.Variable.MuaVariable;

/**
 * Created by zcy on 27/09/2017.
 */
public class IsWord extends BuiltInFunc {

    private static Class[] argTypes = {MuaVariable.class};

    public Class[] getArgTypes() {
        return argTypes;
    }

    public MuaVariable run(Context ctx, MuaVariable[] args) throws MuaException {
        return new MuaBoolean(args[0] instanceof MuaLiteral);
    }
}
