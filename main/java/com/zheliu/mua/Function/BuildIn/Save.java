package com.zheliu.mua.Function.BuildIn;

import com.zheliu.mua.Context;
import com.zheliu.mua.Exception.MuaException;
import com.zheliu.mua.Exception.RuntimeMuaException;
import com.zheliu.mua.Function.BuiltInFunc;
import com.zheliu.mua.Variable.MuaLiteral;
import com.zheliu.mua.Variable.MuaNull;
import com.zheliu.mua.Variable.MuaVariable;

import java.io.FileWriter;

public class Save extends BuiltInFunc {

    private static Class[] argTypes = {MuaLiteral.class};

    public Class[] getArgTypes() {
        return argTypes;
    }

    public MuaVariable run(Context ctx, MuaVariable[] args) throws MuaException {
        String name = ((MuaLiteral) args[0]).getValue();
        try {
            FileWriter fileWriter = new FileWriter(name);
            fileWriter.write(ctx.getSymbolTable().toString());
            fileWriter.close();
        }catch (Exception e){
            throw new RuntimeMuaException(e.getMessage());
        }
        return new MuaNull();
    }
}
