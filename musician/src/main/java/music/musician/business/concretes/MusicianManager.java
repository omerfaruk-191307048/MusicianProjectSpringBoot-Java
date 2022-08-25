package music.musician.business.concretes;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import music.musician.business.abstracts.MusicianService;
import music.musician.core.utilities.excelHelper.MusicianListExcelHelper;
import music.musician.core.utilities.pdfHelper.MusicianListPdfHelper;
import music.musician.core.utilities.results.*;
import music.musician.dataAccess.abstracts.BandRepository;
import music.musician.dataAccess.abstracts.InstrumentRepository;
import music.musician.dataAccess.abstracts.MusicianRepository;
import music.musician.dataAccess.abstracts.StyleRepository;
import music.musician.entities.concretes.Band;
import music.musician.entities.concretes.Instrument;
import music.musician.entities.concretes.Musician;
import music.musician.entities.concretes.Style;
import music.musician.entities.dtos.MusicianAddAndUpdateDto;
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
public class MusicianManager implements MusicianService {
    @Autowired
    private MusicianRepository musicianRepository;

    @Autowired
    private BandRepository bandRepository;

    @Autowired
    private InstrumentRepository instrumentRepository;

    @Autowired
    private StyleRepository styleRepository;
    @Override
    public DataResult<MusicianAddAndUpdateDto> addMusician(MusicianAddAndUpdateDto musicianAddUpdateDto) {
        Musician musician = new Musician();
        musician.setMusicianId(musicianAddUpdateDto.getMusicianId());
        musician.setMusicianName(musicianAddUpdateDto.getMusicianName());
        musician.setMusicianSurname(musicianAddUpdateDto.getMusicianSurname());
        musician.setMusicianGender(musicianAddUpdateDto.getMusicianGender());
        musician.setMusicianBirth(musicianAddUpdateDto.getMusicianBirth());
        Optional<Band> bandOpt = bandRepository.findById(musicianAddUpdateDto.getBandId());
        Band band;
        if (bandOpt.isEmpty()){
            System.out.println("Müzik grubu bulunamadı.");
            return new ErrorDataResult("Hata");
        }else {
            band = bandOpt.get();
        }
        musician.setBand(band);
        Optional<Instrument> instrumentOpt = instrumentRepository.findById(musicianAddUpdateDto.getInstrumentId());
        Instrument instrument;
        if (instrumentOpt.isEmpty()) {
            System.out.println("Enstrüman bulunamadı.");
            return new ErrorDataResult("Hata");
        }else {
            instrument = instrumentOpt.get();
        }
        musician.setInstrument(instrument);
        Optional<Style> styleOpt = styleRepository.findById(musicianAddUpdateDto.getStyleId());
        Style style;
        if (styleOpt.isEmpty()){
            System.out.println("Tarz bulunamadı.");
            return new ErrorDataResult("Hata");
        }else {
            style = styleOpt.get();
        }
        musician.setStyle(style);
        musician.setMusicianTitle(musicianAddUpdateDto.getMusicianTitle());
        musicianRepository.save(musician);
        return new SuccessDataResult("Müzisyen başarıyla eklendi.");
    }

    @Override
    public DataResult<List<Musician>> getAllMusician() {
        return new SuccessDataResult<Iterable<Musician>>(musicianRepository.findAll(), "Müzisyen bilgileri başarıyla listelendi.");
    }

    @Override
    public DataResult<Musician> deleteMusicianById(Integer musicianId) {
        musicianRepository.deleteById(musicianId);
        return new SuccessDataResult<Musician>("Müzisyen başarıyla silindi.");
    }

    @Override
    public DataResult<List<Musician>> getAllMusicianSorted() {
        Sort sort = Sort.by(Sort.Direction.ASC, "musicianName");
        return new SuccessDataResult<List<Musician>>(musicianRepository.findAll(sort), "Başarılı");
    }

    @Override
    public DataResult<List<Musician>> getAllMusicianPageable(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return new SuccessDataResult<List<Musician>>(musicianRepository.findAll(pageable).getContent());
    }

    @Override
    public DataResult<List<Musician>> getMusicianByMusicianNameContains(String musicianName) {
        List<Musician> musicianList = musicianRepository.getByMusicianNameContains(musicianName);
        if (Objects.nonNull(musicianList) && musicianList.size() > 0) {
            return new SuccessDataResult<List<Musician>>(musicianList, "Müzisyen başarıyla bulundu.");
        } else {
            return new ErrorDataResult<List<Musician>>(musicianList, "Müzisyen bulunamadı.");
        }
    }

    @Override
    public DataResult<Musician> getMusicianByMusicianId(Integer musicianId) {
        Optional<Musician> musicianList = musicianRepository.findById(musicianId);
        if (musicianList.isPresent()) {
            return new SuccessDataResult<Optional<Musician>>(musicianList, "Müzisyen başarıyla bulundu.");
        } else {
            return new ErrorDataResult<List<Musician>>("Müzisyen bulunamadı.");
        }
    }

