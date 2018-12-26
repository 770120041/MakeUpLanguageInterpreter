package com.zheliu.mua.Function.BuildIn;

import com.zheliu.mua.Context;
import com.zheliu.mua.Exception.MuaException;
import com.zheliu.mua.Function.BuiltInFunc;
import com.zheliu.mua.Variable.MuaInt;
import com.zheliu.mua.Variable.MuaNumber;
import com.zheliu.mua.Variable.MuaDouble;
import com.zheliu.mua.Variable.MuaVariable;

/**
 * Created by zcy on 27/09/2017.
 */
public class Random extends BuiltInFunc {

    private static Class[] argTypes = {MuaNumber.class};

    public Class[] getArgTypes() {
        return argTypes;
    }

    public MuaVariable run(Context ctx, MuaVariable[] args) throws MuaException {
        java.util.Random random = new java.util.Random();
        if( args[0] instanceof MuaInt){
            int value = ((MuaInt)args[0]).getValue();
            return new MuaInt(random.nextInt(value));
        }
        else{
            double value = ((MuaDouble)args[0]).getValue();
            return new MuaDouble(random.nextDouble() * value);
        }
    }
}

