import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Student {
    private int id;
    private String name;
    private double grade;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public Student(int id, String name, double grade) {
        this.id = id;
        this.name = name;
        this.grade = grade;

    }

    public static Student fromCsvline(String line) {
        if (line.startsWith("id;")) {
            return null;
        }
        String[] split = line.split(";");
        int id = Integer.parseInt(split[0]);
        String name = split[1];
        double grade = Double.parseDouble(split[2]);

        return new Student(id, name, grade);
    }



    @Override
    public String toString() {
        return "Student [id=" + id + ", name=" + name + ", grade=" + grade + "]";
    }


}






