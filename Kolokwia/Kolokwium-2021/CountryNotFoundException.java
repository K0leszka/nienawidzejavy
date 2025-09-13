public class CountryNotFoundException extends RuntimeException {
    private String name;

    public CountryNotFoundException(String name) {
        this.name = name;
    }
}
