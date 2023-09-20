package com.cst438;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CalculatorTest {

    @Test
    public void testAdd() {
        // Créez une instance de la classe à tester
        Calculator calculator = new Calculator();

        // Appelez la méthode à tester
        int result = calculator.add(2, 3);

        // Vérifiez que le résultat est correct
        assertEquals(5, result, "La méthode add doit renvoyer 5 lorsque 2 et 3 sont ajoutés.");
    }
}
