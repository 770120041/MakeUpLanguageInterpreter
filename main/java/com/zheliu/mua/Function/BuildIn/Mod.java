package com.zheliu.mua.Function.BuildIn;

import com.zheliu.mua.Context;
import com.zheliu.mua.Exception.MuaException;
import com.zheliu.mua.Exception.RuntimeMuaException;
import com.zheliu.mua.Function.BuiltInFunc;
import com.zheliu.mua.Variable.MuaInt;
import com.zheliu.mua.Variable.MuaNumber;
import com.zheliu.mua.Variable.MuaVariable;

/**
 * Created by zcy on 27/09/2017.
 */
public class Mod extends BuiltInFunc {

    private static Class[] argTypes = {MuaNumber.class, MuaNumber.class};

    public Class[] getArgTypes() {
        return argTypes;
    }

    public MuaVariable run(Context ctx, MuaVariable[] args) throws MuaException {
        if( args[0] instanceof MuaInt && args[1] instanceof MuaInt){
            return new MuaInt(((MuaInt)args[0]).getValue() % ((MuaInt)args[1]).getValue());
        }
        throw new RuntimeMuaException("mod only support integers");
    }
}
