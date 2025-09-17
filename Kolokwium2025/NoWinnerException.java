// KROK-12: Własny wyjątek gdy w turze nikt nie przekroczył 50%.
public class NoWinnerException extends RuntimeException {
    public NoWinnerException(String message) { super(message); }
}
