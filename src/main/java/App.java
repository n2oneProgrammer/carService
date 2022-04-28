import static spark.Spark.*;

public class App {
    public static void main(String[] args) {
        CarService carService = new CarService();


        port(getHerokuPort());
        staticFiles.location("/public");
        get("/getCar", carService::getCar);
        post("/createCar", carService::createCar);
        delete("/deleteCar", carService::deleteCar);
        post("/updateCar", carService::updateCar);
        post("/generateInvoice", carService::generateInvoice);
        post("/generateInvoiceAll", carService::generateAllInvoice);
        post("/generateInvoiceYear", carService::generateYearInvoice);
        post("/generateInvoicePrice", carService::generatePriceInvoice);
        post("/generateRandom", carService::generateRandom);
        get("/getInvoices", carService::getInvoices);
    }

    static int getHerokuPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567;
    }
}
