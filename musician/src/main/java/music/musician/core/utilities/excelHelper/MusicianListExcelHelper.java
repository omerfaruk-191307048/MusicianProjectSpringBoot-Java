package music.musician.core.utilities.excelHelper;

import music.musician.entities.concretes.Musician;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class MusicianListExcelHelper {
    static String[] HEADERs = { "Musician ID","Musician Name","Musician Surname","Musician Gender","Musician Birth","Musician Band","Musician Style","Musician Instrument Mark","Musician Instrument Name","Musician Title"};

    static String SHEET = "Musicians";

    private Workbook workbook;

    private Sheet sheet;

    private List<Musician> musicians;

    public MusicianListExcelHelper(List<Musician> musicians){
        this.musicians=musicians;
        workbook=new XSSFWorkbook();
        sheet=workbook.createSheet(SHEET);
    }

    public void export(HttpServletResponse response) throws IOException{
        Row headerRow = sheet.createRow(0);

        CellStyle headerCellStyle = workbook.createCellStyle();
        XSSFFont headerFontStyle = (XSSFFont) workbook.createFont();
        headerFontStyle.setBold(true);
        headerFontStyle.setFontHeight(16);
        headerFontStyle.setColor(Font.COLOR_RED);
        headerCellStyle.setFont(headerFontStyle);

        for (int col = 0; col < HEADERs.length; col++){
            Cell cell = headerRow.createCell(col);
            cell.setCellValue(HEADERs[col]);
            cell.setCellStyle(headerCellStyle);
        }

        int rowIdx = 1;
        CellStyle rowCellStyle = workbook.createCellStyle();
        XSSFFont rowFontStyle = (XSSFFont) workbook.createFont();
        rowFontStyle.setFontHeight(13);
        rowFontStyle.setColor(IndexedColors.BLUE.getIndex());
        rowCellStyle.setFont(rowFontStyle);

        for (Musician musician : musicians){
            Row dataRow = sheet.createRow(rowIdx++);

            Cell cell = dataRow.createCell(0);
            cell.setCellValue(musician.getMusicianId());
            sheet.autoSizeColumn(0);
            cell.setCellStyle(rowCellStyle);

            cell = dataRow.createCell(1);
            cell.setCellValue(musician.getMusicianName());
            sheet.autoSizeColumn(1);
            cell.setCellStyle(rowCellStyle);

            cell = dataRow.createCell(2);
            cell.setCellValue(musician.getMusicianSurname());
            sheet.autoSizeColumn(2);
            cell.setCellStyle(rowCellStyle);

            cell = dataRow.createCell(3);
            cell.setCellValue(musician.getMusicianGender());
            sheet.autoSizeColumn(3);
            cell.setCellStyle(rowCellStyle);

            cell = dataRow.createCell(4);
            cell.setCellValue(musician.getMusicianBirth());
            sheet.autoSizeColumn(4);
            cell.setCellStyle(rowCellStyle);

            cell = dataRow.createCell(5);
            cell.setCellValue(musician.getBand().getBandName());
            sheet.autoSizeColumn(5);
            cell.setCellStyle(rowCellStyle);

            cell = dataRow.createCell(6);
            cell.setCellValue(musician.getStyle().getStyleName());
            sheet.autoSizeColumn(6);
            cell.setCellStyle(rowCellStyle);

            cell = dataRow.createCell(7);
            cell.setCellValue(musician.getInstrument().getInstrumentMark());
            sheet.autoSizeColumn(7);
            cell.setCellStyle(rowCellStyle);

            cell = dataRow.createCell(8);
            cell.setCellValue(musician.getInstrument().getInstrumentName());
            sheet.autoSizeColumn(8);
            cell.setCellStyle(rowCellStyle);

            cell = dataRow.createCell(9);
            cell.setCellValue(musician.getMusicianTitle());
            sheet.autoSizeColumn(9);
            cell.setCellStyle(rowCellStyle);
        }

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }
}
