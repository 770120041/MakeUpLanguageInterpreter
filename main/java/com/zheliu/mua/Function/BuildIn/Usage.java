package com.zheliu.mua.Function.BuildIn;

import com.zheliu.mua.Context;
import com.zheliu.mua.Exception.MuaException;
import com.zheliu.mua.Function.BuiltInFunc;
import com.zheliu.mua.Variable.MuaLiteral;
import com.zheliu.mua.Variable.MuaVariable;

public class Usage extends BuiltInFunc {
    @Override
    public Class[] getArgTypes() {
        return new Class[0];
    }

    @Override
    public MuaVariable run(Context ctx, MuaVariable[] args) throws MuaException {
        return new MuaLiteral(helpMsg());
    }
    private String lineString(String sentence){
        return sentence+"\n";
    }
    private String helpMsg(){
        StringBuilder sb = new StringBuilder();
        sb.append(lineString("1.Variable"));
        sb.append(lineString("7654321 // Number\n" +
                "\n" +
                "\"some_word // Word\n" +
                "// Word (or string if you like) starts with a \".\n" +
                "// Note that (\"some word) is not a valid word, as words can't contain blank characters (like space or tab).\n" +
                "\n" +
                "true // Bool\n" +
                "false // Bool\n" +
                "\n" +
                "[7654321 \"some_word true false] // List\n" +
                "// Elements in a list are seperated by a space.\n" +
                "// Elements in a list can be of different types.\n" +
                "[true [\"list_1 11] [\"list_2] false] // A list can also contain other lists.\n\n"));

        sb.append(lineString("2.Basic Operations"));
        sb.append(lineString(" print <value>: Print out <value>.\n" +
                "print 1 // => 1.0"));
        sb.append(lineString(" make <word> <value>: Bind <value> to <word>."));
        sb.append(lineString("\"make \\\"a 1 // Sort of like a = 1.\\n\" +\n" +
                "                \"make \\\"b \\\"a // This means b = \\\"a\\\", not b = 1.\""));
        sb.append(lineString("thing <word>: Return the value bound to <word>.\n" +
                "print thing \"a // => 1.0\n" +
                "print thing thing \"b // => 1.0"));
        sb.append(lineString("// read: Read a number or a word from the standard input\n" +
                "make \"c read // c = (whatever reads from the standard input).\n\n"));

        sb.append(lineString("3.Arithmetic and Logic Operations"));
        sb.append(lineString("print add 5 4 // => 9.0\n" +
                "print sub 5 4 // => 1.0\n" +
                "print mul 5 4 // => 20.0\n" +
                "print div 5 4 // => 1.25\n" +
                "print mod 5 4 // => 1.0\n"));
        sb.append(lineString("print eq 5 4 // => false (if 5 == 4)\n" +
                "print ne 5 4 // => true (if 5 != 4)\n" +
                "print gt 5 4 // => true (if 5 > 4)\n" +
                "print ge 5 4 // => true (if 5 >= 4)\n" +
                "print lt 5 4 // => false (if 5 < 4)\n" +
                "print le 5 4 // => false (if 5 <= 4)\n\n"));

        sb.append(lineString("4.Expressions"));
        sb.append(lineString("// Expressions must be surrounded by ().\n" +
                "print (1 + 2 * 3) // => 7.0\n" +
                "\n" +
                "// Mua operations have higher priority than +-*/%<>= in expressions.\n" +
                "print (:a + sub 4 3 * 2) // => 3.0 (1 + (4 - 3) * 2 = 3)\n" +
                "\n" +
                "// An expression can contain other expressions.\n" +
                "print (:a + sub 4 (3*2)) // => -1.0 (1 + (4 - (3*2)) = -1)\n" +
                "\n" +
                "print (3 < 5 >= 4 = 4 != 3) // => true (if 3 < 5 and 5 >= 4 and 4 == 4 and 4 != 3)\n\n"));

        sb.append(lineString("5.Word and List Operations"));
        sb.append(lineString("// word <word> <word|number|bool>: Word concatenation.\n" +
                "print word \"Ts \"Reaper // => TsReaper\n" +
                "print word \"hello_ 1 // => hello_1.0\n" +
                "print word \"hello_ false // => hello_false\n" +
                "\n" +
                "// sentence <list1> <list2>: Append all elements in <list2> at the end of <list1>.\n" +
                "print sentence [1 \"word1 true] [false \"word2] // => [1.0 \"word1 true false \"word2]\n" +
                "\n" +
                "// list <value1> <value2>: Make a list with 2 elements containing <value1> and <value2>.\n" +
                "print list \"word1 true // => [\"word1 true]\n" +
                "print list \"word2 [1 false] // => [\"word2 [1.0 false]]\n\n"));

        sb.append(lineString("6.control"));

        sb.append(lineString("// if <bool> <list1> <list2>: If <bool> is true, run code in <list1>, otherwise run code in <list2>.\n" +
                "if (:a % 2 = 0) [\n" +
                "    print :a\n" +
                "    print \"even\n" +
                "] [\n" +
                "    print :a\n" +
                "    print \"odd\n" +
                "]\n" +
                "// prints 1.0 and odd\n" +
                "\n" +
                "// repeat <number> <list>: Run code in <list> for <number> times.\n" +
                "make \"e 1\n" +
                "repeat 5 [\n" +
                "    print :e\n" +
                "    make \"e (:e + 1)\n" +
                "]\n\n"));

        sb.append(lineString("7.Function"));
        sb.append(lineString("// Function is just a list with some special constraints.\n" +
                "// make <word> [<list1> <list2>] defines a function named <word>, with <list1> as its arguments and <list2> as its operations.\n" +
                "make \"print_a_plus_b [[a b] [\n" +
                "    print \"a_plus_b_is\n" +
                "    print (:a + :b)\n" +
                "]]\n" +
                "\n" +
                "// To call a function, use <function_name> <arg_list>.\n" +
                "print_a_plus_b 1 2 // prints a_plus_b_is and 3.0\n" +
                "\n" +
                "// output <value>: Sets the return value of the current function.\n" +
                "make \"a_plus_b [[a b] [\n" +
                "    output (:a + :b)\n" +
                "]]\n" +
                "print a_plus_b 1 2 // => 3.0\n" +
                "\n" +
                "// stop: Stops the current function.\n" +
                "// Yeah, stop can mean \"break\" or \"return\". What it really means depends.\n" +
                "make \"some_func [[] [\n" +
                "    print \"some_func\n" +
                "    stop\n" +
                "    print \"shouldn't_print_this\n" +
                "]]\n" +
                "some_func // => some_func\n\n"));
        sb.append(lineString("8.NameSpace"));
        sb.append(lineString("// isname <word>: Check if <word> is a name in the namespace.\n" +
                "print isname \"not_a_name // => false\n" +
                "make \"not_a_name \"now_its_a_name\n" +
                "print isname \"not_a_name // => true\n" +
                "\n" +
                "// erase <word>: Unbind the value attached to <word>.\n" +
                "erase \"not_a_name\n" +
                "print isname \"not_a_name // => false\n" +
                "\n" +
                "// poall: List all names in the current namespace.\n" +
                "poall\n" +
                "// prints the following table\n" +
                "// a       1.0\n" +
                "// b       \"a\n" +
                "// a_plus_b        [[a b] [output (:a + :b)]]\n" +
                "// c       5.0\n" +
                "// d       [6.0]\n" +
                "// some_func       [[] [print \"some_func stop print \"shouldn't_print_this]]\n" +
                "// e       6.0\n" +
                "// print_a_plus_b  [[a b] [print \"a_plus_b_is_ print (:a + :b)]]\n" +
                "// f       6.0\n" +
                "\n" +
                "// erall: Unbind all names in the current namespace.\n" +
                "erall\n" +
                "poall // prints nothing\n" +
                "\n" +
                "// The range of a local namespace is within a function.\n" +
                "// Sort of like `var` in JavaScript.\n" +
                "// Function closure is supported.\n" +
                "make \"f0 [[e] [\n" +
                "    print isname \"a // => false\n" +
                "    print isname \"b // => false\n" +
                "    print isname \"c // => false\n" +
                "    print isname \"d // => false\n" +
                "    print isname \"e // => true\n" +
                "]]\n" +
                "\n" +
                "make \"f1 [[a] [\n" +
                "    make \"f2 [[b] [\n" +
                "        print isname \"a // => true\n" +
                "        f3 0\n" +
                "    ]]\n" +
                "    make \"f3 [[c] [\n" +
                "        print isname \"a // => true\n" +
                "        print isname \"b // => false\n" +
                "        make \"f4 [[d] [\n" +
                "            print isname \"a // => true\n" +
                "            print isname \"b // => false\n" +
                "            print isname \"c // => true\n" +
                "            f0 0\n" +
                "        ]]\n" +
                "        f4 0\n" +
                "    ]]\n" +
                "    f2 0\n" +
                "]]"));
        return sb.toString();
    }
}
