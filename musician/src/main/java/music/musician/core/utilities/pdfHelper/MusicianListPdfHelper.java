package music.musician.core.utilities.pdfHelper;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PatternColor;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import music.musician.entities.concretes.Musician;
import org.springframework.core.io.ClassPathResource;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class MusicianListPdfHelper {

    static String[] HEADERs = { "Musician ID","Musician Name","Musician Surname","Musician Gender","Musician Birth","Musician Band","Musician Style","Musician Instrument Mark","Musician Instrument Name","Musician Title"};

    private List<Musician> musicianList;

    public MusicianListPdfHelper(List<Musician> musicianList){
        this.musicianList = musicianList;
    }

    public void export(HttpServletResponse response) throws DocumentException, IOException{
        Document document = new Document(PageSize.A3);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();

        Image image = Image.getInstance((new ClassPathResource("static/images/user-icon.jpg").getFile().getPath()));
        image.setAlignment(Image.ALIGN_CENTER);
        document.add(image);

        PdfPTable table = new PdfPTable(10);
        table.setWidthPercentage(105);
        table.setWidths(new float[]{ 2f, 2f, 2.5f, 2.2f, 2f, 2f, 3.2f, 3f, 3.1f, 3f });
        table.setSpacingBefore(15);

        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(PatternColor.WHITE);
        cell.setPadding(3);

        Font font = FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.BLUE);

        for (int col = 0; col < HEADERs.length; col++){
            cell.setPhrase(new Phrase(HEADERs[col], font));
            table.addCell(cell);
        }

        for (Musician musician : musicianList){
            table.addCell(String.valueOf(musician.getMusicianId()));
            table.addCell(musician.getMusicianName());
            table.addCell(musician.getMusicianSurname());
            table.addCell(musician.getMusicianGender());
            table.addCell(musician.getMusicianBirth());
            table.addCell(musician.getBand().getBandName());
            table.addCell(musician.getStyle().getStyleName());
            table.addCell(musician.getInstrument().getInstrumentMark());
            table.addCell(musician.getInstrument().getInstrumentName());
            table.addCell(musician.getMusicianTitle());
        }

        document.add(table);
        document.addTitle("MusicianPDF");

        document.close();
    }

}
