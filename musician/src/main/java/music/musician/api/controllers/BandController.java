package music.musician.api.controllers;

import music.musician.business.abstracts.BandService;
import music.musician.core.utilities.results.DataResult;
import music.musician.core.utilities.results.ErrorDataResult;
import music.musician.entities.concretes.Band;
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
@RequestMapping(name = "/api/bands")
public class BandController {

    @Autowired
    private BandService bandService;

    @PostMapping(value = "/addBand")
    public ResponseEntity<?> addBand(@RequestBody Band band){
        return ResponseEntity.ok(bandService.addBand(band));
    }

    @GetMapping("/bands")
    public DataResult<List<Band>> getAllBand(){
        return bandService.getAllBand();
    }

    @GetMapping("/bands/getAllBandPageable")
    public DataResult<List<Band>> getAllBandPageable(int pageNo, int pageSize){
        return bandService.getAllBandPageable(pageNo,pageSize);
    }

    @GetMapping("/bands/getBandByNameContains")
    public DataResult<List<Band>> getBandByNameContains(@RequestParam String bandName){
        return bandService.getBandByNameContains(bandName);
    }

    @GetMapping("/bands/getAllBandSorted")
    public DataResult<List<Band>> getAllBandSorted(){
        return bandService.getAllBandSorted();
    }

    @PutMapping("/bands/updateBand")
    public String updateBand(@RequestBody Band band){
        bandService.updateBand(band);
        return "Başarıyla güncellendi.";
    }

    @DeleteMapping("/bands/deleteBandById")
    public String deleteBandById(@RequestParam Integer bandId){
        bandService.deleteBandById(bandId);
        return "Başarıyla silindi.";
    }

    @GetMapping("/bands/getBandById")
    public DataResult<Band> getBandById(@RequestParam Integer bandId){
        return bandService.getBandByBandId(bandId);
    }

    @GetMapping("/bands/Excel")
    public ResponseEntity<?> exportToExcelBand(HttpServletResponse response) throws IOException{
        return ResponseEntity.ok(bandService.exportToExcelBand(response));
    }

    @GetMapping("/bands/PDF")
    public ResponseEntity<?> exportToPdfBand(HttpServletResponse response) throws IOException{
        return ResponseEntity.ok(bandService.exportToPdfBand(response));
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
