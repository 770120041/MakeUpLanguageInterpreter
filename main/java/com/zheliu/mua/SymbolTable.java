package com.zheliu.mua;

import com.zheliu.mua.Exception.MuaException;
import com.zheliu.mua.Exception.RuntimeMuaException;
import com.zheliu.mua.Function.BuiltInFuncLoader;
import com.zheliu.mua.Variable.MuaVariable;

import java.util.HashMap;

public class SymbolTable {

    static class SubSymbolTable{
        /**
         * using hashmap as symbol table
         */
        HashMap<String, MuaVariable> table;
        SubSymbolTable prev;

        /**
         * defining a new sub namespace
         * @param prev father namespace(symboltable)
         */
        SubSymbolTable(SubSymbolTable prev) {
            this.table = new HashMap<String, MuaVariable>();
            this.prev = prev;
        }
    }
    /**
     * global table is the table whose prev is null
     */
    private SubSymbolTable curTable, globalTable;

    /*
        global table's pre is null, thus we can use this to determine if a table is a global table
     */
    private void initTable(){
        curTable = new SubSymbolTable(null);
        BuiltInFuncLoader.load(curTable.table);
        globalTable = curTable;
        curTable = new SubSymbolTable(curTable);
    }

    public SymbolTable() {
        initTable();
    }

    public MuaVariable getSymbol(String name) throws MuaException {
        SubSymbolTable now = curTable;
        while ( now != null){
            MuaVariable muaVariable = now.table.get(name);
            if(muaVariable != null) return muaVariable;
            now = now.prev;
        }
        return null;
    }

    public void setSymbol(String name, MuaVariable value) throws MuaException {
        if( globalTable.table.get(name) != null)
            throw new RuntimeMuaException("You cannot re-assign a build-in word");
        curTable.table.put(name, value);
    }

    public void setGlobalSymbol(String name, MuaVariable value) throws MuaException {
        if( globalTable.table.get(name) != null)
            throw new RuntimeMuaException("You cannot re-assign a build-in word");
        globalTable.table.put(name, value);
    }


    /**
     * only remove inner name space
     * @param name the name of the symbol trying to remove
     * @throws RuntimeException when trying to remove builtinFunction
     */
    public void removeSymbol(String name) throws MuaException {
        SubSymbolTable now = curTable;
        while ( now != null){
            MuaVariable muaVariable = now.table.get(name);
            if(muaVariable != null){
                if( now.prev == null){
                    throw new RuntimeMuaException("`Global name:" + name + "` cannot be removed ."); }
                else{
                    now.table.remove(name);
                }
            }
            now = now.prev;
        }
    }

    public void createSubSymbolTable(){
        curTable = new SubSymbolTable(curTable);
    }

    public void removeSubSymbolTable() throws MuaException{
        SubSymbolTable now = curTable;
        if(now.prev == null){
            throw new RuntimeMuaException("cannot remove a global Symbol Table");
        }
        curTable = curTable.prev;
    }

    public void displayAll(){
        SubSymbolTable now = curTable;
        while ( now != null){
            for(String key : now.table.keySet()){
                System.out.println(key + " : " + now.table.get(key).toString());
            }
            now = now.prev;
        }
    }

    public void cleanAll(){
        curTable.table = new HashMap<String, MuaVariable>();
    }

    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        SubSymbolTable now = curTable;
        while ( now != null){
            for(String key : now.table.keySet()){
                stringBuilder.append("make \"");
                stringBuilder.append(key);
                stringBuilder.append(" ");
                stringBuilder.append(now.table.get(key).toRawString());
                stringBuilder.append("\n");
            }
            now = now.prev;
        }
        return stringBuilder.toString();
    }

    public SubSymbolTable getGlobalTable() {
        return globalTable;
    }
}
