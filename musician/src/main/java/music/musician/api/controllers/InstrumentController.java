package music.musician.api.controllers;

import music.musician.business.abstracts.InstrumentService;
import music.musician.core.utilities.results.DataResult;
import music.musician.core.utilities.results.ErrorDataResult;
import music.musician.entities.concretes.Instrument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(name = "/api/instruments")
public class InstrumentController {

    @Autowired
    private InstrumentService instrumentService;

    @PostMapping(value = "/addInstrument")
    public ResponseEntity<?> addInstrument(@RequestBody Instrument instrument){
        return ResponseEntity.ok(instrumentService.addInstrument(instrument));
    }

    @GetMapping("/instruments")
    public DataResult<List<Instrument>> getAllInstrument(){
        return instrumentService.getAllInstrument();
    }

    @GetMapping("/instruments/getAllInstrumentPageable")
    public DataResult<List<Instrument>> getAllInstrumentPageable(int pageNo, int pageSize){
        return instrumentService.getAllInstrumentPageable(pageNo,pageSize);
    }

    @GetMapping("/instruments/getInstrumentByInstrumentNameContains")
    public DataResult<List<Instrument>> getInstrumentByInstrumentNameContains(@RequestParam String instrumentName){
        return instrumentService.getInstrumentByInstrumentNameContains(instrumentName);
    }

    @GetMapping("/instruments/getAllInstrumentSorted")
    public DataResult<List<Instrument>> getAllInstrumentSorted(){
        return instrumentService.getAllInstrumentSorted();
    }

    @PutMapping("instruments/updateInstrument")
    public String updateInstrument(@RequestBody Instrument instrument){
        instrumentService.updateInstrument(instrument);
        return "Başarıyla güncellendi.";
    }

    @DeleteMapping("/deleteInstrumentById")
    public String deleteInstrumentById(@RequestParam Integer instrumentId){
        instrumentService.deleteInstrumentById(instrumentId);
        return "Başarıyla silindi.";
    }

    @GetMapping("/instruments/getInstrumentByInstrumentId")
    public DataResult<Instrument> getInstrumentByInstrumentId(@RequestParam Integer instrumentId){
        return instrumentService.getInstrumentByInstrumentId(instrumentId);
    }

    @GetMapping("/instruments/Excel")
    public ResponseEntity<?> exportToExcelInstrument(HttpServletResponse response) throws IOException{
        return ResponseEntity.ok(instrumentService.exportToExcelInstrument(response));
    }

    @GetMapping("/instrument/PDF")
    public ResponseEntity<?> exportToPdfInstrument(HttpServletResponse response) throws IOException{
        return ResponseEntity.ok(instrumentService.exportToPdfInstrument(response));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class) //spring validation'dan gelen butun dogrulama hatalarini listeler.
    @ResponseStatus(HttpStatus.BAD_REQUEST) //Bu metot default olarak bad request(500 hatasi) donsun.
    public ErrorDataResult<Object> handleValidationException(MethodArgumentNotValidException exceptions){ //Exception olusursa bu metodu cagir demek. O hatalari exceptions'a parametre olarak gec.
        Map<String, String> validationErrors = new HashMap<String, String>(); //Iki alani verecegimiz icin map yapisini kullandik. Anahtar(alan), hata.
        for(FieldError fieldError : exceptions.getBindingResult().getFieldErrors()){
            validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage()); //HashMap'i put olarak ekleriz.
        }
        ErrorDataResult<Object> errors = new ErrorDataResult<Object>(validationErrors, "Doğrulama hataları.");
        return errors;
    }
}
