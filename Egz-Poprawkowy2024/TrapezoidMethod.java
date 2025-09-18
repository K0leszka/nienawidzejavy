// Krok 5-6: Metoda trapezów – wersja złożona dla n równych podprzedziałów.
// Wzór: h=(b-a)/n; suma = 0.5*f(a) + Σ_{i=1..n-1} f(a+i*h) + 0.5*f(b); wynik = h*suma.

public class TrapezoidMethod extends Integral {
    private final int n;

    public TrapezoidMethod(CalculatedFunction func, double a, double b, int n) {
        super(func, a, b);
        if (n <= 0) throw new IllegalArgumentException("n musi być > 0");
        this.n = n;
    }

    @Override
    public double calculate() {
        double h = (b - a) / n;
        double sum = 0.5 * func.f(a) + 0.5 * func.f(b);
        for (int i = 1; i < n; i++) {
            double x = a + i * h;
            sum += func.f(x);
        }
        return h * sum;
    }
}
