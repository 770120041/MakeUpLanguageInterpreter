package com.zheliu.mua.Function.BuildIn;

import com.zheliu.mua.Exception.MuaException;
import com.zheliu.mua.Function.BuiltInFunc;
import com.zheliu.mua.Context;
import com.zheliu.mua.Variable.MuaInt;
import com.zheliu.mua.Variable.MuaNumber;
import com.zheliu.mua.Variable.MuaDouble;
import com.zheliu.mua.Variable.MuaVariable;

public class Int extends BuiltInFunc {

    private static Class[] argTypes = {MuaNumber.class};

    public Class[] getArgTypes() {
        return argTypes;
    }

    public MuaVariable run(Context ctx, MuaVariable[] args) throws MuaException {
        if( args[0] instanceof MuaInt) return args[0];
        else{
            MuaDouble real = (MuaDouble) args[0];
            Integer i = (int)real.getValue();
            return new MuaInt(i);
        }
    }
}
