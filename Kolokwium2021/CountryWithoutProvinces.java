import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CountryWithoutProvinces extends Country {
    private List<LocalDate> datesList = new ArrayList<LocalDate>();
    private List<Integer> casesList = new ArrayList<>();
    private List<Integer> deathsList = new ArrayList<>();
    private static final DateTimeFormatter CSV_DATE = DateTimeFormatter.ofPattern("M/d/yy");

    public CountryWithoutProvinces(String name) {
        super(name);
    }
    public void dataFromDay(String date){


    }


    public void fillArrayLists(String filepathDeaths, String filepathdCases) {
        try (BufferedReader brCases = new BufferedReader(new FileReader(filepathdCases));
            BufferedReader brDeaths = new BufferedReader(new FileReader(filepathDeaths))) {

            String headerCases = brCases.readLine();
            String headerDeaths = brDeaths.readLine();

            String lineCases;
            String lineDeaths;


            while ((lineCases = brCases.readLine()) != null && (lineDeaths = brDeaths.readLine()) != null) {
                String[] dataCases = lineCases.split(";");
                String[] dataDeaths = lineDeaths.split(";");
                LocalDate date = LocalDate.parse(dataCases[0].trim(), CSV_DATE);
                int cases = Integer.parseInt(dataCases[1]);
                int deaths = Integer.parseInt(dataDeaths[2]);

                addDailyStatistic(date,cases, deaths);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void addDailyStatistic(LocalDate date, int cases, int deaths){
        datesList.add(date);
        casesList.add(cases);
        deathsList.add(deaths);
    }
    @Override
    public int getConfirmedCases(LocalDate date) {
        int idx = datesList.indexOf(date);
        return casesList.get(idx); // zakładamy, że data istnieje
    }

    @Override
    public int getDeaths(LocalDate date) {
        int idx = datesList.indexOf(date);
        return deathsList.get(idx); // zakładamy, że data istnieje
    }

    public List<LocalDate> getAllDates() {
        return datesList; // to Twoja lista dat
    }
}



