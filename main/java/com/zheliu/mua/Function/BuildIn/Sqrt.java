package com.zheliu.mua.Function.BuildIn;

import com.zheliu.mua.Exception.MuaException;
import com.zheliu.mua.Function.BuiltInFunc;
import com.zheliu.mua.Context;
import com.zheliu.mua.Variable.MuaInt;
import com.zheliu.mua.Variable.MuaNumber;
import com.zheliu.mua.Variable.MuaDouble;
import com.zheliu.mua.Variable.MuaVariable;

/**
 * Created by zcy on 27/09/2017.
 */
public class Sqrt extends BuiltInFunc {

    private static Class[] argTypes = {MuaNumber.class};

    public Class[] getArgTypes() {
        return argTypes;
    }

    public MuaVariable run(Context ctx, MuaVariable[] args) throws MuaException {
        if( args[0] instanceof MuaInt){
            return new MuaDouble(Math.sqrt(((MuaInt)args[0]).getValue()));
        }
        else{
            return new MuaDouble(Math.sqrt(((MuaDouble)args[0]).getValue()));
        }
    }
}


