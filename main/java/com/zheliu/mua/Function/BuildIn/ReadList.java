package com.zheliu.mua.Function.BuildIn;

import com.zheliu.mua.Exception.MuaException;
import com.zheliu.mua.Function.BuiltInFunc;
import com.zheliu.mua.Context;
import com.zheliu.mua.Variable.*;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Created by zcy on 27/09/2017.
 */
public class ReadList extends BuiltInFunc {

    private static Class[] argTypes = {};

    private static Pattern splitRegex =
            Pattern.compile("\\s+");

    public Class[] getArgTypes() {
        return argTypes;
    }

    public MuaVariable run(Context ctx, MuaVariable[] args) throws MuaException {
        Scanner scanner = new Scanner(System.in);
        String value = scanner.nextLine();
        String[] words = splitRegex.split(value);
        MuaList muaList = new MuaList(new ArrayList<MuaVariable>());
        for(String word : words){
            try{
                muaList.getValue().add(new MuaInt(Integer.parseInt(word)));
            }
            catch (Exception e){
                try {
                    muaList.getValue().add(new MuaDouble(Double.parseDouble(word)));
                }catch (Exception ex){
                    muaList.getValue().add(new MuaLiteral(word));
                }
            }
        }
        return muaList;
    }
}

