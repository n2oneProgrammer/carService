import com.google.gson.Gson;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import spark.Request;
import spark.Response;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.LinkOption;
import java.time.Instant;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class CarService {
    private final ArrayList<Car> cars = new ArrayList<>();
    private String allCarInvoice = "";
    private final HashMap<Integer, String> yearCarInvoice = new HashMap<>();
    private final HashMap<String, String> priceCarInvoice = new HashMap<>();

    public String createCar(Request request, Response response) {
        Gson gson = new Gson();
        CarSchema carSchema = gson.fromJson(request.body(), CarSchema.class);
        Car car = new Car(carSchema);
        this.cars.add(car);
        return gson.toJson(car, Car.class);
    }

    public String getCar(Request request, Response response) {
        Gson gson = new Gson();
        return gson.toJson(this.cars, ArrayList.class);
    }

    public String deleteCar(Request request, Response response) {
        String uuid = request.queryParams("uuid");
        this.cars.removeIf(car -> car.getUUID().equals(uuid));
        return "OK";
    }

    public String updateCar(Request request, Response response) {
        String uuid = request.queryParams("uuid");
        Gson gson = new Gson();
        CarSchema carSchema = gson.fromJson(request.body(), CarSchema.class);
        Optional<Car> findCar = this.cars.stream().filter(car -> car.getUUID().equals(uuid)).findFirst();
        findCar.ifPresent(car -> car.update(carSchema));
        return "OK";
    }

    public String generateInvoice(Request request, Response response) {

        String uuid = request.queryParams("uuid");
        Optional<Car> findCar = this.cars.stream().filter(car -> car.getUUID().equals(uuid)).findFirst();
        findCar.ifPresent(car -> {
            try {
                car.generateInvoice();
            } catch (DocumentException | IOException e) {
                throw new RuntimeException(e);
            }
        });

        return "";
    }

    public String generateRandom(Request request, Response response) {
        for (int i = 0; i < 10; i++) {
            this.cars.add(new Car(CarSchema.randomSchema()));
        }
        return "";
    }

    private String getPDFId() {
        Random random = new Random();
        return "FAKTURE " +
                LocalDate.now().getYear() + "/" +
                LocalDate.now().getMonthValue() + "/" +
                LocalDate.now().getDayOfMonth() + "/" +
                random.nextInt(10, 99) + "/" +
                random.nextInt(10, 99);
    }

    public String generateAllInvoice(Request request, Response response) throws DocumentException, IOException {
        this.allCarInvoice = "invoice_all_cars_" + Instant.now().getEpochSecond() + ".pdf";
        ResultInvoice.create(this.allCarInvoice,
                this.getPDFId(), "Faktuara za wszytkie auta", this.cars);
        return "";
    }

    public String getInvoices(Request request, Response response) {
        Gson gson = new Gson();

        return gson.toJson(new getInvoiveStruct(this.allCarInvoice, this.yearCarInvoice, this.priceCarInvoice));
    }

    public String generateYearInvoice(Request request, Response response) throws DocumentException, IOException {
        int year = Integer.parseInt(request.queryParams("year"));
        String name = "invoice_year_cars_" + Instant.now().getEpochSecond() + ".pdf";
        this.yearCarInvoice.put(year, name);
        List<Car> collect = this.cars.stream().filter(car -> car.getYear() == year).toList();
        ResultInvoice.create(name,
                this.getPDFId(), "Faktuara za rok " + year, new ArrayList<>(collect));
        return "";
    }

    public String generatePriceInvoice(Request request, Response response) throws DocumentException, IOException {
        int start = Integer.parseInt(request.queryParams("start"));
        int end = Integer.parseInt(request.queryParams("end"));
        String name = "invoice_price_cars_" + Instant.now().getEpochSecond() + ".pdf";
        this.priceCarInvoice.put(start + "x" + end, name);
        List<Car> collect = this.cars.stream().filter(car -> car.getPrice() >= start && car.getPrice() < end).toList();
        ResultInvoice.create(name,
                this.getPDFId(), "Faktuara za auta w cenach " + start+"-"+end+"zl", new ArrayList<>(collect));
        return "";
    }
}

