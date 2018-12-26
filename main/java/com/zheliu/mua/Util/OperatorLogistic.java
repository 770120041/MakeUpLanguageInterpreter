package com.zheliu.mua.Util;

import com.zheliu.mua.Exception.MuaException;
import com.zheliu.mua.Exception.RuntimeMuaException;
import com.zheliu.mua.Variable.*;


public class OperatorLogistic {

    public interface Executor{
        int run(int a, int b);
        double run(double a, double b);
    }

    public interface Comparer{
        boolean run(int a, int b);
        boolean run(double a, double b);
        boolean run(String a, String b);
    }

    public static MuaNumber solve(MuaVariable a, MuaVariable b, Executor executor) throws MuaException {
        if( a instanceof MuaInt && b instanceof MuaInt)
            return new MuaInt(executor.run(((MuaInt)a).getValue(), ((MuaInt)b).getValue()));
        else if( a instanceof MuaInt && b instanceof MuaDouble)
            return new MuaDouble(executor.run(((MuaInt)a).getValue(), ((MuaDouble)b).getValue()));
        else if( a instanceof MuaDouble && b instanceof MuaInt)
            return new MuaDouble(executor.run(((MuaDouble)a).getValue(), ((MuaInt)b).getValue()));
        else if( a instanceof MuaDouble && b instanceof MuaDouble)
            return new MuaDouble(executor.run(((MuaDouble)a).getValue(), ((MuaDouble)b).getValue()));
        else
            throw new RuntimeMuaException("Illegal Type to calculate");
    }

    public static MuaBoolean solve(MuaVariable a, MuaVariable b, Comparer comparer) throws MuaException {
        if( a instanceof MuaInt && b instanceof MuaInt)
            return new MuaBoolean(comparer.run(((MuaInt)a).getValue(), ((MuaInt)b).getValue()));
        else if( a instanceof MuaInt && b instanceof MuaDouble)
            return new MuaBoolean(comparer.run(((MuaInt)a).getValue(), ((MuaDouble)b).getValue()));
        else if( a instanceof MuaDouble && b instanceof MuaInt)
            return new MuaBoolean(comparer.run(((MuaDouble)a).getValue(), ((MuaInt)b).getValue()));
        else if( a instanceof MuaDouble && b instanceof MuaDouble)
            return new MuaBoolean(comparer.run(((MuaDouble)a).getValue(), ((MuaDouble)b).getValue()));
        else if( a instanceof MuaLiteral && b instanceof MuaInt)
            return new MuaBoolean(comparer.run(((MuaLiteral)a).getValue(), Integer.toString(((MuaInt)b).getValue())));
        else if( a instanceof MuaLiteral && b instanceof MuaDouble)
            return new MuaBoolean(comparer.run(((MuaLiteral)a).getValue(), Double.toString(((MuaDouble)b).getValue())));
        else if( a instanceof MuaLiteral && b instanceof MuaLiteral)
            return new MuaBoolean(comparer.run(((MuaLiteral)a).getValue(), ((MuaLiteral)b).getValue()));
        else
            throw new RuntimeMuaException("Illegal Type to execute compare: " + a.toString() + " and " + b.toString());
    }

}
