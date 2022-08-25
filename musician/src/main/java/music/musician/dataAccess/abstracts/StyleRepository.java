package music.musician.dataAccess.abstracts;

import music.musician.entities.concretes.Style;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StyleRepository extends JpaRepository<Style,Integer> {
}
