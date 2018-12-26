package com.zheliu.mua.Function.BuildIn;

import com.zheliu.mua.Context;
import com.zheliu.mua.Exception.MuaException;
import com.zheliu.mua.Exception.RuntimeMuaException;
import com.zheliu.mua.Function.BuiltInFunc;
import com.zheliu.mua.Variable.MuaList;
import com.zheliu.mua.Variable.MuaLiteral;
import com.zheliu.mua.Variable.MuaVariable;

/**
 * Created by zcy on 27/09/2017.
 */
public class First extends BuiltInFunc {

    private static Class[] argTypes = {MuaVariable.class};

    public Class[] getArgTypes() {
        return argTypes;
    }

    public MuaVariable run(Context ctx, MuaVariable[] args) throws MuaException {
        try {
            if (args[0] instanceof MuaLiteral) return new MuaLiteral(((MuaLiteral) args[0]).getValue().substring(0, 1));
            else if (args[0] instanceof MuaList)return ((MuaList) args[0]).getValue().get(0);
            else throw new RuntimeMuaException("The type of arguments of function `first` is incorrect.");
        }
        catch (IndexOutOfBoundsException e){
            throw new RuntimeMuaException("Command `first` cannot be applied for NULL list.");
        }
    }
}
