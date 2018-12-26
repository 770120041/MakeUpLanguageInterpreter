package com.zheliu.mua.Function;

import java.lang.reflect.Constructor;
import java.util.HashMap;

import com.zheliu.mua.Variable.MuaFunction;
import com.zheliu.mua.Variable.MuaVariable;
import com.zheliu.mua.Function.BuildIn.*;


public class BuiltInFuncLoader {

    private static Class[] functions = {
            Add.class,
            And.class,
            ButFirst.class,
            ButLast.class,
            Div.class,
            Eq.class,
            Erall.class,
            Erase.class,
            Export.class,
            False.class,
            First.class,
            Gt.class,
            Gte.class,
            If.class,
            IfFalse.class,
            IfTrue.class,
            Int.class,
            IsBool.class,
            IsEmpty.class,
            IsList.class,
            IsName.class,
            IsNumber.class,
            IsWord.class,
            Item.class,
            Join.class,
            Last.class,
            List.class,
            Load.class,
            Lt.class,
            Lte.class,
            Make.class,
            Mod.class,
            Mul.class,
            Not.class,
            Or.class,
            Output.class,
            Poall.class,
            Print.class,
            Random.class,
            Read.class,
            ReadList.class,
            Repeat.class,
            Run.class,
            Save.class,
            Sentence.class,
            Sqrt.class,
            Stop.class,
            Sub.class,
            Test.class,
            Thing.class,
            True.class,
            Wait.class,
            Word.class,
            Usage.class,
            Help.class,
    };

    public static void load(HashMap<String, MuaVariable> symbolTable){
        try {
            for (Class x : functions) {
                Constructor constructor = x.getConstructor();
                Function f = (Function) constructor.newInstance();
                symbolTable.put(x.getSimpleName().toLowerCase(),new MuaFunction(f));
//                symbolTable.put(x.getSimpleName().toLowerCase(), new MuaFunction((Function)x.newInstance()));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
