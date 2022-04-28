import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;

public class CarSchema {
    String name = "";
    Integer doorsCount = 1;
    Boolean isDestroyed = false;
    String language = "";
    Integer price = 0;
    Integer year = 0;
    ArrayList<AirBag> airBags = new ArrayList<>();
    String color = "black";
    String sellDate = "2022.04.26";
    Integer vat = 0;
    String imageSrc = "1.jpg";

    public static CarSchema randomSchema() {
        Random random = new Random();
        CarSchema schema = new CarSchema();
        schema.name = Utils.randomFromList(Arrays.asList("Renault", "Opel", "Fiat"));
        schema.doorsCount = Utils.randomFromList(Arrays.asList(1, 2, 3, 4));
        schema.isDestroyed = Utils.randomFromList(Arrays.asList(true, false));
        schema.language = Utils.randomFromList(Arrays.asList("polski", "angielski"));
        schema.year = random.nextInt(1900, 2020);
        schema.price = random.nextInt(1000, 20000);
        schema.color = Utils.randomFromList(Arrays.asList("red", "blue", "green", "yellow", "black"));
        schema.airBags.add(new AirBag("Kierownica", Utils.randomFromList(Arrays.asList(true, false))));
        schema.airBags.add(new AirBag("Pasazer", Utils.randomFromList(Arrays.asList(true, false))));
        schema.airBags.add(new AirBag("Tylnia", Utils.randomFromList(Arrays.asList(true, false))));
        schema.airBags.add(new AirBag("Boczne", Utils.randomFromList(Arrays.asList(true, false))));
        schema.sellDate = Utils.between(LocalDate.of(schema.year, 1, 1), LocalDate.of(2100, 12, 31)).toString();
        schema.vat =  Utils.randomFromList(Arrays.asList(0,7,22));
        schema.imageSrc = Utils.randomFromList(Arrays.asList("1.jpg", "2.jpeg", "3.jpg"));

        return schema;
    }

}
