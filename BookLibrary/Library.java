import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Library {
    private List<Book> books;
    private int errorCode;

    public Library() {
        books = new ArrayList<>();
    }

    public int getErrorCode() {
        return errorCode;
    }

    public List<Book> getBooks() {
        return new ArrayList<>(books);
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void findByAuthor(String author) {
        for (Book book : books) {
            if (book.getAuthor().equals(author)) {
                System.out.println(book);
            }
        }
    }

    public void findByTtitle(String title) throws MyCustomExepction {
        for (Book book : books) {
            if (book.getTitle().equals(title)) {
                System.out.println(book);
                return;
            }
        }
        throw new MyCustomExepction("Nie znaleziono książki: " + title, 404);
    }

    public void saveToFile(String filename) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            for (Book book : books) {
                bw.write(book.toString());
                bw.newLine();
            }
            System.out.println("Dane zapisane do pliku: " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readFromFile(String filename){
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null){
                String[] split = line.split(";");
                if (split.length == 3){
                    String title =  split[0];
                    String author = split[1];
                    int year = Integer.parseInt(split[2]);
                    books.add(new Book(title, author, year));
                }
            }
            System.out.println("Dane wczytane z pliku: " + filename);
        } catch (IOException e){
            System.out.println("Nie znaleziono pliku: " + filename);
        }

    }

    public void countBooksByAuthor(String author) {
        int authorBookCount = 0;
        for (Book book : books) {
            if (book.getAuthor().equals(author)) {
                authorBookCount++;


            }

        }

            if(authorBookCount == 0){
                System.out.println("Podany autor nie istnieje w bibliotece.");
            } else {
                System.out.println("Ilość książek podanego autora: " + authorBookCount);
            }
    }
}

