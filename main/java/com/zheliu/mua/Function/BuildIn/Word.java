package com.zheliu.mua.Function.BuildIn;

import com.zheliu.mua.Context;
import com.zheliu.mua.Exception.MuaException;
import com.zheliu.mua.Exception.RuntimeMuaException;
import com.zheliu.mua.Function.BuiltInFunc;
import com.zheliu.mua.Variable.*;

/**
 * Created by zcy on 27/09/2017.
 */
public class Word extends BuiltInFunc {

    private static Class[] argTypes = {MuaLiteral.class, MuaVariable.class};

    public Class[] getArgTypes() {
        return argTypes;
    }

    public MuaVariable run(Context ctx, MuaVariable[] args) throws MuaException {
        String word = ((MuaLiteral)args[0]).getValue();
        if( args[1] instanceof MuaInt || args[1] instanceof MuaDouble || args[1] instanceof MuaBoolean){
            return new MuaLiteral(word + args[1].toString());
        }
        else if(args[1] instanceof MuaLiteral){
            return new MuaLiteral( word + ((MuaLiteral)args[1]).getValue());
        }
        else throw new RuntimeMuaException("The type of arguments of function `word` is incorrect.");
    }
}
