package music.musician.business.concretes;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import music.musician.business.abstracts.BandService;
import music.musician.core.utilities.excelHelper.BandListExcelHelper;
import music.musician.core.utilities.pdfHelper.BandListPdfHelper;
import music.musician.core.utilities.results.*;
import music.musician.dataAccess.abstracts.BandRepository;
import music.musician.dataAccess.abstracts.StyleRepository;
import music.musician.entities.concretes.Band;
import music.musician.entities.concretes.Style;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@NoArgsConstructor
@AllArgsConstructor
@Service
public class BandManager implements BandService {

    @Autowired
    private BandRepository bandRepository;

    @Autowired
    private StyleRepository styleRepository;
    @Override
    public Result addBand(Band band) {
        bandRepository.save(band);
        return new SuccessResult("Grup başarıyla eklendi.");
    }

    @Override
    public DataResult<List<Band>> getAllBand() {
        return new SuccessDataResult<Iterable<Band>>(bandRepository.findAll(), "Gruplar başarıyla listelendi.");
    }

    @Override
    public DataResult updateBand(@NotNull Band band) {
        Optional<Band> bandUpdateDBOpt = bandRepository.findById(band.getBandId());
        Band bandUpdateDB;
        if (bandUpdateDBOpt.isEmpty()){
            System.out.println("Grup bulunamadı.");
            return new ErrorDataResult("Hata");
        }else {
            bandUpdateDB = bandUpdateDBOpt.get();
        }
        Optional<Style> styleOpt = styleRepository.findById(band.getStyle().getStyleId());
        Style style;
        if (styleOpt.isEmpty()){
            System.out.println("Tarz bulunamadı.");
            return new ErrorDataResult("Hata");
        }else {
            style = styleOpt.get();
        }
        if (Objects.nonNull(band.getBandName()) && !"".equalsIgnoreCase(band.getBandName())) {
            bandUpdateDB.setBandName(band.getBandName());
        }
        if (Objects.nonNull(band.getStyle().getStyleId()) && !"".equalsIgnoreCase(band.getStyle().getStyleId().toString())) {
            bandUpdateDB.setStyle(band.getStyle());
        }
        if (Objects.nonNull(band.getBandBirthYear()) && !"".equalsIgnoreCase(band.getBandBirthYear())) {
            bandUpdateDB.setBandBirthYear(band.getBandBirthYear());
        }
        if (Objects.nonNull(band.getBandOrigin()) && !"".equalsIgnoreCase(band.getBandOrigin())) {
            bandUpdateDB.setBandOrigin(band.getBandOrigin());
        }
        bandRepository.save(bandUpdateDB);
        return new SuccessDataResult<Band>("Grup başarıyla güncellendi.");
    }

    @Override
    public DataResult<Band> deleteBandById(Integer bandId) {
        bandRepository.deleteById(bandId);
        return new SuccessDataResult<Band>("Grup başarıyla silindi.");
    }

    @Override
    public DataResult<List<Band>> getAllBandSorted() {
        Sort sort = Sort.by(Sort.Direction.ASC, "bandName");
        return new SuccessDataResult<List<Band>>(bandRepository.findAll(sort), "Başarılı");
    }

    @Override
    public DataResult<List<Band>> getAllBandPageable(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return new SuccessDataResult<List<Band>>(bandRepository.findAll(pageable).getContent());
    }

    @Override
    public DataResult<List<Band>> getBandByNameContains(String bandName) {
        List<Band> bandList = bandRepository.getBandByBandNameContains(bandName);
        if (Objects.nonNull(bandList) && bandList.size() > 0) {
            return new SuccessDataResult<List<Band>>(bandList, "Grup başarıyla bulundu.");
        } else {
            return new ErrorDataResult<List<Band>>(bandList, "Grup bulunamadı.");
        }
    }


    @Override
    public DataResult<Band> getBandByBandId(Integer bandId) {
        Optional<Band> bandList = bandRepository.findById(bandId);
        if (bandList.isPresent()) {
            return new SuccessDataResult<Optional<Band>>(bandList, "Grup başarıyla bulundu.");
        }
        return new ErrorDataResult<List<Band>>("Grup bulunamadı");
    }

    @Override
    public Object exportToExcelBand(HttpServletResponse response) {
        try {
            String fileName = "band-list";

            response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
            response.setHeader("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ".xlsx");

            BandListExcelHelper bandListExcelHelper = new BandListExcelHelper(bandRepository.findAll());
            bandListExcelHelper.export(response);
            return new SuccessResult(getAllBand()).toString();
        } catch (Exception exception) {
            return new ErrorDataResult("Bilinmeyen bir hata oluştu");
        }
    }

    @Override
    public Result exportToPdfBand(HttpServletResponse response) {
        try {
            String fileName = "band-list";
            response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
            response.setHeader("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ".pdf");
            BandListPdfHelper bandListPdfHelper = new BandListPdfHelper(bandRepository.findAll());
            bandListPdfHelper.export(response);
            return new SuccessResult(getAllBand().toString());
        } catch (Exception exception) {
            return new ErrorResult("Bilinmeyen bir hata oluştu");
        }
    }

}
