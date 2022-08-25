package music.musician.business.concretes;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import music.musician.business.abstracts.InstrumentService;
import music.musician.core.utilities.excelHelper.InstrumentListExcelHelper;
import music.musician.core.utilities.pdfHelper.BandListPdfHelper;
import music.musician.core.utilities.pdfHelper.InstrumentListPdfHelper;
import music.musician.core.utilities.results.*;
import music.musician.dataAccess.abstracts.InstrumentRepository;
import music.musician.entities.concretes.Instrument;
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
public class InstrumentManager implements InstrumentService {
    @Autowired
    private InstrumentRepository instrumentRepository;

    @Override
    public Result addInstrument(Instrument instrument) {
        instrumentRepository.save(instrument);
        return new SuccessResult("Müzik aleti başarıyla eklendi.");
    }

    @Override
    public DataResult<List<Instrument>> getAllInstrument() {
        return new SuccessDataResult<Iterable<Instrument>>(instrumentRepository.findAll(), "Müzik aletleri başarıyla listelendi.");
    }

    @Override
    public DataResult<Instrument> updateInstrument(Instrument instrument) {
        Instrument instrumentDB=instrumentRepository.findById(instrument.getInstrumentId()).get();

        if(Objects.nonNull(instrument.getInstrumentName()) && !"".equalsIgnoreCase(instrument.getInstrumentName())){
            instrumentDB.setInstrumentName(instrument.getInstrumentName());
        }
        if(Objects.nonNull(instrument.getInstrumentMark()) && !"".equalsIgnoreCase(instrument.getInstrumentMark())){
            instrumentDB.setInstrumentMark(instrument.getInstrumentMark());
        }

        if(Objects.nonNull(instrument.getInstrumentKind()) && !"".equalsIgnoreCase(instrument.getInstrumentKind())){
            instrumentDB.setInstrumentKind(instrument.getInstrumentKind());
        }
        instrumentRepository.save(instrumentDB);
        return new SuccessDataResult<Instrument>("Müzik aleti başarıyla güncellendi.");
    }

    @Override
    public DataResult<Instrument> deleteInstrumentById(Integer instrumentId) {
        instrumentRepository.deleteById(instrumentId);
        return new SuccessDataResult<Instrument>("Müzik aleti başarıyla silindi.");
    }

    @Override
    public DataResult<List<Instrument>> getAllInstrumentSorted() {
        Sort sort= Sort.by(Sort.Direction.ASC,"instrumentName");
        return new SuccessDataResult<List<Instrument>>(instrumentRepository.findAll(sort),"Başarılı");
    }

    @Override
    public DataResult<List<Instrument>> getAllInstrumentPageable(int pageNo, int pageSize) {
        Pageable pageable= PageRequest.of(pageNo-1,pageSize);
        return new SuccessDataResult<List<Instrument>>(instrumentRepository.findAll(pageable).getContent());
    }

    @Override
    public DataResult<List<Instrument>> getInstrumentByInstrumentNameContains(String instrumentName) {
        List<Instrument> instrumentList=instrumentRepository.getInstrumentByInstrumentNameContains(instrumentName);
        if(Objects.nonNull(instrumentList) && instrumentList.size()>0){
            return new SuccessDataResult<List<Instrument>>(instrumentList,"Müzik aleti başarıyla bulundu.");
        }
        else{
            return new ErrorDataResult<List<Instrument>>(instrumentList,"Müzik aleti bulunamadı.");
        }
    }

    @Override
    public DataResult<Instrument> getInstrumentByInstrumentId(Integer instrumentId) {
        Optional<Instrument> instrumentOpt = Optional.ofNullable(instrumentRepository.getInstrumentByInstrumentId(instrumentId));
        if (instrumentOpt.isEmpty()){
            return new  ErrorDataResult("Müzik aleti bulunamadı.");
        }
        return new SuccessDataResult<Optional<Instrument>>(instrumentOpt,"Müzik aleti başarıyla bulundu.");
    }

    @Override
    public Object exportToExcelInstrument(HttpServletResponse response) {
        try{
            String fileName = "instrument-list";

            response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
            response.setHeader("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ".xlsx");

            InstrumentListExcelHelper instrumentListExcelHelper = new InstrumentListExcelHelper(instrumentRepository.findAll());
            instrumentListExcelHelper.export(response);
            return new SuccessResult(getAllInstrument().toString());
        }
        catch (Exception exception){
            return new ErrorDataResult("Bilinmeyen bir hata oluştu");
        }
    }

    @Override
    public Result exportToPdfInstrument(HttpServletResponse response) {
        try {
            String fileName = "instrument-list";
            response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
            response.setHeader("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName+".pdf");
            InstrumentListPdfHelper instrumentListPdfHelper=new InstrumentListPdfHelper(instrumentRepository.findAll());
            instrumentListPdfHelper.export(response);
            return new SuccessResult(getAllInstrument().toString());
        }
        catch (Exception exception){
            return new ErrorResult("Bilinmeyen bir hata oluştu");
        }
    }
}
