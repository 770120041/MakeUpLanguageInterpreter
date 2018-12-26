package com.zheliu.mua.Function.BuildIn;

import com.zheliu.mua.Exception.MuaException;
import com.zheliu.mua.Exception.RuntimeMuaException;
import com.zheliu.mua.Function.BuiltInFunc;
import com.zheliu.mua.Context;
import com.zheliu.mua.Variable.MuaBoolean;
import com.zheliu.mua.Variable.MuaList;
import com.zheliu.mua.Variable.MuaLiteral;
import com.zheliu.mua.Variable.MuaVariable;

/**
 * Created by zcy on 27/09/2017.
 */
public class IsEmpty extends BuiltInFunc {

    private static Class[] argTypes = {MuaVariable.class};

    public Class[] getArgTypes() {
        return argTypes;
    }

    public MuaVariable run(Context ctx, MuaVariable[] args) throws MuaException {
        if( args[0] instanceof MuaLiteral) return new MuaBoolean(((MuaLiteral)args[0]).getValue().equals(""));
        else if( args[0] instanceof MuaList)return new MuaBoolean(((MuaList)args[0]).getValue().size() == 0);
        else throw new RuntimeMuaException("Illegal Type");
    }
}

