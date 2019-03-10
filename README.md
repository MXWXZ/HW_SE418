# HW_SE418
homework for SE418
Simple calculator

## Getting started
Developed using IntelliJ IDEA, test passed in Ubuntu 18.04, maybe you need to try on your own if you use other IDEs...

1. Clone this repo
2. IntelliJ - Open, choose the folder
3. Build & Run - Main
4. Input the expression

## Calculator manual
This calculator support 6 operator: `+ - * / % !` and support `ANS` for the last valid answer.

We make these rules:
- End the input using `#`
- Negative number should be in `()`, otherwise an error will be produce. e.g. `1+(-1)`
- Results are rounded off to 5 decimal places, remove extra 0s in the end.
- No space should be in the expression.
- `ANS` should be exactly in upper-case, otherwise an error will be produce.

### Sample of input/output
Sample 1:

    1+1
    2
    ANS
    2
    ANS+1
    3
    ANS
    3
    #

Sample 2:

    7*4*(3/9)
    9.33333
    1/7
    0.14286
    5!
    120
    1+dd
    error
    #

## About JUnit
We make the test run after every build, you can also run it by yourself.
See `CalculatorTest` for more details.