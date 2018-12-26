package com.zheliu.mua.Function.BuildIn;

import com.zheliu.mua.Context;
import com.zheliu.mua.Exception.MuaException;
import com.zheliu.mua.Exception.RuntimeMuaException;
import com.zheliu.mua.Function.BuiltInFunc;
import com.zheliu.mua.Variable.MuaList;
import com.zheliu.mua.Variable.MuaLiteral;
import com.zheliu.mua.Variable.MuaVariable;

import java.util.ArrayList;


public class ButLast extends BuiltInFunc {

    private static Class[] argTypes = {MuaVariable.class};

    public Class[] getArgTypes() {
        return argTypes;
    }

    public MuaVariable run(Context ctx, MuaVariable[] args) throws MuaException {
        try {
            if (args[0] instanceof MuaLiteral){
                String value = ((MuaLiteral) args[0]).getValue();
                return new MuaLiteral(value.substring(0, value.length() - 1));
            }
            else if (args[0] instanceof MuaList){
                ArrayList<MuaVariable> value = ((MuaList) args[0]).getValue();
                ArrayList<MuaVariable> result = new ArrayList<MuaVariable>();
                result.addAll(value.subList(0, value.size() - 1));
                return new MuaList(result);
            }
            else throw new RuntimeMuaException("The type of arguments of function `butlast` is incorrect.");
        }
        catch (RuntimeException e){
            throw new RuntimeMuaException("Command `butlast` cannot be applied for NULL list.");
        }
    }
}
