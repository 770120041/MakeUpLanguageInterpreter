package com.zheliu.mua.Function.BuildIn;

import com.zheliu.mua.Context;
import com.zheliu.mua.Exception.MuaException;
import com.zheliu.mua.Function.BuiltInFunc;
import com.zheliu.mua.Util.OperatorLogistic;
import com.zheliu.mua.Variable.MuaVariable;

public class Lte extends BuiltInFunc {

    private static Class[] argTypes = {MuaVariable.class, MuaVariable.class};

    public Class[] getArgTypes() {
        return argTypes;
    }

    public MuaVariable run(Context ctx, MuaVariable[] args) throws MuaException {
        return OperatorLogistic.solve(args[0], args[1], new OperatorLogistic.Comparer() {
            public boolean run(int a, int b) {
                return a <= b;
            }

            public boolean run(double a, double b) {
                return a <= b;
            }

            public boolean run(String a, String b) {
                return a.compareTo(b) <= 0;
            }
        });
    }
}
