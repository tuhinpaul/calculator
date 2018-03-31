#Calculator Program

##Overview
Depending on whether you use maven to compile or make package, you may have to execute the program slightly differently. If you are making a jar of the code, you need to add that to the classpath.

The solution has **three phases**:

1. Tokenize the expression.
2. Create a token tree using pre order traversal.
3. Evaluate the token tree using a post order traversal.


---------

## Assumptions

#### Keywords
The operator names (case-sensitive) are keywords. Variables should not use those names with exact case of the operator names. The operator names can be changed in calculator.Calculator class (e.g., fields: OP_LET, OP_ADD, etc.).

#### double vs int
The Calculator.TokenTree class actually uses double as the internal datatype to store the result. Finally, the result is casted to int when returned from Calculator.evaluate(). This may create a different result if you considered only integer in the Calculator.TokenTree class. This may happen due to truncation of fractional part in integer division.


#### Require valid input
The program will execute on valid input. To be able to handle all sorts of invalid input, the grammar of the expressions has to be analyzed.

#### Handling wrong inputs
Some invalid inputs will give a result.

Because of the way the program was implemented, it will overlook errors in some invalid inputs: such as "add((1,2" and "add(1,2))". This happens because:

The program tokenizes the input string. And the form an expression takes after replacing all the delimiters with a single space can actually be represented as a valid expression if you do a pre-order traversal of the transformed expression.
For example, suppose you are given the expression:

```
let a mult 10 2 add a 5
```

Pre-order traversal of the above tokens will give you a tree, which you can then evaluate using a post-order traversal.

**Another wrong input that will give a result**
```
let(a, add(3,2,1))
```



#### Logging Layer

Logging layer class calculator.AppLogger uses custom formatter found in AppLogger.java. Please note that, program outputs are shown to console using System.out and sometimes System.err (e.g., usage of Main program).

Use AppLogger.LogToConsoleOnly(boolean) to send logs to console/filesystem. Default is filesystem (default log file is ./logs/calculator.log).

I added three methods to the AppLogger class: info(), error(), and debug(). These methods pertain to the Log levels mentioned in the specification. These methods use a common logger. AppLogger class provides a setLevel(string) method that sets the verbose level of the common logger.

You may instead call AppLogger.getAppLogger(String name) to get a java.util.Logger instance with the name of the class where you are logging. Then you may use any of the java.util.logging.Level levels.

**Limitation:** If logging to filesystem, you should call AppLogger.closeHandlers() at the end of the program.

--------------

##Notes on Further Improvement

#### Cleaning up token tree
Not cleaning up the tree will not be a problem for a small program like this. However, if the program is processing giga bytes of data and creating data structures to store them temporarily, it may cause memory leak as gc may not free the memory by the time the program runs out of available memory. Although I am not implementing cleanup right now, the way to clean up will be as follows: do a post order traversal of the tree and clear the data in a node at the end of visiting the subtree rooted at that node.

#### Handling wrong input
Some errors are handled; others are not. However, this is not specified in the specification. Handling multiple errors would be nice.

#### To add test cases that use Java reflexion
To test private methods.

#### To Test AppLogger
Did not test AppLogger.

#### Checking overflow
Overflow checks have not been implemented, and have not been added in test cases. Utilizing google's guava api may help in this case.

#### Logging layer
May consider choosing between logging to console and filesystem.

