package music.musician.dataAccess.abstracts;

import music.musician.core.utilities.results.DataResult;
import music.musician.entities.concretes.Instrument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InstrumentRepository extends JpaRepository<Instrument,Integer> {
    List<Instrument> getInstrumentByInstrumentNameContains(String instrumentName);
    Instrument getInstrumentByInstrumentId(Integer instrumentId);

}
