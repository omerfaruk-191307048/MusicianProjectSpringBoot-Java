package music.musician.core.utilities.excelHelper;


import music.musician.entities.concretes.Band;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class BandListExcelHelper {
    static String[] HEADERs = { "Band ID","Band Name","Band Birth Year","Band Origin","Band Style" };

    static String SHEET = "Bands";

    private Workbook workbook;

    private Sheet sheet;

    private List<Band> bands;

    public BandListExcelHelper(List<Band> bands){
        this.bands=bands;
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

        for (Band band : bands){
            Row dataRow = sheet.createRow(rowIdx++);

            Cell cell = dataRow.createCell(0);
            cell.setCellValue(band.getBandId());
            sheet.autoSizeColumn(0);
            cell.setCellStyle(rowCellStyle);

            cell = dataRow.createCell(1);
            cell.setCellValue(band.getBandName());
            sheet.autoSizeColumn(1);
            cell.setCellStyle(rowCellStyle);

            cell = dataRow.createCell(2);
            cell.setCellValue(band.getBandBirthYear());
            sheet.autoSizeColumn(2);
            cell.setCellStyle(rowCellStyle);

            cell = dataRow.createCell(3);
            cell.setCellValue(band.getBandOrigin());
            sheet.autoSizeColumn(3);
            cell.setCellStyle(rowCellStyle);

            cell = dataRow.createCell(4);
            cell.setCellValue(band.getStyle().getStyleName());
            sheet.autoSizeColumn(4);
            cell.setCellStyle(rowCellStyle);

        }

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }
}
