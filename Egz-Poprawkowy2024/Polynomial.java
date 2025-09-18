// Krok 2-3: Wielomian jednej zmiennej implementujący CalculatedFunction.
// Konstruktor varargs przyjmuje współczynniki od najwyższego stopnia do wyrazu stałego.
// Przykład: new Polynomial(1., 2., -8., -1.) -> x^3 + 2x^2 - 8x - 1

public class Polynomial implements CalculatedFunction {
    private final double[] a; // współczynniki a0..an (a0 dla najwyższego stopnia)

    public Polynomial(double... coefficientsDesc) {
        if (coefficientsDesc == null || coefficientsDesc.length == 0) {
            throw new IllegalArgumentException("Podaj przynajmniej jeden współczynnik.");
        }
        this.a = coefficientsDesc.clone();
    }

    // Horner – stabilny i szybki sposób liczenia wartości wielomianu
    @Override
    public double f(double x) {
        double acc = 0.0;
        for (double coef : a) {
            acc = acc * x + coef;
        }
        return acc;
    }
}
