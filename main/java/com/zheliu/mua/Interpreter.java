package com.zheliu.mua;

import com.zheliu.mua.Exception.MuaException;
import com.zheliu.mua.Exception.SyntaxMuaException;
import com.zheliu.mua.Variable.MuaList;
import com.zheliu.mua.Variable.MuaVariable;

import java.util.ArrayList;
import java.util.Scanner;


public class Interpreter {


    private SymbolTable symbolTable;
    private Tokenizer tokenizer;
    private Parser parser ;
    private Context context;

    /**
     * Program flow:
     * get input from stdin
     * tokenize it.
     * parser evaluate it
     * Context run it
     *
     */
    public Interpreter() {
        symbolTable = new SymbolTable();
        tokenizer = Tokenizer.getInstance();
        parser = new Parser(this, symbolTable);
        context = new Context(tokenizer, symbolTable);
    }

    ArrayList<MuaVariable> tokens = null;
    Scanner scanner = new Scanner(System.in);


    public void getTokens(boolean isNew) throws MuaException {
        tokens = null;
        boolean showHelp = false;
        while(tokens == null){
            if( isNew ) System.out.print("MUA>");
            String code = scanner.nextLine();
            if( code.equals("exit") || code.equals("quit") ) return;
            if( code.equals("")) break;
            tokens = tokenizer.tokenize(code, true);
            isNew = false;
        }
        if( tokens == null) throw new SyntaxMuaException(" Break Input ");
    }
    private void loadPresetCode(){
        String presetProgram = "make \"pi 3.14159" + "\n"+
                "";

        parser.setProgram(tokenizer.tokenize(presetProgram,false));
        try{
            context.run(parser.parse());
        }catch (MuaException e){
            e.printStackTrace();
        }


    }
    public void interprept(){
        loadPresetCode();

        while(true){
            try {
                getTokens(true);
                MuaVariable ret;

                parser.setProgram(tokens);
                do {
                    Parser.ASTNode astNodes = parser.parse();
                    ret = context.run(astNodes);
                }while(!parser.isComplete());
                while( ret instanceof MuaList){
                    ret = context.run(((MuaList)ret).getValue(), 1);
                }
                System.out.println(">>>" + ret.toString());
                tokenizer.cleanUp();
                parser.cleanUp();
            }catch (MuaException e){
                System.out.println(e.getMessage());
                tokenizer.cleanUp();
                parser.cleanUp();
            }
        }
    }





}
