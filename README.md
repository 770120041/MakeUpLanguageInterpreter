# Mua Interpreter

 It's an interpreter for the functional programming language Mua written with Java.

## Usage
Build `Mua.class` from source. Use `java Mua` to start interactive mode, or `java Mua <filename>` to run Mua code in a file.

## Mua language specification

Mua (**M**ade-**u**p l**a**nguage) is a Lisp-like functional programming language.

```
// Single line comments start with //.
// Sadly, block comments aren't supported.

// ====================
// 1. Basic Types
// ====================

7654321 // Number

"some_word // Word
// Word (or string if you like) starts with a ".
// Note that ("some word) is not a valid word, as words can't contain blank characters (like space or tab).

true // Bool
false // Bool

[7654321 "some_word true false] // List
// Elements in a list are seperated by a space.
// Elements in a list can be of different types.
[true ["list_1 11] ["list_2] false] // A list can also contain other lists.

// ====================
// 2. Basic Operations
// ====================

// print <value>: Print out <value>.
print 1 // => 1.0

// make <word> <value>: Bind <value> to <word>.
// We call the word "name" after the binding, and it's recorded in the current namespace.
make "a 1 // Sort of like a = 1.
make "b "a // This means b = "a", not b = 1.
// Note that <word> can't be the name of an operation.

// thing <word>: Return the value bound to <word>.
print thing "a // => 1.0
print thing thing "b // => 1.0
// :<word> has is same effect with thing <word>.
print :a // => 1.0
// Note that multiple colons before a word is not valid. We can't use ::b to get 1.

// Mua considers all operations as an operation flow, so operations can be divided onto multiple lines.
// The following three lines of code is also valid.
make "a
1 make "b "a print thing
thing "b // => 1.0

// read: Read a number or a word from the standard input
make "c read // c = (whatever reads from the standard input).

// readlinst: Read a whole line from the standard input, and make them a list.
make "d readlinst // Input: 7 "some_word true, then d = [7 "some_word true]

// Type checking
print isnumber 7654321 // => true
print isword "some_word // => true
print islist [7654321 "some_word] // => true
print isbool false // => true

// ====================
// 3. Arithmetic and Logic Operations
// ====================
print add 5 4 // => 9.0
print sub 5 4 // => 1.0
print mul 5 4 // => 20.0
print div 5 4 // => 1.25
print mod 5 4 // => 1.0

print eq 5 4 // => false (if 5 == 4)
print ne 5 4 // => true (if 5 != 4)
print gt 5 4 // => true (if 5 > 4)
print ge 5 4 // => true (if 5 >= 4)
print lt 5 4 // => false (if 5 < 4)
print le 5 4 // => false (if 5 <= 4)

// There are also some built-in arithmetic operations.
print random 7.89 // => (a random number in [0, 7.89)).
print sqrt 2.25 // => 1.5
print pi // => 3.1415926535
print int pi // => 3.0 (floor the given number)

print and true false // => false (true && false)
print or true false // => true (true || false)
print not true // => false (!true)

// ====================
// 4. Expressions
// ====================

// Expressions must be surrounded by ().
print (1 + 2 * 3) // => 7.0

// Mua operations have higher priority than +-*/%<>= in expressions.
print (:a + sub 4 3 * 2) // => 3.0 (1 + (4 - 3) * 2 = 3)

// An expression can contain other expressions.
print (:a + sub 4 (3*2)) // => -1.0 (1 + (4 - (3*2)) = -1)

print (3 < 5 >= 4 = 4 != 3) // => true (if 3 < 5 and 5 >= 4 and 4 == 4 and 4 != 3)

// ====================
// 5. Word and List Operations
// ====================

// word <word> <word|number|bool>: Word concatenation.
print word "Ts "Reaper // => TsReaper
print word "hello_ 1 // => hello_1.0
print word "hello_ false // => hello_false

// sentence <list1> <list2>: Append all elements in <list2> at the end of <list1>.
print sentence [1 "word1 true] [false "word2] // => [1.0 "word1 true false "word2]

// list <value1> <value2>: Make a list with 2 elements containing <value1> and <value2>.
print list "word1 true // => ["word1 true]
print list "word2 [1 false] // => ["word2 [1.0 false]]

// join <list> <value>: Append <value> at the end of <list>.
print join [1 2] 3 // => [1.0 2.0 3.0]
print join [1 2] [3 4] // => [1.0 2.0 [3.0 4.0]]

// first <word|list>: Get the first character of <word>, or the first element of <list>.
print first "TsReaper // => T
print first [1 2 3] // => 1.0

// last <word|list>: Get the last character of <word>, or the last character of <list>.
print last "TsReaper // => r
print last [1 2 3] // => 3.0

// butfirst <word|list>: Get the <word> without the first character, or the <list> without the first element.
print butfirst "TsReaper // => sReaper
print butfirst [1 2 3] // => [2.0 3.0]

// butlast <word|list>: Get the <word> without the last character, or the <list> without the last element.
print butlast "TsReaper // => TsReape
print butlast [1 2 3] // => [1.0 2.0]

// isempty <word|list>: Check if <word> or <list> is empty.
print isempty "TsReaper // => false
print isempty butfirst butfirst [1 2] // => true

// ====================
// 6. Control
// ====================

// if <bool> <list1> <list2>: If <bool> is true, run code in <list1>, otherwise run code in <list2>.
if (:a % 2 = 0) [
    print :a
    print "even
] [
    print :a
    print "odd
]
// prints 1.0 and odd

// repeat <number> <list>: Run code in <list> for <number> times.
make "e 1
repeat 5 [
    print :e
    make "e (:e + 1)
]
// prints 1.0 2.0 3.0 4.0 5.0
// run <list> is the same with repeat 1 <list>

// stop: Stop the current repeat.
make "f 1
repeat 5 [
    print :f
    if (:f = 3) [
        stop
    ] []
    make "f (:f + 1)
]
// prints 1.0 2.0 3.0

// wait <number>: wait for <number> ms
wait 1000
print "wait_after_1_second // print this after 1000 ms

// ====================
// 7. Function
// ====================

// Function is just a list with some special constraints.
// make <word> [<list1> <list2>] defines a function named <word>, with <list1> as its arguments and <list2> as its operations.
make "print_a_plus_b [[a b] [
    print "a_plus_b_is
    print (:a + :b)
]]

// To call a function, use <function_name> <arg_list>.
print_a_plus_b 1 2 // prints a_plus_b_is and 3.0

// output <value>: Sets the return value of the current function.
make "a_plus_b [[a b] [
    output (:a + :b)
]]
print a_plus_b 1 2 // => 3.0

// stop: Stops the current function.
// Yeah, stop can mean "break" or "return". What it really means depends.
make "some_func [[] [
    print "some_func
    stop
    print "shouldn't_print_this
]]
some_func // => some_func

// ====================
// 8. Namespace
// ====================

// isname <word>: Check if <word> is a name in the namespace.
print isname "not_a_name // => false
make "not_a_name "now_its_a_name
print isname "not_a_name // => true

// erase <word>: Unbind the value attached to <word>.
erase "not_a_name
print isname "not_a_name // => false

// poall: List all names in the current namespace.
poall
// prints the following table
// a       1.0
// b       "a
// a_plus_b        [[a b] [output (:a + :b)]]
// c       5.0
// d       [6.0]
// some_func       [[] [print "some_func stop print "shouldn't_print_this]]
// e       6.0
// print_a_plus_b  [[a b] [print "a_plus_b_is_ print (:a + :b)]]
// f       6.0

// erall: Unbind all names in the current namespace.
erall
poall // prints nothing

// The range of a local namespace is within a function.
// Sort of like `var` in JavaScript.
// Function closure is supported.
make "f0 [[e] [
    print isname "a // => false
    print isname "b // => false
    print isname "c // => false
    print isname "d // => false
    print isname "e // => true
]]

make "f1 [[a] [
    make "f2 [[b] [
        print isname "a // => true
        f3 0
    ]]
    make "f3 [[c] [
        print isname "a // => true
        print isname "b // => false
        make "f4 [[d] [
            print isname "a // => true
            print isname "b // => false
            print isname "c // => true
            f0 0
        ]]
        f4 0
    ]]
    f2 0
]]

f1 0

// export: Move all names in the local namespace to the global namespace.
erall
make "func [[] [
    make "a 1
    export
]]
print isname "a // => false
func
print isname "a // => true
```

# Miscellaneous
1. Mua language is created by course Instructor Weng Kai in Zhejiang University for his course "Programming Language Principles"'
2. This README is referenced from TsReaper and this implementation in under the framework of zurl.
