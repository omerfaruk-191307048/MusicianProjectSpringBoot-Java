package music.musician.business.abstracts;

import music.musician.core.utilities.results.DataResult;
import music.musician.core.utilities.results.Result;
import music.musician.entities.concretes.Musician;
import music.musician.entities.concretes.Style;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface StyleService {
    @Autowired
    Result addStyle(Style style);
    @Autowired
    DataResult<List<Style>> getAllStyle();
    @Autowired
    DataResult<Style> updateStyle(Style style);
    @Autowired
    DataResult<Style> deleteStyleById(Integer styleId);
    @Autowired
    Object exportToExcelStyle(HttpServletResponse response);
    @Autowired
    Result exportToPdfStyle(HttpServletResponse response);
}
