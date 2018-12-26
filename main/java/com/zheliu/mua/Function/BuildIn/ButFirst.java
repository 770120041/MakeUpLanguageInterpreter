package com.zheliu.mua.Function.BuildIn;

import com.zheliu.mua.Exception.MuaException;
import com.zheliu.mua.Exception.RuntimeMuaException;
import com.zheliu.mua.Function.BuiltInFunc;
import com.zheliu.mua.Context;
import com.zheliu.mua.Variable.MuaList;
import com.zheliu.mua.Variable.MuaLiteral;
import com.zheliu.mua.Variable.MuaVariable;

import java.util.ArrayList;


public class ButFirst extends BuiltInFunc {

    private static Class[] argTypes = {MuaVariable.class};

    public Class[] getArgTypes() {
        return argTypes;
    }

    public MuaVariable run(Context ctx, MuaVariable[] args) throws MuaException {
        try {
            if (args[0] instanceof MuaLiteral){
                String value = ((MuaLiteral) args[0]).getValue();
                return new MuaLiteral(value.substring(1, value.length()));
            }
            else if (args[0] instanceof MuaList){
                ArrayList<MuaVariable> value = ((MuaList) args[0]).getValue();
                ArrayList<MuaVariable> result = new ArrayList<MuaVariable>();
                result.addAll(value.subList(1, value.size()));
                return new MuaList(result);
            }
            else throw new RuntimeMuaException("The type of arguments of function `butfirst` is incorrect.");
        }
        catch (RuntimeException e){
            throw new RuntimeMuaException("Command `butfirst` cannot be applied for NULL list.");
        }
    }
}
