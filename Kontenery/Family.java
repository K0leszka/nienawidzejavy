import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Family {
    private Map<String, List<Person>> family;

    public Family() {
        family = new HashMap<>();
    }

    public Map<String, List<Person>> getFamily() {
        return family;
    }

    public void addPerson(Person... persons) {
        for (Person person : persons) {
            String key = person.getName() + person.getSurname();
            family.computeIfAbsent(key, k -> new ArrayList<>()).add(person);
        }
    }
    
    public List<Person> getPerson(String key){
        return family.getOrDefault(key, new ArrayList<>());
    }
}
