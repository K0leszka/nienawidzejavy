import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class Main {
    public static void main(String[] args) {

        List<Person> parents = new ArrayList<>();

        Person parent1 = new Person("Jan", "Kowalski", LocalDate.of(1980,2,12));
        Person parent2 = new Person("John", "Doe", LocalDate.of(1991,4,14));
        Person parent3 = new Person("John", "Doe", LocalDate.of(1996,1,12));


        parent1.adopt("Otylia","Kowalska", LocalDate.of(2012,6,16));
        parent1.adopt("Sigmund","Kowalski", LocalDate.of(2010,8,21));
        parent1.adopt("Jakub", "Kowalski", LocalDate.of(2014,6,24));
        parent1.getYoungestChild();
        parent1.getOldestChild();
        parent1.getChildren();

        Family family = new Family();
        family.addPerson(parent1, parent2, parent3);


        List<Person> jans = family.getPerson("JohnDoe");
        for (Person jan : jans) {
            System.out.println(jan.getName() + " " + jan.getSurname() + " " + jan.getBirthdate());
        }



    }
}
