import com.fasterxml.uuid.Generators;
import com.itextpdf.text.DocumentException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class Car {
    private final String uuid;
    private String name;
    private Integer doorsCount;
    private Boolean isDestroyed;
    private String language;
    private Integer price;
    private Integer year;
    private final ArrayList<AirBag> airBags;
    private final String color;
    private final String sellDate;
    private final Integer vat;
    private final String imageSrc;
    private boolean hasInvoice;

    public Car(CarSchema schema) {
        UUID uuid = Generators.randomBasedGenerator().generate();
        this.uuid = uuid.toString();
        this.name = schema.name;
        this.doorsCount = schema.doorsCount;
        this.isDestroyed = schema.isDestroyed;
        this.language = schema.language;
        this.price = schema.price;
        this.year = schema.year;
        this.airBags = schema.airBags;
        this.color = schema.color;
        this.sellDate = schema.sellDate;
        this.vat = schema.vat;
        this.imageSrc = schema.imageSrc;
        this.hasInvoice = false;
    }

    public String getUUID() {
        return this.uuid;
    }

    public void update(CarSchema car) {
        this.name = car.name;
        this.doorsCount = car.doorsCount;
        this.isDestroyed = car.isDestroyed;
        this.language = car.language;
        this.price = car.price;
        this.year = car.year;
    }

    public void generateInvoice() throws DocumentException, IOException {
        System.out.println("INVOICE");
        OneCarInvoice.create(this);
        this.hasInvoice = true;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public Integer getYear() {
        return year;
    }

    public ArrayList<AirBag> getAirBags() {
        return airBags;
    }

    public String getImageSrc() {
        return imageSrc;
    }

    public Integer getPrice() {
        return price;
    }

    public Integer getVat() {
        return vat;
    }
}
