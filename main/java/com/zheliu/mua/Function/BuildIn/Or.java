package com.zheliu.mua.Function.BuildIn;

import com.zheliu.mua.Context;
import com.zheliu.mua.Exception.MuaException;
import com.zheliu.mua.Function.BuiltInFunc;
import com.zheliu.mua.Variable.MuaBoolean;
import com.zheliu.mua.Variable.MuaVariable;

/**
 * Created by zcy on 27/09/2017.
 */
public class Or extends BuiltInFunc {

    private static Class[] argTypes = {MuaBoolean.class, MuaBoolean.class};

    public Class[] getArgTypes() {
        return argTypes;
    }

    public MuaVariable run(Context ctx, MuaVariable[] args) throws MuaException {
        return new MuaBoolean(((MuaBoolean)args[0]).getValue() || ((MuaBoolean)args[1]).getValue());
    }
}

