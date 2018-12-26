package com.zheliu.mua.Function.BuildIn;

import com.zheliu.mua.Exception.MuaException;
import com.zheliu.mua.Function.BuiltInFunc;
import com.zheliu.mua.Context;
import com.zheliu.mua.Variable.MuaInt;
import com.zheliu.mua.Variable.MuaNull;
import com.zheliu.mua.Variable.MuaVariable;

/**
 * Created by zcy on 27/09/2017.
 */
public class Wait extends BuiltInFunc {

    private static Class[] argTypes = {MuaInt.class};

    public Class[] getArgTypes() {
        return argTypes;
    }

    public MuaVariable run(Context ctx, MuaVariable[] args) throws MuaException {
        try {
            Thread.sleep(((MuaInt) args[0]).getValue() * 1000);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new MuaNull();
    }
}

