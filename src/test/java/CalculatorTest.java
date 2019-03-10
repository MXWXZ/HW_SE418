import org.junit.Test;

import static org.junit.Assert.*;

public class CalculatorTest {

    @Test
    public void expression() {
        Calculator calc = new Calculator();
        try {
            assertEquals(calc.expression("ANS"), "0");
            assertEquals(calc.expression("ANS*2"), "0");
            assertEquals(calc.expression("1+(-3)"), "-2");
            assertEquals(calc.expression("-5!"), "-120");
            assertEquals(calc.expression("11*12/(1+(1/2))+5*(6-4)*31"),"398");
            assertEquals(calc.expression("5!"),"120");
            assertEquals(calc.expression("+3"),"3");
            assertEquals(calc.expression("ANS!!"),"720");
            assertEquals(calc.expression("3"),"3");
            assertEquals(calc.expression("-ANS!!-5%2*(2*2-0!)!!"),"-1440");
            assertEquals(calc.expression("-1-1440"),"-1441");
            assertEquals(calc.expression("3*ANS-ANS"),"-2882");
            assertEquals(calc.expression("78+3*0!"),"81");
            assertEquals(calc.expression("0.98729/2"),"0.49365");
            assertEquals(calc.expression("128%42"),"2");
            assertEquals(calc.expression("-3"),"-3");
            assertTrue(expression_error(calc,"1+4+5*-3"));
            assertTrue(expression_error(calc,"q3"));
            assertTrue(expression_error(calc,"(-5)!"));
        } catch (CalcError e) {
            fail("Error detect!");
        }
    }

    public boolean expression_error(Calculator calc,String str) {
        try {
            calc.expression(str);
        } catch (CalcError e) {
            return true;
        }
        return false;
    }
}