package com.zheliu.mua.Function;

import com.zheliu.mua.Exception.MuaException;
import com.zheliu.mua.Context;
import com.zheliu.mua.Variable.*;

import java.util.ArrayList;


public class CustomFunction implements Function {

    private ArrayList<MuaVariable> program;
    private ArrayList<String> argList;
    private Class[] argTypes;
    private MuaList muaList;
    /*
        Program: the funcion body
        argList: the argument List for this function
        MuaList: the list which creates this customFunction
    */
    public CustomFunction(ArrayList<MuaVariable> argList, ArrayList<MuaVariable> program, MuaList muaList) {
        this.muaList = muaList;
        this.program = program;
        this.argList = new ArrayList<String>();
        argTypes = new Class[argList.size()];
        for(int i = 0; i < argList.size(); i++){
            if( argList.get(i) instanceof MuaLiteral){
                this.argList.add(((MuaLiteral)argList.get(i)).getValue());
            }
            else{
                this.argList.add(((MuaWord)argList.get(i)).getValue());
            }
            argTypes[i] = MuaVariable.class;
        }
    }

    public MuaVariable run(Context ctx, MuaVariable[] args) throws MuaException {
        ctx.getSymbolTable().createSubSymbolTable();
        for(int i = 0 ; i < argList.size(); i++){
            ctx.getSymbolTable().setSymbol(argList.get(i), args[i]);
        }
        MuaVariable savedOutputValue = ctx.getOutputValue();
        ctx.setOutputValue(new MuaNull());
        ctx.run(program, 1);
        MuaVariable returnValue = ctx.getOutputValue();
        ctx.setOutputValue(savedOutputValue);
        ctx.getSymbolTable().removeSubSymbolTable();
        return returnValue;
    }

    public Class[] getArgTypes() {
        return argTypes;
    }

    public String getName() {
        return "Lambda: " + muaList.toString();
    }

}
