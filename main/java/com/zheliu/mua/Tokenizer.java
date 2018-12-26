package com.zheliu.mua;

import com.zheliu.mua.Variable.*;

import java.util.ArrayList;
import java.util.Stack;
import java.util.regex.Pattern;

/*
    Tokenizer are tools independent of context, so it is a singleton used only once!
 */

public class Tokenizer {
    private static Pattern splitRegex =
            Pattern.compile("\\s+");
    private static String commentReg = "(?<!:)\\/\\/.*|\\/\\*(\\s|.)*?\\*\\/";
    private static char[] opList = {'+','-','*','/','%','(',')','='};
    private static char[] whilteList = {'\n','\t',' '};
    private ArrayList<MuaVariable> tokens;
    private Stack<ArrayList<MuaVariable>> tokenStack;
    private boolean isMultiLine = false; // used to handle brackets

    private void initTokenizer(){
        this.tokens = new ArrayList<MuaVariable>();
        this.tokenStack = new Stack<ArrayList<MuaVariable>>();
    }

    private static Tokenizer singleInstance = null;
    public static Tokenizer getInstance()
    {
        if (singleInstance == null)
            singleInstance = new Tokenizer();
        return singleInstance;
    }

    private Tokenizer() {
        cleanUp();
    }
    public void cleanUp(){
        initTokenizer();
    }


    private boolean checkWhite(char cur){
        for (char x: whilteList
        ) {
            if(cur == x){
                return true;
            }
        }
        return  false;
    }
    /*
        return 2 means there is <= or >=
        return 1 means normal operator
     */
    private  boolean checkBrackets(char cur){
        if(cur == '[' || cur == ']'){
            return true;
        }
        return false;
    }
    private int checkOp(String code,int pos){
        char cur = code.charAt(pos);
        for (char x: opList
        ) {
            if(cur == x){
                return 1;
            }
            if(cur == '<' || cur == '>'){
                if(pos+1 < code.length() && code.charAt(pos+1) == '='){
                    return 2;
                }
                else{
                    return 1;
                }
            }
        }
        return 0;
    }
    private boolean isInteger(String word){
        try{
            Integer v = Integer.valueOf(word);
            return true;
        }catch (Exception e){
            return false;
        }
    }
    private boolean isDouble(String word){
        try{
            Double v = Double.valueOf(word);
            return true;
        }catch (Exception e){
            return false;
        }
    }
    private boolean isBoolean(String word){
        try{
            boolean v = Boolean.parseBoolean(word);
            return true;
        }catch (Exception e){
            return false;
        }
    }


    private String beautifyString(String code){
        StringBuilder sb = new StringBuilder();
        boolean inLiteral = false;
        for(int i=0;i<code.length();i++){
            char cur = code.charAt(i);
            if(checkWhite(cur)){
                inLiteral = false;
                sb.append(" ");
                continue;
            }
            if(inLiteral){

                sb.append(cur);
                continue;
            }
            if(checkBrackets(cur)){
                sb.append(" ");
                sb.append(cur);
                sb.append(" ");
                continue;
            }
            /*
                check arithmatic operations
             */
            int opValue = checkOp(code,i);
            if(opValue == 1){

                sb.append(" ");
                sb.append(cur);
                sb.append(" ");
            }
            else if(opValue == 2){
                sb.append(" ");
                sb.append(cur);
                sb.append(" =  ");
                i++;

            }
            else{

                sb.append(cur);

                if(cur == '\"') inLiteral = true;
            }
        }
        return sb.toString();
    }

    public ArrayList<MuaVariable> tokenize(String code,boolean isMultiLine) throws Error{
        code = code.replaceAll(commentReg,"");
        String program = beautifyString(code);
        String[] words = splitRegex.split(program);


        for (String word: words
        ) {
            if (word.equals("") ) continue;
            char word0 = word.charAt(0);
            int opValue = checkOp(word,0);
            if(opValue == 1){
                tokens.add(new MuaOperator(word0));
            }
            // <= and >= operator
            else if(opValue == 2){
                if(word0 =='<'){
                    tokens.add(new MuaOperator(MuaOperator.LTE));
                }
                else {
                    tokens.add(new MuaOperator(MuaOperator.GTE));
                }
            }
            else{
                if(word0== '\'' ||word0== '\"'){
                    tokens.add(new MuaLiteral(word.substring(1)));
                }
                else if (word0 == ':'){
                    tokens.add(new MuaWord("thing"));
                    tokens.add(new MuaLiteral(word.substring(1)));
                }
                else if(word0=='['){
                    tokenStack.add(tokens);
                    tokens = new ArrayList<MuaVariable>();
                }
                else if(word0==']'){
                    //if(tokenStack.empty()) throw new SyntaxError("brackets not matched");
                    MuaList curList = new MuaList(tokens);
                    tokens = tokenStack.pop();
                    tokens.add(curList);
                }
                else if(isInteger(word)){
                    tokens.add(new MuaInt(Integer.parseInt(word)));
                }
                else if(isDouble(word)){
                    tokens.add(new MuaDouble(Double.parseDouble(word)));
                }

                else  tokens.add(new MuaWord(word));
            }
        }
        if (tokenStack.isEmpty() == false){
            /*
                if have multiple lines, the same tokenizer will be reused before it is cleaned up, so the
                partial result can be store in variable "tokens"
             */
            if (isMultiLine) {
                return null;
            }


        }
        return tokens;
    }
}
