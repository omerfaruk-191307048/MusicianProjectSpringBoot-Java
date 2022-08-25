package music.musician.business.abstracts;

import music.musician.core.utilities.results.DataResult;
import music.musician.core.utilities.results.Result;
import music.musician.entities.concretes.Band;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface BandService {
    @Autowired
    Result addBand(Band band);
    @Autowired
    DataResult<List<Band>> getAllBand();
    @Autowired
    DataResult updateBand(Band band);
    @Autowired
    DataResult<Band> deleteBandById(Integer bandId);
    @Autowired
    public DataResult<List<Band>> getAllBandSorted();
    @Autowired
    public DataResult<List<Band>> getAllBandPageable(int pageNo, int pageSize);
    @Autowired
    DataResult<List<Band>> getBandByNameContains(String bandName);
    @Autowired
    DataResult<Band> getBandByBandId(Integer bandId);
    @Autowired
    Object exportToExcelBand(HttpServletResponse response);
    @Autowired
    Result exportToPdfBand(HttpServletResponse response);
}
