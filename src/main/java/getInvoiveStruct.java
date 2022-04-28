import java.util.HashMap;

public class getInvoiveStruct {
    private String allCarInvoice = "";
    private final HashMap<Integer, String> yearCarInvoice;
    private final HashMap<String, String> priceCarInvoice;

    public getInvoiveStruct(String allCarInvoice, HashMap<Integer, String> yearCarInvoice, HashMap<String, String> priceCarInvoice) {
        this.allCarInvoice = allCarInvoice;
        this.yearCarInvoice = yearCarInvoice;
        this.priceCarInvoice = priceCarInvoice;
    }
}