    @Override
    public DataResult updateMusician(@NotNull MusicianAddAndUpdateDto musicianAddUpdateDto) {
        Optional<Musician> musicianUpdateDtoDBOpt=musicianRepository.findById(musicianAddUpdateDto.getMusicianId());
        Musician musicianUpdateDtoDB;
        if (musicianUpdateDtoDBOpt.isEmpty()){
            System.out.println("Müzisyen bulunamadı.");
            return new ErrorDataResult("Hata");
        }else {
            musicianUpdateDtoDB = musicianUpdateDtoDBOpt.get();
        }
        Optional<Band> bandOpt = bandRepository.findById(musicianAddUpdateDto.getBandId());
        Band band;
        if (bandOpt.isEmpty()){
            System.out.println("Müzik grubu bulunamadı.");
            return new ErrorDataResult("Hata");
        }else {
            band = bandOpt.get();
        }
        Optional<Instrument> instrumentOpt = instrumentRepository.findById(musicianAddUpdateDto.getInstrumentId());
        Instrument instrument;
        if (instrumentOpt.isEmpty()) {
            System.out.println("Enstrüman bulunamadı.");
            return new ErrorDataResult("Hata");
        }else {
            instrument = instrumentOpt.get();
        }
        Optional<Style> styleOpt = styleRepository.findById(musicianAddUpdateDto.getStyleId());
        Style style;
        if (styleOpt.isEmpty()){
            System.out.println("Tarz bulunamadı.");
            return new ErrorDataResult("Hata");
        }else {
            style = styleOpt.get();
        }
        if(Objects.nonNull(musicianAddUpdateDto.getMusicianName()) && !"".equalsIgnoreCase(musicianAddUpdateDto.getMusicianName())){
            musicianUpdateDtoDB.setMusicianName(musicianAddUpdateDto.getMusicianName());
        }
        if(Objects.nonNull(musicianAddUpdateDto.getMusicianSurname()) && !"".equalsIgnoreCase(musicianAddUpdateDto.getMusicianSurname())){
            musicianUpdateDtoDB.setMusicianSurname(musicianAddUpdateDto.getMusicianSurname());
        }
        if(Objects.nonNull(musicianAddUpdateDto.getMusicianGender()) && !"".equalsIgnoreCase(musicianAddUpdateDto.getMusicianGender())){
            musicianUpdateDtoDB.setMusicianGender(musicianAddUpdateDto.getMusicianGender());
        }
        if(Objects.nonNull(musicianAddUpdateDto.getMusicianBirth()) && !"".equalsIgnoreCase(musicianAddUpdateDto.getMusicianBirth())){
            musicianUpdateDtoDB.setMusicianBirth(musicianAddUpdateDto.getMusicianBirth());
        }
        if(Objects.nonNull(musicianAddUpdateDto.getBandId()) && !"".equalsIgnoreCase(String.valueOf(musicianAddUpdateDto.getBandId()))){
           musicianUpdateDtoDB.setBand(band);
        }
        if(Objects.nonNull(musicianAddUpdateDto.getInstrumentId()) && !"".equalsIgnoreCase(String.valueOf(musicianAddUpdateDto.getInstrumentId()))){
            musicianUpdateDtoDB.setInstrument(instrument);
        }
        if(Objects.nonNull(musicianAddUpdateDto.getStyleId()) && !"".equalsIgnoreCase(String.valueOf(musicianAddUpdateDto.getStyleId()))){
            musicianUpdateDtoDB.setStyle(style);
        }
        if(Objects.nonNull(musicianAddUpdateDto.getMusicianTitle()) && !"".equalsIgnoreCase(musicianAddUpdateDto.getMusicianTitle())){
            musicianUpdateDtoDB.setMusicianTitle(musicianAddUpdateDto.getMusicianTitle());
        }
        musicianRepository.save(musicianUpdateDtoDB);
        return new SuccessDataResult<Musician>("Müzisyen başarıyla güncellendi.");
    }

    @Override
    public Object exportToExcelMusician(HttpServletResponse response) {
        try{
            String fileName = "musician-list";

            response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
            response.setHeader("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ".xlsx");

            MusicianListExcelHelper musicianListExcelHelper = new MusicianListExcelHelper(musicianRepository.findAll());
            musicianListExcelHelper.export(response);
            return new SuccessDataResult(getAllMusician().toString());

        }
        catch (Exception exception){
            return new ErrorDataResult("Bilinmeyen bir hata oluştu");
        }
    }

    @Override
    public Result exportToPdfMusician(HttpServletResponse response) {
        try {
            String fileName = "musician-list";

            response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
            response.setHeader("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName+".pdf");

            MusicianListPdfHelper musicianListPdfHelper = new MusicianListPdfHelper(musicianRepository.findAll());
            musicianListPdfHelper.export(response);
            return new SuccessResult(getAllMusician().toString());
        }
        catch (Exception exception){
            return new ErrorResult("Bilinmeyen bir hata oluştu");
        }
    }

}
