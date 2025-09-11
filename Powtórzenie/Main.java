import java.io.FileNotFoundException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
         School school = new School();
         school.fillStudents("src/students.csv");
         for(Student student : school.getStudents()){
             System.out.println(student.toString());
         }
         school.printRaport();

         school.fillTeachers("src/teachers.csv");
         for(Teacher t : school.getTeachers().values()){
             System.out.println(t.toString());
         }

         school.findById(11);
         school.findBySubject("Chemia");
         school.findHighestId();
         school.sortBySurname();
         System.out.println(" ");
         school.findLetterInSurname("H");
         school.findBySubjectMap("Chemia");
        }
    }
