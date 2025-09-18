// IntegralTest – testy jednostkowe JUnit 5 zgodnie z zadaniem (Krok 6 i Krok 12).
// Aby uruchomić, trzeba mieć JUnit 5 w classpath (np. org.junit.jupiter.api).

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class IntegralTest {

    @Test
    void trapezoidPolynomial() {
        // wielomian: x^3 + 2x^2 - 8x - 1
        CalculatedFunction poly = new Polynomial(1., 2., -8., -1.);
        Integral I = new TrapezoidMethod(poly, 0.0, 3.0, 16);
        double result = I.calculate();
        // wartość powinna być około 39, dopuszczamy błąd ~1
        assertEquals(39.0, result, 1.0);
    }

    @Test
    void lagrangeOutOfRangeThrows() {
        // prosta parabola przez (0,0), (1,1), (2,4)
        double[] xs = {0.0, 1.0, 2.0};
        double[] ys = {0.0, 1.0, 4.0};
        LagrangeInterpolatedFunction f = new LagrangeInterpolatedFunction(xs, ys);
        // tworzymy całkę poza zakresem [0,2] -> powinno rzucić wyjątek
        Integral I = new TrapezoidMethod(f, -1.0, 1.0, 10);
        assertThrows(IllegalArgumentException.class, I::calculate);
    }
}
