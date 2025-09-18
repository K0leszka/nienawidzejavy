// Krok 8: AnyFunction – owijka na dowolną funkcję lambda jako CalculatedFunction.

import java.util.function.Function;

public class AnyFunction implements CalculatedFunction {
    private final Function<Double, Double> fun;

    public AnyFunction(Function<Double, Double> fun) {
        if (fun == null) throw new IllegalArgumentException("Funkcja nie może być null.");
        this.fun = fun;
    }

    @Override
    public double f(double x) {
        return fun.apply(x);
    }
}
