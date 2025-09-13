import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public abstract class Country {
    private final String name;
    private static String filePath1;
    private static String filePath2;
    private static final DateTimeFormatter CSV_DATE = DateTimeFormatter.ofPattern("M/d/yy");

    public Country(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getFilePath1() {
        return filePath1;
    }

    public String getFilePath2() {
        return filePath2;
    }

    public abstract int getConfirmedCases(LocalDate date);
    public abstract int getDeaths(LocalDate date);
    public abstract List<LocalDate> getAllDates();

    public static void setFiles(String filePath1, String filePath2) throws FileNotFoundException {
        File file1 = new File(filePath1);
        File file2 = new File(filePath2);

        if (file1.exists() || file1.isFile()) {
            System.out.println("File exists");
        } else {
            throw new FileNotFoundException("File not exists" + Country.filePath1);
        }


        if (file2.exists() || file2.isFile()) {
            System.out.println("File exists");
        } else {
            throw new FileNotFoundException("File not exists" + Country.filePath2);
        }
    }


    public static Country fromCsv(String name, String casesPath, String deathsPath) throws IOException {
        try (BufferedReader brCases = new BufferedReader(new FileReader(casesPath));
             BufferedReader brDeaths = new BufferedReader(new FileReader(deathsPath))) {

            // 1) Czytamy 2 pierwsze wiersze (kraj + prowincje) z obu plików
            String countriesHeaderCases = brCases.readLine();    // np. "Country/Region;Poland;Australia;Australia;..."
            String provincesHeaderCases = brCases.readLine();    // np. "Province/State;nan;NSW;VIC;..."
            String countriesHeaderDeaths = brDeaths.readLine();   // (analogicznie)
            String provincesHeaderDeaths = brDeaths.readLine();

            // 2) Używamy Twojej metody: znajdź blok kolumn dla podanego kraju
            CountryColumns cc = getCountryColumns(countriesHeaderCases, name);
            int start = cc.getFirstColumnIndex();       // pierwsza kolumna kraju
            int count = cc.getColumnCount();      // ile kolumn (czyli ile prowincji lub 1)

            // 3) Nazwy prowincji bierzemy z drugiego wiersza (jeśli są)
            String[] provinceNames = provincesHeaderCases.split(";");

            if (count == 1 || provinceNames[start].equalsIgnoreCase("nan")) {
                // ---- wariant: kraj bez prowincji ----
                CountryWithoutProvinces country = new CountryWithoutProvinces(name);

                String lineCases, lineDeaths;
                while ((lineCases = brCases.readLine()) != null
                        && (lineDeaths = brDeaths.readLine()) != null) {

                    String[] c = lineCases.split(";");
                    String[] d = lineDeaths.split(";");

                    LocalDate date = LocalDate.parse(c[0].trim(), CSV_DATE); // kol. 0 = data
                    int cases = Integer.parseInt(c[start]);   // kolumna "start" należy do kraju
                    int deaths = Integer.parseInt(d[start]);   // ta sama kolumna w pliku zgonów

                    country.addDailyStatistic(date, cases, deaths); // <--- UŻYCIE TWOJEJ STRUKTURY
                }
                return country;

            } else {
                // ---- wariant: kraj z prowincjami ----
                Country[] provinces = new Country[count];
                for (int i = 0; i < count; i++) {
                    // bierzemy nazwy prowincji z 2. wiersza; jeśli "nan", nadaj nazwę domyślną
                    String pName = provinceNames[start + i];
                    if (pName.equalsIgnoreCase("nan")) pName = name + " - province " + (i + 1);
                    provinces[i] = new CountryWithoutProvinces(pName);
                }

                CountryWithProvinces country = new CountryWithProvinces(name, provinces);

                String lineCases, lineDeaths;
                while ((lineCases = brCases.readLine()) != null
                        && (lineDeaths = brDeaths.readLine()) != null) {

                    String[] c = lineCases.split(";");
                    String[] d = lineDeaths.split(";");

                    LocalDate date = LocalDate.parse(c[0].trim(), CSV_DATE);

                    // dla każdej prowincji z bloku start..start+count-1
                    for (int i = 0; i < count; i++) {
                        int cases = Integer.parseInt(c[start + i]);
                        int deaths = Integer.parseInt(d[start + i]);

                        // każda prowincja to CountryWithoutProvinces → wołamy addDailyStatistic
                        ((CountryWithoutProvinces) provinces[i]).addDailyStatistic(date, cases, deaths);
                    }
                }
                return country;
            }
        }
    }

    public static Country[] fromCsv(String[] names, String casesPath, String deathsPath)
            throws IOException {
        List<Country> result = new ArrayList<>();

        for (String name : names) {
            try {
                Country c = fromCsv(name, casesPath, deathsPath); // używamy wersji pojedynczej
                result.add(c);
            } catch (CountryNotFoundException e) {
                // wymaganie z zadania: wypisz i pomiń
                System.out.println(e.getMessage());
            }
        }

        return result.toArray(new Country[0]);
    }

    private static CountryColumns getCountryColumns(String firstLine, String desiredCountry) throws IOException {
        String[] columns = firstLine.split(";");
        int startIndex = -1;
        int count = 0;

        for (int i = 0; i < columns.length; i++) {
            if (columns[i].equalsIgnoreCase(desiredCountry)) {
                if (startIndex == -1) {
                    startIndex = i;
                }
                count++;
            } else {
                if (startIndex != -1) {
                    break;
                }
            }
        }
        if (startIndex == -1) {
            throw new CountryNotFoundException("Nie znaleziono kraju: " + desiredCountry);
        }
        return new CountryColumns(startIndex, count);
    }


    public static void sortByDeaths(List<Country> countries, LocalDate start, LocalDate end) {
        // sortujemy malejąco po sumie zgonów w zakresie dat
        Collections.sort(countries, new Comparator<Country>() {
            @Override
            public int compare(Country c1, Country c2) {
                int deaths1 = sumDeaths(c1, start, end);
                int deaths2 = sumDeaths(c2, start, end);
                // malejąco: większa liczba zgonów ma być "mniejsza" w porządku sortowania
                return Integer.compare(deaths2, deaths1);
            }
        });
    }

    private static int sumDeaths(Country c, LocalDate start, LocalDate end) {
        int sum = 0;
        LocalDate current = start;
        while (!current.isAfter(end)) { // dopóki current <= end
            sum += c.getDeaths(current);
            current = current.plusDays(1);
        }
        return sum;
    }

    public void saveToDataFile(String outputPath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath))) {
            for (LocalDate date : getAllDates()) {
                String line = OUTPUT_DATE.format(date) + "\t"
                        + getConfirmedCases(date) + "\t"
                        + getDeaths(date);
                writer.write(line);
                writer.newLine();
            }
        }
    }


    public static class CountryColumns {
        private final int firstColumnIndex;
        private final int columnCount;


        public CountryColumns(int firstColumnIndex, int columnCount) {
            this.firstColumnIndex = firstColumnIndex;
            this.columnCount = columnCount;
        }

        public int getFirstColumnIndex() {
            return firstColumnIndex;
        }

        public int getColumnCount() {
            return columnCount;
        }
    }
}
