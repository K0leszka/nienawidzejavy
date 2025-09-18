// Krok 7: Metoda Monte Carlo – n losowań z [a,b].
// Wynik ≈ (b-a) * (średnia z f(U)), gdzie U ~ U(a,b).

import java.util.Random;

public class MonteCarloMethod extends Integral {
    private final int n;
    private final Random rng;

    public MonteCarloMethod(CalculatedFunction func, double a, double b, int n) {
        this(func, a, b, n, new Random());
    }

    public MonteCarloMethod(CalculatedFunction func, double a, double b, int n, Random rng) {
        super(func, a, b);
        if (n <= 0) throw new IllegalArgumentException("n musi być > 0");
        this.n = n;
        this.rng = rng == null ? new Random() : rng;
    }

    @Override
    public double calculate() {
        double length = b - a;
        double sum = 0.0;
        for (int i = 0; i < n; i++) {
            double u = a + rng.nextDouble() * length;
            sum += func.f(u);
        }
        return length * (sum / n);
    }
}
