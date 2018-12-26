package com.zheliu.mua.Util;

import com.zheliu.mua.Parser;
import com.zheliu.mua.Variable.MuaVariable;

import java.util.ArrayList;
import java.util.HashMap;

public class ProgramCache {

    /*
        Cache is single Hashmap, if it is full(larger than size), then
        just reallocate the space.
    */
    private  static int cacheSize = 500;
    private HashMap<ArrayList<MuaVariable>,ArrayList<Parser.ASTNode>> map;
    private  int counter;

    public static int getCacheSize() {
        return cacheSize;
    }

    private  void initCache(){
        counter = 0;
        this.map = new HashMap<ArrayList<MuaVariable>, ArrayList<Parser.ASTNode>>();
    }
    private ProgramCache(){
        initCache();
    }
    private static ProgramCache singleInstane = null;
    public static ProgramCache getInstance() {
        if(singleInstane==null){
            singleInstane = new ProgramCache();
        }
        return singleInstane;
    }

    public void insertToken(ArrayList<MuaVariable> key, ArrayList<Parser.ASTNode> value){
        counter++;
        if(counter == cacheSize){
            initCache();
        }
        map.put(key,value);
    }
    public ArrayList<Parser.ASTNode> getProgram(ArrayList<MuaVariable> key){
        return map.get(key);
    }

}
