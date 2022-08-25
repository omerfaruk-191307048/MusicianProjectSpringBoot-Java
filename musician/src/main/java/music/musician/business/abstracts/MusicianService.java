package music.musician.business.abstracts;

import music.musician.core.utilities.results.DataResult;
import music.musician.core.utilities.results.Result;
import music.musician.entities.concretes.Instrument;
import music.musician.entities.concretes.Musician;
import music.musician.entities.dtos.MusicianAddAndUpdateDto;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface MusicianService {
    @Autowired
    DataResult<MusicianAddAndUpdateDto> addMusician(MusicianAddAndUpdateDto musicianAddUpdateDto);
    @Autowired
    DataResult<List<Musician>> getAllMusician();
    //@Autowired
    //DataResult<Musician> updateMusician(Musician musician);
    @Autowired
    DataResult<Musician> deleteMusicianById(Integer musicianId);
    @Autowired
    public DataResult<List<Musician>> getAllMusicianSorted();
    @Autowired
    public DataResult<List<Musician>> getAllMusicianPageable(int pageNo, int pageSize);
    @Autowired
    DataResult<List<Musician>> getMusicianByMusicianNameContains(String musicianName);
    @Autowired
    DataResult<Musician> getMusicianByMusicianId(Integer musicianId);
    @Autowired
    DataResult updateMusician(MusicianAddAndUpdateDto musicianAddUpdateDto);
    @Autowired
    Object exportToExcelMusician(HttpServletResponse response);
    @Autowired
    Result exportToPdfMusician(HttpServletResponse response);
}
