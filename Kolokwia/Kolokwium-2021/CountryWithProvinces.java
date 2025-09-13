import java.time.LocalDate;
import java.util.List;

public class CountryWithProvinces extends Country {
    private Country[] provinces;


    public Country[] getProvinces(){
        return provinces;
    }

    public CountryWithProvinces(String name, Country[] provinces) {
        super(name);
        this.provinces = provinces;
    }

    @Override
    public int getConfirmedCases(LocalDate date) {
        int sum = 0;
        for (Country p : provinces) {
            sum += p.getConfirmedCases(date);
        }
        return sum;
    }

    @Override
    public int getDeaths(LocalDate date) {
        int sum = 0;
        for (Country p : provinces) {
            sum += p.getDeaths(date);
        }
        return sum;
    }

    @Override
    public List<LocalDate> getAllDates() {
        if (provinces.length > 0) {
            return provinces[0].getAllDates();
        }
        return List.of();
    }


}
