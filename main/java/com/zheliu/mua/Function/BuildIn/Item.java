package com.zheliu.mua.Function.BuildIn;

import com.zheliu.mua.Exception.MuaException;
import com.zheliu.mua.Exception.RuntimeMuaException;
import com.zheliu.mua.Function.BuiltInFunc;
import com.zheliu.mua.Context;
import com.zheliu.mua.Variable.MuaInt;
import com.zheliu.mua.Variable.MuaList;
import com.zheliu.mua.Variable.MuaLiteral;
import com.zheliu.mua.Variable.MuaVariable;

import java.util.ArrayList;

/**
 * Created by zcy on 27/09/2017.
 */
public class Item extends BuiltInFunc {

    private static Class[] argTypes = {MuaInt.class, MuaVariable.class};

    public Class[] getArgTypes() {
        return argTypes;
    }

    public MuaVariable run(Context ctx, MuaVariable[] args) throws MuaException {
        try {
            if (args[1] instanceof MuaLiteral){
                String value = ((MuaLiteral) args[1]).getValue();
                return new MuaLiteral(value.charAt(((MuaInt) args[0]).getValue()) + "");
            }
            else if (args[1] instanceof MuaList){
                ArrayList<MuaVariable> value = ((MuaList) args[1]).getValue();
                return value.get(((MuaInt) args[0]).getValue());
            }
            else throw new RuntimeMuaException("Illegal Type");
        }
        catch (RuntimeException e){
            throw new RuntimeMuaException("The index exceed the size of list or string");
        }
    }
}
