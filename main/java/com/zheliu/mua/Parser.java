package com.zheliu.mua;

import com.zheliu.mua.Exception.MuaException;
import com.zheliu.mua.Exception.RuntimeMuaException;
import com.zheliu.mua.Exception.SyntaxMuaException;
import com.zheliu.mua.Function.BuildIn.*;
import com.zheliu.mua.Function.Function;
import com.zheliu.mua.Variable.*;

import java.util.ArrayList;

/*
    this class is used to parse Tokens into ASTNode(semantic analysis)
    Because mua is a functional programming language, so each AST node is actually a function and a list of argumentsF
 */
public class Parser {

    boolean isMultiline = true;
    private Interpreter interpreter;
    private SymbolTable symbolTable;


    public Parser(Interpreter interpreter, SymbolTable symbolTable) {
        this.interpreter = interpreter;
        this.symbolTable = symbolTable;
        /*
            The reason why some Parser don't have an interpreter is that:
                Some parser need to generate ASTNode from a given list, so they cannot require more input
         */
        if( interpreter == null) isMultiline = false;
    }

    public static class ASTNode {
        Function function;
        ASTNode[] arguments;
        /*
             two kinds of AST node, one is with explicit argumetns
             one is only value. The reason why arguments are arglist is to solve situations when argument is generated
             by runtime code
        */
        public ASTNode(Function function, ASTNode[] arguments) {
            this.function = function;
            this.arguments = arguments;
        }
        /*
           Actually a value, the running result of it is to return the value itself
        */
        public ASTNode(MuaVariable value){
            this.function = new MValueFunc(value);
            this.arguments = new ASTNode[]{};
        }
    }


    ArrayList<MuaVariable> program;
    int now = 0;

    public void setProgram(ArrayList<MuaVariable> program) {
        this.program = program;
    }

    private static ASTNode ZERO = new ASTNode(new MuaInt(0));

    private void getNext() throws MuaException {
        now ++;
        if(isComplete()){
            if( isMultiline ) interpreter.getTokens(false);
            else throw new SyntaxMuaException(" More tokens required for parsing ");
        }
    }

    private void getThis() throws MuaException {
        if (isComplete()) {
            if (isMultiline) interpreter.getTokens(false);
            else throw new SyntaxMuaException(" More tokens required for parsing  ");
        }
    }

    /*
         The order of parsing:
             Because Mua language is made up of functions, then
             1.if a token is a fucntion, if not a function, then it is a simple literal, just return ASTNode with simple value.
             2.for a specific function, if the function parameter, if it is a Literal, parse literal, otherwise parse boolean or number

          Try to refactor the code to function->expression->product->sum->boolean but failed. thw order now is :
          boolean -> sum -> product -> expression(parenthesis or negative) -> Function

          Why need to use recursive parsing :
                The numerical operation need left hand side(lhs) value and right hand side(rhs) value, thus it needs to parse first before
                it encounters an operator.
          TODO refactor the order of parsing

   */
    public ASTNode parse() throws MuaException {
        return parseCompare();
    }

    private ASTNode parseValue() throws MuaException {
        getThis();
        MuaVariable value = program.get(now);
        if( value instanceof MuaOperator) throw new MuaException("Illegal Operation");
        else if( value instanceof MuaWord){
            MuaWord word = (MuaWord) value;
            MuaVariable item = symbolTable.getSymbol(word.getValue());
            if( item == null ) throw new RuntimeMuaException("`" + word.getValue() + "` is undefined .");
            Function func;
            if( item instanceof MuaList && ((MuaList) item).getCustomFunction() != null){
                func = ((MuaList) item).getCustomFunction();
            }
            else if( item instanceof MuaFunction){
                func = ((MuaFunction) item).getFunction();
            }
            else{
                throw new SyntaxMuaException("`" + word.getValue() + "` is not a callable value.");
            }
            Class[] types = func.getArgTypes();
            ASTNode[] args = new ASTNode[types.length];
            if( args.length != 0 ) getNext();
            else now ++;
            for(int i = 0; i < args.length; i++){
                if(types[i] == MuaLiteral.class) args[i] = parseValue();
                else if(types[i] == MuaBoolean.class) args[i] = parseCompare();
                else args[i] = parseSum();
            }
            return new ASTNode(func, args);
        }
        else { // Other Simple
            now++; // maybe end;
            return new ASTNode(value);
        }
    }

