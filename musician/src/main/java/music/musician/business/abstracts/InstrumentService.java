package music.musician.business.abstracts;

import music.musician.core.utilities.results.DataResult;
import music.musician.core.utilities.results.Result;
import music.musician.entities.concretes.Instrument;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;

public interface InstrumentService {
    @Autowired
    Result addInstrument(Instrument instrument);
    @Autowired
    DataResult<List<Instrument>> getAllInstrument();
    @Autowired
    DataResult<Instrument> updateInstrument(Instrument instrument);
    @Autowired
    DataResult<Instrument> deleteInstrumentById(Integer instrumentId);
    @Autowired
    public DataResult<List<Instrument>> getAllInstrumentSorted();
    @Autowired
    public DataResult<List<Instrument>> getAllInstrumentPageable(int pageNo, int pageSize);
    @Autowired
    DataResult<List<Instrument>> getInstrumentByInstrumentNameContains(String instrumentName);
    @Autowired
    DataResult<Instrument> getInstrumentByInstrumentId(Integer instrumentId);
    @Autowired
    Object exportToExcelInstrument(HttpServletResponse response);
    @Autowired
    Result exportToPdfInstrument(HttpServletResponse response);
}
