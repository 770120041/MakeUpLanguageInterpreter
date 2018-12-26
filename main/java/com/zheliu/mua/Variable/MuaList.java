package com.zheliu.mua.Variable;

import com.zheliu.mua.Function.CustomFunction;

import java.util.ArrayList;


public class MuaList implements MuaVariable {

    private ArrayList<MuaVariable> value;

    private CustomFunction customFunction = null;

    public MuaList(ArrayList<MuaVariable> value) {
        this.value = value;
        if(value.size() >= 2){
            MuaVariable muaVariable1 = value.get(0);
            MuaVariable muaVariable2 = value.get(1);
            if( muaVariable1 instanceof MuaList && muaVariable2 instanceof MuaList){
                boolean isFunc = true;
                for(MuaVariable muaVariable : ((MuaList) muaVariable1).getValue()){
                    if(!(muaVariable instanceof MuaLiteral || muaVariable instanceof MuaWord)) isFunc = false;
                }
                if(isFunc){
                    customFunction = new CustomFunction(((MuaList) muaVariable1).getValue(), ((MuaList) muaVariable2).getValue(), this);
                }
            }
        }

    }

    public CustomFunction getCustomFunction() {
        return customFunction;
    }

    public ArrayList<MuaVariable> getValue() {
        return value;
    }

    public void setValue(ArrayList<MuaVariable> value) {
        this.value = value;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        for(MuaVariable muaVariable : value){
            stringBuilder.append(" ");
            stringBuilder.append(muaVariable.toString());
        }
        if(value.size() != 0)stringBuilder.append(" ");
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    public String toRawString() {
        return toString();
    }
}
