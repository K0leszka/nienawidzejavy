public class Main {
    public static void main(String[] args) {
        MapParser parser = new MapParser();
        // Plik musi być w resources jako /map.svg
        parser.parse("/map.svg");
        parser.matchLabelsToTowns();
        parser.addCitiesToLands();

        for (Land land : parser.getLands()) {
            String s = land.toString();
            if (!s.isBlank()) System.out.println(s);
        }
    }
}
