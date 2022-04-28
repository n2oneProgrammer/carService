import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class ResultInvoice {

    public static void create(String name, String title, String subtitle, ArrayList<Car> car) throws IOException, DocumentException {
        Document document = new Document(); // dokument pdf
        String path = Objects.requireNonNull(OneCarInvoice.class.getResource("/public/invoice/")).getPath() + name;
        // lokalizacja zapisu
        PdfWriter.getInstance(document, new FileOutputStream(path));

        document.open();

        Font font = FontFactory.getFont(FontFactory.COURIER, 32, Font.BOLD, BaseColor.BLACK);
        Font font2 = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
        Font font3 = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.RED);
        Paragraph header = new Paragraph(title, font);
        document.add(header);
        Paragraph nabywca = new Paragraph("Nabywca: nabywca", font2);
        document.add(nabywca);

        Paragraph sprzedawca = new Paragraph("Nabywca: Firma sprzedawca aut", font2);
        document.add(sprzedawca);
        Paragraph sub = new Paragraph(subtitle, font3);
        document.add(sub);
        Paragraph padding = new Paragraph(" ", font3);
        document.add(padding);
        PdfPTable table = new PdfPTable(5);
        table.setPaddingTop(20);
        PdfPCell h1 = new PdfPCell(new Phrase("Lp.", font2));
        table.addCell(h1);
        PdfPCell h2 = new PdfPCell(new Phrase("rok", font2));
        table.addCell(h2);
        PdfPCell h3 = new PdfPCell(new Phrase("cena", font2));
        table.addCell(h3);
        PdfPCell h4 = new PdfPCell(new Phrase("vat", font2));
        table.addCell(h4);
        PdfPCell h5 = new PdfPCell(new Phrase("wartosc", font2));
        table.addCell(h5);
        var ref = new Object() {
            int lp = 1;
            int sum = 0;
        };
        car.forEach(car1 -> {
            PdfPCell c1 = new PdfPCell(new Phrase(String.valueOf(ref.lp++), font2));
            table.addCell(c1);
            PdfPCell c2 = new PdfPCell(new Phrase(String.valueOf(car1.getYear()), font2));
            table.addCell(c2);
            PdfPCell c3 = new PdfPCell(new Phrase(String.valueOf(car1.getPrice()), font2));
            table.addCell(c3);
            PdfPCell c4 = new PdfPCell(new Phrase(car1.getVat() + "%", font2));
            table.addCell(c4);
            ref.sum += car1.getPrice() * (1 + car1.getVat() / 100);
            PdfPCell c5 = new PdfPCell(new Phrase(String.valueOf(car1.getPrice() * (1 + car1.getVat() / 100)), font2));
            table.addCell(c5);
        });

        document.add(table);

        Paragraph end = new Paragraph("DO ZAPŁATY " + ref.sum + " zl", font);
        document.add(end);

        document.close();
    }
}
