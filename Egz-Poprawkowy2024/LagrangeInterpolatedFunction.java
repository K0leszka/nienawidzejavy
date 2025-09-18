// Krok 10-11-12: LagrangeInterpolatedFunction – funkcja będąca wynikiem interpolacji Lagrange'a.
// Implementacja własna (żeby nie zależeć od zewnętrznego JARa).
// - w konstruktorze przyjmujemy punkty (x_i, y_i) – x muszą być unikalne;
// - f(x) obliczamy wzorem Lagrange'a;
// - fromCsv(Path) wczytuje kolumny x,y (dowolna kolejność wierszy);
// - getMinX()/getMaxX() zwracają zakres węzłów – przy całkowaniu sprawdzamy, czy [a,b] mieści się w (minX, maxX).

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

public class LagrangeInterpolatedFunction implements CalculatedFunction {
    private final double[] xs;
    private final double[] ys;

    public LagrangeInterpolatedFunction(double[] xs, double[] ys) {
        if (xs == null || ys == null || xs.length != ys.length || xs.length < 2) {
            throw new IllegalArgumentException("Wymagane co najmniej 2 punkty i równe długości tablic.");
        }
        // Kopie + sprawdzenie unikalności x
        this.xs = xs.clone();
        this.ys = ys.clone();
        // posortuj po x (nie wymagane przez wzór, ale ułatwi weryfikacje zakresu)
        sortByX(this.xs, this.ys);
        for (int i = 1; i < this.xs.length; i++) {
            if (this.xs[i] == this.xs[i-1]) {
                throw new IllegalArgumentException("Wartości x muszą być unikalne.");
            }
        }
    }

    private static void sortByX(double[] x, double[] y) {
        // prosty selection-sort dla pewności braku błędów z NaN
        for (int i = 0; i < x.length; i++) {
            int min = i;
            for (int j = i+1; j < x.length; j++) {
                if (x[j] < x[min]) min = j;
            }
            if (min != i) {
                double tx = x[i]; x[i] = x[min]; x[min] = tx;
                double ty = y[i]; y[i] = y[min]; y[min] = ty;
            }
        }
    }

    @Override
    public double f(double x) {
        // klasyczny wzór: sum_{i} y_i * L_i(x), gdzie
        // L_i(x) = Π_{j != i} (x - x_j)/(x_i - x_j)
        double sum = 0.0;
        int n = xs.length;
        for (int i = 0; i < n; i++) {
            double Li = 1.0;
            for (int j = 0; j < n; j++) {
                if (j == i) continue;
                Li *= (x - xs[j]) / (xs[i] - xs[j]);
            }
            sum += ys[i] * Li;
        }
        return sum;
    }

    public double minX() { return xs[0]; }
    public double maxX() { return xs[xs.length-1]; }

    public static LagrangeInterpolatedFunction fromCsv(Path csv) {
        try {
            List<String> lines = Files.readAllLines(csv).stream()
                    .filter(l -> l != null && !l.isBlank())
                    .collect(Collectors.toList());
            // dopuszczamy nagłówek – jeśli zawiera litery, pomijamy pierwszą linię
            if (lines.get(0).matches(".*[a-zA-Z].*")) {
                lines = lines.subList(1, lines.size());
            }
            List<Double> x = new ArrayList<>();
            List<Double> y = new ArrayList<>();
            for (String line : lines) {
                String[] parts = line.split("[,;\\t]");
                if (parts.length < 2) continue;
                x.add(Double.parseDouble(parts[0].trim()));
                y.add(Double.parseDouble(parts[1].trim()));
            }
            double[] xs = x.stream().mapToDouble(Double::doubleValue).toArray();
            double[] ys = y.stream().mapToDouble(Double::doubleValue).toArray();
            return new LagrangeInterpolatedFunction(xs, ys);
        } catch (IOException e) {
            throw new RuntimeException("Błąd czytania CSV: " + csv, e);
        }
    }
}
