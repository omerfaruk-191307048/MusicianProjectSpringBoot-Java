package music.musician.core.utilities.pdfHelper;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PatternColor;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import music.musician.entities.concretes.Style;
import org.springframework.core.io.ClassPathResource;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class StyleListPdfHelper {
    static String[] HEADERs = { "Style ID","Style Name" };

    private List<Style> styleList;

    public StyleListPdfHelper(List<Style> styleList){
        this.styleList = styleList;
    }

    public void export(HttpServletResponse response) throws DocumentException, IOException{
        Document document = new Document(PageSize.A3);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();

        Image image = Image.getInstance((new ClassPathResource("static/images/styles.jpg").getFile().getPath()));
        image.setAlignment(Image.ALIGN_CENTER);
        document.add(image);

        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(105);
        table.setWidths(new float[]{ 4f,4f });
        table.setSpacingBefore(15);

        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(PatternColor.WHITE);
        cell.setPadding(4);

        Font font = FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.PINK);

        for (int col = 0; col < HEADERs.length; col++){
            cell.setPhrase(new Phrase(HEADERs[col], font));
            table.addCell(cell);
        }

        for (Style style : styleList){
            table.addCell(String.valueOf(style.getStyleId()));
            table.addCell(style.getStyleName());
        }
        document.add(table);
        document.addTitle("StylePDF");

        document.close();
    }
}
