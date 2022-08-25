package music.musician.core.utilities.results;

import music.musician.entities.concretes.Band;

import java.util.List;

public class SuccessResult extends Result{
    public SuccessResult(String s){ //Islem sonucu basarili.
        super(true);
    }

    public SuccessResult(DataResult<List<Band>> message){
        super(true, String.valueOf(message));
    }
}
