package music.musician.core.utilities.results;

public class ErrorResult extends Result{
    public ErrorResult(){ //Islem sonucu basarili.
        super(false);
    }

    public ErrorResult(String message){
        super(false,message);
    }
}
