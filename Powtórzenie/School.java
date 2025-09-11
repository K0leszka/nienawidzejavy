import javax.security.auth.Subject;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class School {
    private List<Student> students;
    private Map<Integer, Teacher> teachers;

    public List<Student> getStudents() {
        return students;
    }

    public Map<Integer, Teacher> getTeachers() {
        return teachers;
    }

    public School() {
        students = new ArrayList<>();
        teachers = new HashMap<>();
    }

    public void fillStudents(String file) {
        try (BufferedReader br = new BufferedReader((new FileReader(file)))) {
            String line;
            while ((line = br.readLine()) != null) {
                Student s = Student.fromCsvline(line);
                if (s != null) {

                    students.add(s);
                }

            }
            System.out.println("Wczytanie uczniów sie powiodło");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void findGrades(double grade) {
        boolean found = false;
        for (Student s : students) {
            if (s.getGrade() == grade) {
                System.out.println(s.getName());
                found = true;
            }
        }
        if (found == false) {
            System.out.println("Nie znaleziono ucznia ze średnia " + grade);
        }
    }

    public void findStudents(String name) {
        boolean found = false;
        for (Student s : students) {
            if (s.getName().equals(name)) {
                System.out.println(s.getName() + " " + "ID: " + s.getId() + " " + "Grade: " + s.getGrade());
                found = true;
            }
        }
        if (found == false) {
            System.out.println("Nie znaleziono ucznia");
        }
    }

    public void findNobleStudents() {
        System.out.println("Uczniowe ze średnią powyżej 4.75");
        double NobleFactor = 4.75;
        for (Student s : students) {
            if (s.getGrade() >= 4.75) {
                System.out.println("Imie i naziwsko: " + s.getName() + " " + "Średnia: " + s.getGrade());
            }
        }
    }

    public void findBestStudent() {
        if (students.isEmpty()) return;

        Student best = students.get(0);
        for (Student s : students) {
            if (s.getGrade() >= best.getGrade()) {
                best = s;
            }
        }
        System.out.println("Najlepszy student: " + " " + best.getName() + "ze średnią" + " " + best.getGrade());
    }

    public void findStudentsBelow(double grade) {
        for (Student s : students) {
            if (s.getGrade() <= grade) {
                System.out.println(s.getName() + " " + "ID: " + s.getId() + " " + "Grade: " + s.getGrade());
            }
        }

    }

    public void calculateAverageGrade() {
        double sum = 0;
        for (Student s : students) {
            sum += s.getGrade();
        }
        double average = sum / students.size();
        System.out.println("Average grade: " + average);
    }

    public void sortByNameAtoZ() {
        students.sort(Comparator.comparing(Student::getName));
        System.out.println(students);
    }

    public void sortByNameZtoA() {
        students.sort(Comparator.comparing(Student::getName).reversed());
        System.out.println(students);
    }

    public void mapIdTOStudent() {
        Map<Integer, String> IdsStudentMap = new HashMap<>();
        for (Student s : students) {
            IdsStudentMap.put(s.getId(), s.getName());
        }
        for (Map.Entry<Integer, String> entry : IdsStudentMap.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
    }

    public void upgradeLowGrades(double minGrade) {
        for (Student s : students) {
            if (s.getGrade() < minGrade) {
                s.setGrade(minGrade);
            }

        }
        for (Student s : students) {
            System.out.println(s.getName() + " " + "ID: " + s.getId() + " " + "Grade: " + s.getGrade());
        }
    }

    public void printRaport() {
        System.out.println("=== Raport szkoły ===");
        System.out.println("Lista studentów:");
        for (Student s : students) {
            System.out.println(s.getId() + ": " + s.getName() + " - " + s.getGrade());
        }
        System.out.println("\nStudenci z oceną >= 4.0:");
        findGrades(4.0);
        findBestStudent();
        System.out.println("\nStudenci z oceną < 3.0:");
        findStudentsBelow(3.0);
    }


    public void fillTeachers(String file) throws FileNotFoundException {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                Teacher t = Teacher.fromCsvLine(line);
                if (t != null) {
                    teachers.put(t.getId(), t);
                }
            }
            System.out.println("Wczytanie nauczycieli powiodło się");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void findById(int id) {
        boolean found = false;
        for (Teacher t : teachers.values()) {
            if (t.getId() == id) {
                System.out.println(t.getName() + " " + t.getSubject());
                found = true;
            }

        }
        if (!found) {
            System.out.println("Nie znaleziono nauczyciela o id: " + id);
        }
    }

    public void findBySubject(String subject) {
        boolean found = false;
        for (Teacher t : teachers.values()) {
            if (t.getSubject().equals(subject)) {
                System.out.println(t.getName() + " " + t.getId());
                found = true;
            }
        }
        if (!found) {
            System.out.println("Nie znaleziono przedmiotu i odpowiadającemu za niego nauczyciela");
        }
    }

    public void findHighestId() {
        Teacher maxTeacher = null;
        int higestId = 0;
        for (Teacher t : teachers.values()) {
            if (t.getId() > higestId) {
                higestId = t.getId();
                maxTeacher = t;
            }
        }
        System.out.println("Nauczyciel z najwiekszym id = " + maxTeacher.getId() + maxTeacher.getName());
    }

    public void sortBySurname() {
        List<Teacher> teachersList = new ArrayList<>(teachers.values());
        teachersList.sort(Comparator.comparing(Teacher::getName));
        System.out.println(teachersList);
    }


    public void findLetterInSurname(String letter) {
        boolean found = false;
        for (Teacher t : teachers.values()) {
            String lastName = t.getName().substring(t.getName().lastIndexOf(" ") + 1);
            if (lastName.startsWith(letter)) {
                System.out.println(t.getId() + " " + t.getName());
                found = true;
            }
        }
        if (!found) {
            System.out.println("Nie znaleziono litery która występuje");
        }
    }

    public void findBySubjectMap(String subject) {
        Map<String, List<Teacher>> subjectMap = new HashMap<>();

        for(Teacher t: teachers.values()){
            subjectMap.computeIfAbsent(t.getSubject(), k -> new ArrayList<>());
            subjectMap.get(t.getSubject()).add(t);
        }
        for (Map.Entry<String, List<Teacher>> entry : subjectMap.entrySet()) {
                System.out.println(entry.getKey() + " " + entry.getValue());
            }
        }



    }








