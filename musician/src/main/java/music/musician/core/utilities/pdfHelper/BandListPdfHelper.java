package music.musician.core.utilities.pdfHelper;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PatternColor;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import music.musician.entities.concretes.Band;
import org.springframework.core.io.ClassPathResource;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class BandListPdfHelper {

    static String[] HEADERs = { "Band ID","Band Name","Band Birth Year","Band Origin","Band Style" };

    private List<Band> bandList;

    public BandListPdfHelper(List<Band> bandList){
        this.bandList = bandList;
    }

    public void export(HttpServletResponse response) throws DocumentException, IOException{
        Document document = new Document(PageSize.A3);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();

        Image image = Image.getInstance((new ClassPathResource("static/images/rockbands.jpg").getFile().getPath()));
        image.setAlignment(Image.ALIGN_CENTER);
        document.add(image);

        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(105);
        table.setWidths(new float[]{ 1.5f, 3f, 2f, 2f, 3f });
        table.setSpacingBefore(15);

        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(PatternColor.WHITE);
        cell.setPadding(4);

        Font font = FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.MAGENTA);

        for (int col = 0; col < HEADERs.length; col++){
            cell.setPhrase(new Phrase(HEADERs[col], font));
            table.addCell(cell);
        }

        for (Band band : bandList){
            table.addCell(String.valueOf(band.getBandId()));
            table.addCell(band.getBandName());
            table.addCell(band.getBandBirthYear());
            table.addCell(band.getBandOrigin());
            table.addCell(band.getStyle().getStyleName());
        }
        document.add(table);
        document.addTitle("BandPDF");

        document.close();
    }
}
