#Calculator

##Overview
Depending on whether you use maven to compile or make package, you may have to execute the program slightly differently. If you are making a jar of the code, you need to add that to the classpath.

## Assumptions

#### double vs inte
The Calculator.TokenTree class actually uses double as the internal datatype to store the result. Finally, the result is casted to int when returned from Calculator.evaluate(). This may create a different result if you considered only integer in the Calculator.TokenTree class. This may happen due to truncation of fractional part in integer division.


#### Require valid input
The program will execute on valid input. To be able to handle all sorts of invalid input, the grammar of the expressions has to be analyzed.

#### some invalid inputs will still work
Because of the way the program was implemented, it will overlook errors in some invalid inputs: such as "add((1,2" and "add(1,2))". This happens because:

The program tokenizes the input string. And the form an expression takes after replacing all the delimiters with a single space can actually be represented as a valid expression if you do a pre-order traversal of the transformed expression.
For example, suppose you are given the expression:

```
let a mult 10 2 add a 5
```

Pre-order traversal of the above tokens will give you a tree, which you can then evaluate using a post-order traversal.


##Notes for further improvement

### Cleaning up token tree
Not cleaning up the tree will not be a problem for a small program like this. However, if the program is processing giga bytes of data and creating data structures to store them temporarily, it may cause memory leak as gc may not free the memory by the time the program runs out of available memory. Although I am not implementing cleanup right now, the way to clean up will be as follows: do a post order traversal of the tree and clear the data in a node at the end of visiting the subtree rooted at that node.
