package com.zheliu.mua.Function.BuildIn;

import com.zheliu.mua.Context;
import com.zheliu.mua.Exception.MuaException;
import com.zheliu.mua.Exception.RuntimeMuaException;
import com.zheliu.mua.Function.BuiltInFunc;
import com.zheliu.mua.Variable.MuaList;
import com.zheliu.mua.Variable.MuaLiteral;
import com.zheliu.mua.Variable.MuaVariable;

import java.util.ArrayList;

/**
 * Created by zcy on 27/09/2017.
 */
public class Last extends BuiltInFunc {

    private static Class[] argTypes = {MuaVariable.class};

    public Class[] getArgTypes() {
        return argTypes;
    }

    public MuaVariable run(Context ctx, MuaVariable[] args) throws MuaException {
        try {
            if (args[0] instanceof MuaLiteral){
                String value = ((MuaLiteral) args[0]).getValue();
                return new MuaLiteral(value.substring(value.length() - 1, value.length()));
            }
            else if (args[0] instanceof MuaList){
                ArrayList<MuaVariable> value = ((MuaList) args[0]).getValue();
                return value.get(value.size() - 1);
            }
            else throw new RuntimeMuaException("The type of arguments of function `last` is incorrect.");
        }
        catch (IndexOutOfBoundsException e){
            throw new RuntimeMuaException("Command `last` cannot be applied for NULL list.");
        }
    }
}
