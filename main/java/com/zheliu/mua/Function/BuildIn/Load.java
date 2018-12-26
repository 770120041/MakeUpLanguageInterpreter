package com.zheliu.mua.Function.BuildIn;

import com.zheliu.mua.Exception.MuaException;
import com.zheliu.mua.Exception.RuntimeMuaException;
import com.zheliu.mua.Function.BuiltInFunc;
import com.zheliu.mua.Context;
import com.zheliu.mua.Tokenizer;
import com.zheliu.mua.Variable.MuaLiteral;
import com.zheliu.mua.Variable.MuaNull;
import com.zheliu.mua.Variable.MuaVariable;

import java.io.BufferedReader;
import java.io.FileReader;

public class Load extends BuiltInFunc {

    private static Class[] argTypes = {MuaLiteral.class};

    public Class[] getArgTypes() {
        return argTypes;
    }


    /*
        Load from file
     */
    public MuaVariable run(Context ctx, MuaVariable[] args) throws MuaException {
        String name = ((MuaLiteral) args[0]).getValue();
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader fileReader = new BufferedReader(new FileReader(name));
            String line = fileReader.readLine();
            while(line != null){
                sb.append(line);
                sb.append("\n");
                line = fileReader.readLine();
            }

            ctx.run(Tokenizer.getInstance().tokenize(sb.toString(),false),1);
        }catch (Exception e){
            throw new RuntimeMuaException(e.getMessage());
        }
        return new MuaNull();

    }
}

