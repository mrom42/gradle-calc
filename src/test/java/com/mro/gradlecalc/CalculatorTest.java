package com.mro.gradlecalc;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
/**
* Main Service
*/
public class CalculatorTest {
private Calculator calculator = new Calculator();
@Test
public void testSum() {
assertEquals(5, calculator.sum(2, 3));
}
}
