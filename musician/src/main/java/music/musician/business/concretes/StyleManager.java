package music.musician.business.concretes;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import music.musician.business.abstracts.StyleService;
import music.musician.core.utilities.excelHelper.StyleListExcelHelper;
import music.musician.core.utilities.pdfHelper.StyleListPdfHelper;
import music.musician.core.utilities.results.*;
import music.musician.dataAccess.abstracts.StyleRepository;
import music.musician.entities.concretes.Style;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Service
public class StyleManager implements StyleService {

    @Autowired
    private StyleRepository styleRepository;

    @Override
    public Result addStyle(Style style) {
        styleRepository.save(style);
        return new SuccessResult("Stil başarıyla eklendi.");
    }

    @Override
    public DataResult<List<Style>> getAllStyle() {
        return new SuccessDataResult<Iterable<Style>>(styleRepository.findAll(),"Stil başarıyla listelendi.");
    }

    @Override
    public DataResult<Style> updateStyle(Style style) {
        Style styleDB=styleRepository.findById(style.getStyleId()).get();
        if(Objects.nonNull(style.getStyleName()) && !"".equalsIgnoreCase(style.getStyleName())){
            styleDB.setStyleName(style.getStyleName());
        }
        styleRepository.save(styleDB);
        return new SuccessDataResult<Style>("Stil başarıyla güncellendi.");
    }

    @Override
    public DataResult<Style> deleteStyleById(Integer styleId) {
        styleRepository.deleteById(styleId);
        return new SuccessDataResult<Style>("Stil başarıyla silindi.");
    }

    @Override
    public Object exportToExcelStyle(HttpServletResponse response) {
        try {
            String fileName = "style-list";

            response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
            response.setHeader("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ".xlsx");

            StyleListExcelHelper styleListExcelHelper = new StyleListExcelHelper(styleRepository.findAll());
            styleListExcelHelper.export(response);
            return new SuccessResult(getAllStyle().toString());

        }
        catch (Exception exception){
            return new ErrorResult("Bilinmeyen bir hata oluştu");
        }
    }

    @Override
    public Result exportToPdfStyle(HttpServletResponse response) {
        try {
            String fileName = "style-list";
            response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
            response.setHeader("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName+".pdf");
            StyleListPdfHelper styleListPdfHelper = new StyleListPdfHelper(styleRepository.findAll());
            styleListPdfHelper.export(response);
            return new SuccessResult(getAllStyle().toString());
        }
        catch (Exception exception){
            return new ErrorResult("Bilinmeyen bir hata oluştu");
        }
    }
}
