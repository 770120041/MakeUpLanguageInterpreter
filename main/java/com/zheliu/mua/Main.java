package com.zheliu.mua;

import com.zheliu.mua.Exception.MuaException;
import com.zheliu.mua.Util.UnitTest;


public class Main {
    public static void unitTest(){
        UnitTest ts = new UnitTest();
        try {
            ts.testCalculation();
            ts.testValue();
            ts.testControl();
            ts.testExpr();
            ts.testLogic();
            ts.testWord();
            ts.testControl();
            ts.testExpr();
            ts.testCompare();
            ts.testIs();
            ts.testList();

        }
        catch (MuaException e){
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        System.out.println("Mua set up! Type in \"usage\" or \"help\" for sample code ");
//        unitTest();
        Interpreter interpreter = new Interpreter();
        interpreter.interprept();

    }
}