    private ASTNode parseItem() throws MuaException {
        getThis();
        if( program.get(now) instanceof MuaOperator){
            char op = ((MuaOperator) program.get(now)).value;
            if( op == '-'){
                getNext();
                ASTNode value = parseSum();
                return new ASTNode(
                        new Sub(),
                        new ASTNode[]{ ZERO, value}
                );
            }
            else if (op == '('){
                getNext();
                ASTNode value = parseCompare();
                if( isComplete() || !(program.get(now) instanceof MuaOperator) ||
                        ((MuaOperator)program.get(now)).value != ')') throw new SyntaxMuaException(" Bracket Not Match ");
                now ++; // Maybe at an end;
                return value;
            }
            else return parseValue();
        }
        else return parseValue();
    }

    private ASTNode parseProduct() throws MuaException {
        getThis();
        ASTNode node = parseItem();
        while(!isComplete()){
            if( program.get(now) instanceof MuaOperator){
                char op = ((MuaOperator) program.get(now)).value;
                if( op == '*' || op == '%' || op == '/'){
                    getNext();
                    ASTNode right = parseItem();
                    Function function;
                    if (op == '*' ) function = new Mul();
                    else if(op == '/' ) function = new Div();
                    else function = new Mod();
                    node = new ASTNode(
                            function,
                            new ASTNode[]{ node, right }
                    );
                }
                else break;
            }
            else break;
        }
        return node;
    }

    private ASTNode parseSum() throws MuaException {
        getThis();
        ASTNode node = parseProduct();
        while(!isComplete()){
            if( program.get(now) instanceof MuaOperator){
                char op = ((MuaOperator) program.get(now)).value;
                if( op == '+' || op == '-'){
                    getNext();
                    ASTNode right = parseProduct();
                    Function function;
                    if (op == '+' ) function = new Add();
                    else function = new Sub();
                    node = new ASTNode(
                            function,
                            new ASTNode[]{ node, right }
                    );
                }
                else break;
            }
            else break;
        }
        return node;
    }

