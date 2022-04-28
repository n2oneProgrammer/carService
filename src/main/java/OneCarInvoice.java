import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

public class OneCarInvoice {
    static HashMap<String, BaseColor> colorToPDFColor = new HashMap<>() {{
        put("red", BaseColor.RED);
        put("blue", BaseColor.BLUE);
        put("green", BaseColor.GREEN);
        put("yellow", BaseColor.YELLOW);
        put("black", BaseColor.BLACK);
    }};

    public static void create(Car car) throws IOException, DocumentException {
        Document document = new Document(); // dokument pdf
        String path = Objects.requireNonNull(OneCarInvoice.class.getResource("/public/invoice/")).getPath() + car.getUUID() + ".pdf";
        // lokalizacja zapisu
        PdfWriter.getInstance(document, new FileOutputStream(path));

        document.open();
        Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
        Paragraph paragraph1 = new Paragraph("FAKTURA dla " + car.getUUID(), font);
        document.add(paragraph1);
        Paragraph model = new Paragraph("Model: " + car.getName(), font);
        document.add(model);
        BaseColor color = colorToPDFColor.getOrDefault(car.getColor(), BaseColor.BLACK);
        Font font2 = FontFactory.getFont(FontFactory.COURIER, 16, color);
        Paragraph colorParagraph = new Paragraph("Color: " + car.getColor(), font2);
        document.add(colorParagraph);
        Paragraph year = new Paragraph("Rok: " + car.getYear(), font);
        document.add(year);
        car.getAirBags().forEach(airBag -> {
            Paragraph bag = new Paragraph("poduszka " + airBag.getName() + ": " + airBag.isExist(), font);
            try {
                document.add(bag);
            } catch (DocumentException e) {
                throw new RuntimeException(e);
            }
        });
        Image img = Image.getInstance(Objects.requireNonNull(OneCarInvoice.class.getResource("/public/images/" + car.getImageSrc())).getPath());
        float scaler = ((document.getPageSize().getWidth() - document.leftMargin()
                - document.rightMargin() - 0) / img.getWidth()) * 100;

        img.scalePercent(scaler);
        document.add(img);
        document.close();
    }
}
