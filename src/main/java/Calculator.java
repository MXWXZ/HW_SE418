import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Stack;

public class Calculator {
    private BigDecimal ans = new BigDecimal(0);     // cache last ans

    /**
     * Caculate expression.
     * @param str math expression
     * @return answer string
     * @throws CalcError
     */
    public String expression(String str) throws CalcError {
        str = str.replace("ANS", "(" + String.valueOf(ans) + ")");
        StringBuilder buf = new StringBuilder();
        Stack<String> op = new Stack<>();
        Stack<BigDecimal> digit = new Stack<>();
        if (str.charAt(0) == '+' || str.charAt(0) == '-')    // prevent +/- prefix
            digit.push(new BigDecimal(0));

        try {
            for (int i = 0; i < str.length(); ++i) {
                char now = str.charAt(i);
                if (Character.isDigit(now) || now == '.') { // eat digit and .
                    buf.append(now);
                    continue;
                }
                if (buf.length() != 0) {    // prevent expression like "-3" error
                    digit.push(new BigDecimal(buf.toString()));
                    buf.setLength(0);
                }

                if (now == '!') {   // factor, highest priority
                    digit.push(Factor(digit.pop()));
                    continue;
                }
                if (now == '(') {
                    if (str.charAt(i + 1) == '-')   // tricks for negative numbers
                        digit.push(BigDecimal.valueOf(0));
                    op.push(String.valueOf(now));
                    continue;
                }

                ClearStack(op, digit, CheckPriority(String.valueOf(now)));
                if (now != ')')
                    op.push(String.valueOf(now));
            }
            if (buf.length() != 0)
                digit.push(new BigDecimal(buf.toString()));     // push remain digit
            ClearStack(op, digit, 255); // final clear
            if (!op.empty() || digit.size() != 1)
                throw new CalcError();
        } catch (NumberFormatException e) {
            throw new CalcError();
        }
        ans = digit.pop();
        return new BigDecimal(String.valueOf(ans)).stripTrailingZeros().toPlainString();
    }

    /**
     * Clear digit and op stack.
     * Returns when meet any operation have higher priority.
     * @param op        operation stack
     * @param digit     digit stack
     * @param priority  clear priority
     * @throws CalcError
     */
    private void ClearStack(Stack<String> op, Stack<BigDecimal> digit, int priority) throws CalcError {
        while (true) {
            if (op.empty() || CheckPriority(op.peek()) > priority)
                break;
            if (priority == 254 && op.peek().equals("(")) {
                op.pop();
                break;
            }
            if (priority == 255 && CheckPriority(op.peek()) == 254)
                throw new CalcError();

            if (digit.empty())
                throw new CalcError();
            BigDecimal digit2 = digit.pop();
            if (digit.empty())
                throw new CalcError();
            BigDecimal digit1 = digit.pop();
            digit.push(SolveOp(digit1, digit2, op.pop()));
        }
    }

    /**
     * Factor operation.
     * @param n Factor digit
     * @return  answer
     * @throws CalcError
     */
    private BigDecimal Factor(BigDecimal n) throws CalcError {
        if (n.compareTo(BigDecimal.valueOf(0))<0)
            throw new CalcError();
        if (n.equals(BigDecimal.valueOf(0)))
            return new BigDecimal(1);
        BigDecimal res = new BigDecimal(1);
        for (BigDecimal i = new BigDecimal(1); i.compareTo(n) <= 0; i = i.add(BigDecimal.valueOf(1)))
            res = res.multiply(i);
        return res;
    }

    /**
     * Check operation's priority.
     * @param op    operation string
     * @return  priority digit
     * @throws CalcError
     * @apiNote priority 254 is ( and ), 255 is reserved for ALL CLEAR
     */
    private int CheckPriority(String op) throws CalcError {
        switch (op) {
            case "*":
                return 10;
            case "/":
                return 10;
            case "%":
                return 10;
            case "+":
                return 20;
            case "-":
                return 20;
            case "(":
                return 254;
            case ")":
                return 254;
            default:
                throw new CalcError();
        }
    }

    /**
     * Solve operation.
     * @param op1   operation left
     * @param op2   operation right
     * @param op    operator
     * @return  answer
     * @throws CalcError
     */
    private BigDecimal SolveOp(BigDecimal op1, BigDecimal op2, String op) throws CalcError {
        switch (op) {
            case "*":
                return op1.multiply(op2);
            case "/":
                return op1.divide(op2,5, RoundingMode.HALF_UP);
            case "+":
                return op1.add(op2);
            case "-":
                return op1.subtract(op2);
            case "%":
                BigDecimal ret = op1.divideAndRemainder(op2)[1];
                if (ret.stripTrailingZeros().toPlainString().indexOf('.') != -1)
                    throw new CalcError();
                return ret;
            default:
                throw new CalcError();
        }
    }
}
