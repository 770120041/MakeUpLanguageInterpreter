package com.zheliu.mua.Function.BuildIn;

import com.zheliu.mua.Exception.MuaException;
import com.zheliu.mua.Function.BuiltInFunc;
import com.zheliu.mua.Context;
import com.zheliu.mua.Variable.MuaList;
import com.zheliu.mua.Variable.MuaVariable;

import java.util.ArrayList;


public class List extends BuiltInFunc {

    private static Class[] argTypes = {MuaList.class, MuaList.class};

    public Class[] getArgTypes() {
        return argTypes;
    }

    public MuaVariable run(Context ctx, MuaVariable[] args) throws MuaException {
        MuaList value = new MuaList(new ArrayList<MuaVariable>());
        value.getValue().addAll(((MuaList)args[0]).getValue());
        value.getValue().addAll(((MuaList)args[1]).getValue());
        return value;
    }
}

