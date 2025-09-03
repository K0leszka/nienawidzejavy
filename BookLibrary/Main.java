public class Main {
    public static void main(String[] args) throws MyCustomExepction {


        Library library = new Library();
        Book b1 = new Book("Obcy", "Albert Camus" , 1942);
        Book b2 = new Book("Proces", "Franz Kafka" , 1925);
        Book b3 = new Book("Komu bije dzwon", "Ernest Hemingway", 1940);
        EBook eb0 = new EBook("Żart", "Milan Kundera", 1967, 102251);


        library.addBook(b1);
        library.addBook(b2);
        library.addBook(b3);
        library.addBook(eb0);
        library.addBook(new Book("Dżuma", "Albert Camus", 1947));
        library.addBook(new EBook("D","2",2001, 20412));
        System.out.println(library.getBooks());


       library.saveToFile("books.txt");
       library.countBooksByAuthor("Franz Kafka");

    }
}