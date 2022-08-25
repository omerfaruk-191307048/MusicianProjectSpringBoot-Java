package music.musician.core.utilities.pdfHelper;

import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.PatternColor;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import music.musician.entities.concretes.Instrument;
import org.apache.poi.ss.usermodel.*;
import org.springframework.core.io.ClassPathResource;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class InstrumentListPdfHelper {

    static String[] HEADERs = { "Instrument ID","Instrument Mark","Instrument Name","Instrument Kind" };

    private List<Instrument> instrumentList;

    public InstrumentListPdfHelper(List<Instrument> instrumentList){
        this.instrumentList = instrumentList;
    }

    public void export(HttpServletResponse response) throws DocumentException, IOException{
        Document document = new Document(PageSize.A3);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();

        Image image = Image.getInstance((new ClassPathResource("static/images/instrument.jpg").getFile().getPath()));
        image.setAlignment(Image.ALIGN_CENTER);
        document.add(image);

        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(105);
        table.setWidths(new float[]{ 1.5f, 3f, 2.5f, 2f });
        table.setSpacingBefore(15);

        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(PatternColor.WHITE);
        cell.setPadding(4);

        Font font = FontFactory.getFont(FontFactory.HELVETICA,12,BaseColor.GREEN);

        for (int col = 0; col < HEADERs.length; col++){
            cell.setPhrase(new Phrase(HEADERs[col],font));
            table.addCell(cell);
        }

        for (Instrument instrument : instrumentList){
            table.addCell(String.valueOf(instrument.getInstrumentId()));
            table.addCell(instrument.getInstrumentMark());
            table.addCell(instrument.getInstrumentName());
            table.addCell(instrument.getInstrumentKind());
        }
        document.add(table);
        document.addTitle("InstrumentPDF");

        document.close();
    }
}
