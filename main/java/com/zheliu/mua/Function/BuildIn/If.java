package com.zheliu.mua.Function.BuildIn;

import com.zheliu.mua.Exception.MuaException;
import com.zheliu.mua.Function.BuiltInFunc;
import com.zheliu.mua.Context;
import com.zheliu.mua.Variable.MuaBoolean;
import com.zheliu.mua.Variable.MuaList;
import com.zheliu.mua.Variable.MuaVariable;

/**
 * Created by zcy on 27/11/2017.
 */
public class If extends BuiltInFunc {

    private static Class[] argTypes = {MuaBoolean.class, MuaList.class, MuaList.class};

    public Class[] getArgTypes() {
        return argTypes;
    }

    public MuaVariable run(Context ctx, MuaVariable[] args) throws MuaException {
        if(((MuaBoolean)args[0]).getValue()){
            return ctx.run(((MuaList)args[1]).getValue(), 1);
        }
        else{
            return ctx.run(((MuaList)args[2]).getValue(), 1);
        }
    }
}
