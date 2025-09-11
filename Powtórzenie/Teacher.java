public class Teacher {
    private int id;
    private String name;
    private String subject;

    public Teacher(int id, String name, String subject) {
        this.id = id;
        this.name = name;
        this.subject = subject;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSubject() {
        return subject;
    }

    public static Teacher fromCsvLine(String line) {
        if (line.startsWith("id;")) {
            return null;
        }
        String[] fields = line.split(";");
        int id = Integer.parseInt(fields[0]);
        String name = fields[1];
        String subject = fields[2];

        return new Teacher(id, name, subject);
    }

    @Override
    public String toString() {
        return "Teacher{" + "id=" + id + ", name=" + name + ", subject=" + subject + '}';
    }
}











