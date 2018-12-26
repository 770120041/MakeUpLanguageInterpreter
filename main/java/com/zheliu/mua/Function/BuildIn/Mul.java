package com.zheliu.mua.Function.BuildIn;

import com.zheliu.mua.Exception.MuaException;
import com.zheliu.mua.Function.BuiltInFunc;
import com.zheliu.mua.Context;
import com.zheliu.mua.Util.OperatorLogistic;
import com.zheliu.mua.Variable.MuaNumber;
import com.zheliu.mua.Variable.MuaVariable;

/**
 * Created by zcy on 27/09/2017.
 */
public class Mul extends BuiltInFunc {

    private static Class[] argTypes = {MuaNumber.class, MuaNumber.class};

    public Class[] getArgTypes() {
        return argTypes;
    }

    public MuaVariable run(Context ctx, MuaVariable[] args) throws MuaException {
        return OperatorLogistic.solve(args[0], args[1], new OperatorLogistic.Executor() {
            public int run(int a, int b) {
                return a * b;
            }

            public double run(double a, double b) {
                return a * b;
            }
        });
    }
}
