import java.time.LocalDate;
import java.util.*;

public class Person implements Comparable<Person> {
    private String name;
    private String surname;
    private LocalDate birthdate;
    private Set<Person> children = new TreeSet<Person>();

    public Person(String name, String surname, LocalDate birthdate) {
        this.name = name;
        this.surname = surname;
        this.birthdate = birthdate;
        this.children = new TreeSet<>(Comparator.reverseOrder());
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public Set<Person> getChildren() {
       for(Person child : children) {
           System.out.println(child.getName());
       }
       return children;
    }

    public boolean adopt(String name, String surname, LocalDate birthdate) {
        Person child = new Person(name, surname, birthdate);
        if (children.add(child)) {
            System.out.println("Dodano członka rodziny powiodło się");
            return true;
        } else {
            System.out.println("Dodanie nie powiodło się");
            return false;
        }

    }

    public void getYoungestChild() {
        if (children.isEmpty()) {
            System.out.println("No children");
            return;
        }
        Person youngestChild = null;
        for (Person child : children) {
            if (youngestChild == null) {
                youngestChild = child;
            } else {
                if (child.compareTo(youngestChild) > 0) {
                    youngestChild = child;
                }

            }

        }
        System.out.println("Najmłodsze dziecko to: " + youngestChild.getName());
    }


    public void getOldestChild(){
        if (children.isEmpty()) {
            System.out.println("No children");
            return;
        }
        Person oldestChild = null;
        for(Person child : children){
            if (oldestChild == null) {
                oldestChild = child;
            } else {
            if (child.compareTo(oldestChild) < 0) {
                oldestChild = child;
                }

            }

        }
        System.out.println("Najstarsze dziecko to: " + oldestChild.getName());
    }



    @Override
    public int compareTo(Person other) {
        return this.getBirthdate().compareTo(other.getBirthdate());
    }

}

