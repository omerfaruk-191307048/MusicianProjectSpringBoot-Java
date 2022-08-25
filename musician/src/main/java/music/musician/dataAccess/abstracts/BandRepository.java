package music.musician.dataAccess.abstracts;

import music.musician.entities.concretes.Band;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BandRepository extends JpaRepository<Band,Integer> {
    List<Band> getBandByBandNameContains(String bandName);
    //Band getBandByBandId(Integer bandId);

}
