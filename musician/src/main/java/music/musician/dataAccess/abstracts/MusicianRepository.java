package music.musician.dataAccess.abstracts;

import music.musician.core.utilities.results.DataResult;
import music.musician.entities.concretes.Musician;
import music.musician.entities.dtos.MusicianAddAndUpdateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MusicianRepository extends JpaRepository<Musician,Integer> {
    List<Musician> getByMusicianNameContains(String musicianName);
}
