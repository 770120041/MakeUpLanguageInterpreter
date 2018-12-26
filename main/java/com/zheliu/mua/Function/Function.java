package com.zheliu.mua.Function;

import com.zheliu.mua.Context;
import com.zheliu.mua.Exception.MuaException;
import com.zheliu.mua.Variable.MuaVariable;


public interface Function {

    Class[] getArgTypes();
    MuaVariable run(Context ctx, MuaVariable[] args) throws MuaException;
    String getName();
}
