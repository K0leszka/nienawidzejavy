// Krok 1: Interfejs funkcyjny – jedna metoda abstrakcyjna f(double)
// Dzięki adnotacji @FunctionalInterface kompilator pilnuje, że jest dokładnie jedna metoda abstr.
@FunctionalInterface
public interface CalculatedFunction {
    double f(double x);
}
