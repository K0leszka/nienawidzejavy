// Main – proste testy „ręczne” zgodnie z treścią zadania.
// 1) Trapezy dla wielomianu x^3 + 2x^2 − 8x − 1 na [0,3] z n=16 ≈ 39.
// 2) Monte Carlo (losowe – tylko podgląd).
// 3) AnyFunction z lambdą.
// 4) LagrangeInterpolatedFunction – pokaz użycia (bez konkretnego CSV w tym demie).

import java.nio.file.Path;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        CalculatedFunction poly = new Polynomial(1., 2., -8., -1.); // x^3 + 2x^2 − 8x − 1
        TrapezoidMethod trap = new TrapezoidMethod(poly, 0.0, 3.0, 16);
        double approx = trap.calculate();
        System.out.println("Trapezy (n=16) dla ∫_0^3 (x^3 + 2x^2 − 8x − 1) dx ≈ " + approx + " (oczekiwanie ≈ 39)");

        MonteCarloMethod mc = new MonteCarloMethod(poly, 0.0, 3.0, 200_000, new Random(123));
        System.out.println("Monte Carlo ≈ " + mc.calculate());

        AnyFunction sin2 = new AnyFunction(x -> 2 * Math.sin(x));
        System.out.println("AnyFunction f(pi/2) = " + sin2.f(Math.PI/2));

        // Przykład Lagrange'a (jeśli masz CSV z punktami, odkomentuj i podaj ścieżkę):
        // LagrangeInterpolatedFunction L = LagrangeInterpolatedFunction.fromCsv(Path.of("punkty.csv"));
        // Integral I = new TrapezoidMethod(L, a, b, 1000);
        // Przed policzeniem I.calculate() upewnij się, że [a,b] mieści się w (L.minX(), L.maxX()).
    }
}
