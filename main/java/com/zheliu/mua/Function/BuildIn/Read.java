package com.zheliu.mua.Function.BuildIn;

import com.zheliu.mua.Exception.MuaException;
import com.zheliu.mua.Function.BuiltInFunc;
import com.zheliu.mua.Context;
import com.zheliu.mua.Variable.MuaInt;
import com.zheliu.mua.Variable.MuaLiteral;
import com.zheliu.mua.Variable.MuaDouble;
import com.zheliu.mua.Variable.MuaVariable;

import java.util.Scanner;


public class Read extends BuiltInFunc {

    private static Class[] argTypes = {};

    public Class[] getArgTypes() {
        return argTypes;
    }

    public MuaVariable run(Context ctx, MuaVariable[] args) throws MuaException {
        Scanner scanner = new Scanner(System.in);
        String value = scanner.next();
        try{
            return new MuaInt(Integer.parseInt(value));
        }
        catch (Exception e){
            try {
                return new MuaDouble(Double.parseDouble(value));
            }catch (Exception ex){
                return new MuaLiteral(value);
            }
        }
    }
}
