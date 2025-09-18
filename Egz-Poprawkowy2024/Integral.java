// Krok 4: Abstrakcyjna klasa reprezentująca całkę oznaczoną ∫_a^b f(x) dx.
// Trzyma referencję do funkcji i granice. Metoda calculate() – do zaimplementowania w podklasach.

public abstract class Integral {
    protected final CalculatedFunction func;
    protected final double a;
    protected final double b;

    public Integral(CalculatedFunction func, double a, double b) {
        if (func == null) throw new IllegalArgumentException("Funkcja nie może być null.");
        if (Double.isNaN(a) || Double.isNaN(b) || Double.isInfinite(a) || Double.isInfinite(b))
            throw new IllegalArgumentException("Granice muszą być liczbami skończonymi.");
        this.func = func;
        this.a = a;
        this.b = b;
    }

    public abstract double calculate();
}
