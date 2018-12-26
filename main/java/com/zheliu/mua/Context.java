package com.zheliu.mua;

import com.zheliu.mua.Exception.MuaException;
import com.zheliu.mua.Exception.RuntimeMuaException;
import com.zheliu.mua.Exception.StopInterrupt;
import com.zheliu.mua.Util.ProgramCache;
import com.zheliu.mua.Variable.MuaNull;
import com.zheliu.mua.Variable.MuaVariable;

import java.util.ArrayList;
import java.util.Stack;

public class Context {

    /*
        usage of Context: execute a ASTNode in a given context
     */
    private Tokenizer tokenizer;
    private SymbolTable symbolTable;
    private Stack<ArrayList<String>> errorStack;

    private ProgramCache programCache;

    public Stack<ArrayList<String>> getErrorStack() {
        return errorStack;
    }


    public Context(Tokenizer tokenizer, SymbolTable symbolTable) {
        this.tokenizer = tokenizer;
        this.symbolTable = symbolTable;
        this.errorStack = new Stack<ArrayList<String>>();
        this.programCache = ProgramCache.getInstance();
    }

    public SymbolTable getSymbolTable() {
        return symbolTable;
    }

    /*
        Used for if and else for tesing
     */
    Boolean testFlag = false;

    public Boolean getTestFlag() {
        return testFlag;
    }

    public void setTestFlag(Boolean testFlag) {
        this.testFlag = testFlag;
    }



    private MuaVariable outputValue = new MuaNull();

    public MuaVariable getOutputValue() {
        return outputValue;
    }

    public void setOutputValue(MuaVariable outputValue) {
        this.outputValue = outputValue;
    }
    /*
        Theoretically, Context should only have method for executing an ASTNode,
        adding parsing tokens will help to accelerate the speed, because recently parsed program will
        save their ASTNode into cache, thus don't need to parse again
     */
    public MuaVariable run(ArrayList<MuaVariable> tokens, int repeat) throws MuaException {
        ArrayList<Parser.ASTNode> programs = programCache.getProgram(tokens);
        MuaVariable returnValue = new MuaNull();
        Parser parser = new Parser(null, symbolTable);
        boolean inCache = false;
        if (programs == null) {
            programs = new ArrayList<Parser.ASTNode>();
            parser.setProgram(tokens);
            do {
                programs.add(parser.parse());
                try {
                    returnValue = run(programs.get(programs.size() - 1));
                } catch (StopInterrupt e) {
                    return new MuaNull();
                }
            } while (!parser.isComplete());
            parser.cleanUp();
            programCache.insertToken(tokens, programs);
        } else {
            inCache = true;
        }

        /*
            If program not in cache, then it is already executed once;
            So repeat --;
         */
        if(inCache == false){
            repeat--;
        }

        for(int i = 0; i < repeat;i ++){
            try {
                for (int j = 0; j < programs.size(); j++) {
                    returnValue = run(programs.get(j));
                }
            } catch (StopInterrupt e) {
                return new MuaNull();
            }
        }
        return returnValue;
    }


    public MuaVariable run(Parser.ASTNode program) throws MuaException {
        Class[] types = program.function.getArgTypes();
        MuaVariable[] arguments = new MuaVariable[program.arguments.length];

        for(int i = 0; i < arguments.length; i++){
            arguments[i] = run(program.arguments[i]);
            //check for type not match
            if( !types[i].isInstance(arguments[i])){
                throw new RuntimeMuaException("Type of function "
                        + program.function.getClass().getSimpleName().toLowerCase()
                        + " is incorrect, "
                        + " require = " + types[i].getSimpleName()
                        + " fact = " + arguments[i].getClass().getSimpleName()
                        + " value = " + arguments[i].toString()
                );
            }
        }

        MuaVariable result = program.function.run(this, arguments);


        return result;
    }
}
