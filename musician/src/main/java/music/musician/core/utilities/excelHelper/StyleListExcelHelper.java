package music.musician.core.utilities.excelHelper;

import music.musician.entities.concretes.Style;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class StyleListExcelHelper {
    static String[] HEADERs = { "Style ID","Style Name" };

    static String SHEET = "Styles";

    private Workbook workbook;

    private Sheet sheet;

    private List<Style> styles;

    public StyleListExcelHelper(List<Style> styles){
        this.styles=styles;
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

        for (Style style : styles){
            Row dataRow = sheet.createRow(rowIdx++);

            Cell cell = dataRow.createCell(0);
            cell.setCellValue(style.getStyleId());
            sheet.autoSizeColumn(0);
            cell.setCellStyle(rowCellStyle);

            cell = dataRow.createCell(1);
            cell.setCellValue(style.getStyleName());
            sheet.autoSizeColumn(1);
            cell.setCellStyle(rowCellStyle);
        }

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }
}
