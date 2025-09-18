# Egzamin poprawkowy 2024 – Całki numeryczne (mega‑komentarze)

## Co robimy w projekcie
- Definiujemy **funkcyjny interfejs** `CalculatedFunction` z metodą `double f(double x)`.
- Implementujemy **wielomian** `Polynomial`, który przyjmuje współczynniki (od najwyższego stopnia do stałej) i wylicza wartość `f(x)`.
- Projektujemy **abstrakcyjną klasę** `Integral` (granice całkowania + referencja do `CalculatedFunction`), z metodą abstrakcyjną `calculate()`.
- Tworzymy **metodę trapezów** `TrapezoidMethod` (z `n` podziałami) – klasyczny wzór złożony trapezów.
- Tworzymy **metodę Monte Carlo** `MonteCarloMethod` (z `n` próbami) – średnia wartości * długość przedziału.
- `AnyFunction` – klasa owijająca dowolny `java.util.function.Function<Double,Double>` jako `CalculatedFunction` (np. `new AnyFunction(x -> 2*Math.sin(x))`).
- `LagrangeInterpolatedFunction` – funkcja zbudowana z **interpolacji Lagrange’a** na podstawie punktów `(x_i, y_i)` wczytanych z CSV.
  - ma metodę `static LagrangeInterpolatedFunction fromCsv(Path)` – wczytuje pary `x,y` (dowolna kolejność wierszy).
  - przy obliczaniu całki **sprawdzamy**, że przedział całkowania mieści się **wewnątrz** zakresu znanych węzłów (w przeciwnym razie `IllegalArgumentException`).

## Szybki test
- Całka z `x^3 + 2x^2 − 8x − 1` po `[0, 3]` metodą trapezów dla `n = 16` daje **≈ 39** (sprawdzenie w `Main`).

## Struktura
- `CalculatedFunction.java`, `Polynomial.java`
- `Integral.java`, `TrapezoidMethod.java`, `MonteCarloMethod.java`
- `AnyFunction.java`
- `LagrangeInterpolatedFunction.java` (wbudowana prosta implementacja interpolacji Lagrange’a)
- `Main.java` – uruchomienie przykładów / „testów ręcznych”

