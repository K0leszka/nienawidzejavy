import java.util.ArrayList;

public class Biblioteka {
    private String title;
    private int year;
    private ArrayList<Ksiazka> ksiazki;

    public Biblioteka() {
        this.title = title;
        this.year = year;
        ksiazki = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }
    public int getYear() {
        return year;
    }

    public void wyswietlKsiazki(){
        for(Ksiazka k: ksiazki){
            System.out.println(k.getTitle() + " " +  k.getYear());
        }
    }

    public void dodajKsiazki(String title, int year){
        ksiazki.add(new Ksiazka(title, year));
    }


}