    private ASTNode parseCompare() throws MuaException {
        getThis();
        ASTNode node = parseSum();
        while(!isComplete()){
            if( program.get(now) instanceof MuaOperator){
                char op = ((MuaOperator) program.get(now)).value;
                if( op == '=' ||op == '<' || op == '>' || op == MuaOperator.GTE || op == MuaOperator.LTE){
                    getNext();
                    ASTNode right = parseSum();
                    Function function;
                    if (op == '<' ) function = new Lt();
                    else if (op == '>' ) function = new Gt();
                    else if (op == '=' ) function = new Eq();
                    else if (op == MuaOperator.LTE) function = new Lte();
                    else function = new Gte();
                    node = new ASTNode(
                            function,
                            new ASTNode[]{ node, right }
                    );
                }
                else break;
            }
            else break;
        }
        return node;
    }

//    public ASTNode parseToken() throws  Error {
//        try {
//            lhs =  parseFunc();
//            return lhs;
//        }catch (Error e){
//            throw  e;
//        }
//
//    }
//    private ASTNode parseFunc() throws  Error{
//        getThis();
//        MuaVariable word = program.get(curPos);
//        if(word instanceof MuaOperator){
////            throw new SyntaxError("Mua language cannot start with an Operator");
//        }
//        if(word instanceof MuaWord){
//            String keyWordName = ((MuaWord) word).getValue();
//            MuaVariable muaFunc = symbolTable.getSymbol(keyWordName);
//            if (muaFunc == null){
////                throw new SyntaxError("");
//            }
//            else{
//                Function func = null;
//                if (muaFunc instanceof  MuaFunction){
//                    func = ((MuaFunction) muaFunc).getFunction();
//                }
//                else if(muaFunc instanceof MuaList && ((MuaList) muaFunc).getCustomFunction() != null){
//                    func = ((MuaList) muaFunc).getCustomFunction();
//                }
//                else {
////                    throw new SyntaxError(((MuaWord) word).getValue()+" is not callable");
//                }
//                try {
//                    lhs = parseFuncArgs(func);
//                }catch (Error e){
//                    throw  e;
//                }
//
//                return lhs;
//            }
//        }
//        /*
//            Means no Muaword exist,only a literal or anything.
//         */
//
//        curPos++;
//        lhs = new ASTNode(word);
//        return  lhs;
//
//    }
//
//
//
//    private ASTNode parseFuncArgs(Function func) throws Error{
//        Class[] argTypes = func.getArgTypes();
//        ASTNode[] args = new ASTNode[argTypes.length];
//        //increment pos to ready for parsing parameters
//        if(args.length!=0) getNext();
//        else curPos++;
//        try {
//            for(int i=0;i<argTypes.length;i++){
//                if(argTypes[i] == MuaLiteral.class || argTypes[i] == MuaWord.class){
//                    args[i] = parseFunc();
//                }
////                else if(argTypes[i] == MuaBoolean.class) args[i] = parseBoolean();
////                else args[i] = parseNumber();
//            }
//        }catch (Error e){
//            throw  e;
//        }
//
//        return new ASTNode(func,args);
//    }

//    private ASTNode parseBoolean() throws Error{
//        getThis();
//        while(!isComplete()){
//            if( program.get(curPos) instanceof MuaOperator){
//                char op = ((MuaOperator) program.get(curPos)).getValue();
//                if( op == '=' ||op == '<' || op == '>' || op == MuaOperator.GTE || op == MuaOperator.LTE){
//                    getNext();
//                    ASTNode rhs = parseToken();
//                    Function function;
//                    if (op == '<' ) function = new LT();
//                    else if (op == '>' ) function = new GT();
//                    else if (op == '=' ) function = new EQ();
//                    else if (op == MuaOperator.LTE) function = new LTE();
//                    else function = new GTE();
//                    lhs = new ASTNode(
//                            function,
//                            new ASTNode[]{ lhs, rhs }
//                    );
//                }
//                else break;
//            }
//            else break;
//        }
//        return lhs;
//    }
//
//
//    // need to parse values in a brackets and negative numbers,
//    // then parse product level operator
//    // then parse addition level operator
//    private ASTNode parseNumber() throws Error{
//        getThis();
//        parseExpression();
//
//
//    }
//
//
//
//    private ASTNode parseExpression() throws Error {
//        getThis();
//        if( program.get(curPos) instanceof MuaOperator){
//            char op = ((MuaOperator) program.get(curPos)).getValue();
//            if( op == '-'){
//                getNext();
//                ASTNode minuend = parseNumber();
//                return new ASTNode(
//                        new Sub(),
//                        new ASTNode[]{ ZERO, minuend}
//                );
//            }
//            else if (op == '('){
//                getNext();
//                ASTNode value = parseFunc();
//                if( isComplete() || !(program.get(curPos) instanceof MuaOperator) ||
//                        ((MuaOperator)program.get(curPos)).getValue() != ')') {
////                    throw new SyntaxError(" Bracket Not Match ");
//                }
//                curPos ++; // Maybe at an end;
//                return value;
//            }
//            else return parseNumber();
//        }
//        else return parseNumber();
//    }
//
//    private ASTNode parseProduct() throws Error{
//        getThis();
//        ASTNode node = parseItem();
//        while(!isComplete()){
//            if( program.get(now) instanceof MOpe){
//                char op = ((MOpe) program.get(now)).value;
//                if( op == '*' || op == '%' || op == '/'){
//                    getNext();
//                    ASTNode right = parseItem();
//                    Function function;
//                    if (op == '*' ) function = new Mul();
//                    else if(op == '/' ) function = new Div();
//                    else function = new Mod();
//                    node = new ASTNode(
//                            function,
//                            new ASTNode[]{ node, right }
//                    );
//                }
//                else break;
//            }
//            else break;
//        }
//        return node;
//    }


    public void cleanUp(){
        now = 0;
    }

    public boolean isComplete(){
        return now >= this.program.size();
    }



}
