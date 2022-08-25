package music.musician.api.controllers;

import music.musician.business.abstracts.MusicianService;
import music.musician.core.utilities.results.DataResult;
import music.musician.core.utilities.results.ErrorDataResult;
import music.musician.entities.concretes.Musician;
import music.musician.entities.dtos.MusicianAddAndUpdateDto;
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
@RequestMapping(name = "/api/musicians")
public class MusicianController {

    @Autowired
    private MusicianService musicianService;

    @PostMapping(value = "/addMusician")
    public ResponseEntity<?> addMusician(@RequestBody MusicianAddAndUpdateDto musicianAddUpdateDto){
        return ResponseEntity.ok(musicianService.addMusician(musicianAddUpdateDto));
    }

    @GetMapping("/musicians")
    public DataResult<List<Musician>> getAllMusician(){
        return musicianService.getAllMusician();
    }

    @GetMapping("/musicians/getAllMusicianPageable")
    public DataResult<List<Musician>> getAllMusicianPageable(int pageNo, int pageSize){
        return musicianService.getAllMusicianPageable(pageNo,pageSize);
    }

    @GetMapping("/musicians/getMusicianByMusicianNameContains")
    public DataResult<List<Musician>> getMusicianByMusicianNameContains(String musicianName){
        return musicianService.getMusicianByMusicianNameContains(musicianName);
    }

    @GetMapping("/musicians/getAllMusicianSorted")
    public DataResult<List<Musician>> getAllMusicianSorted(){
        return musicianService.getAllMusicianSorted();
    }

    @PutMapping("/musicians/updateMusician")
    public DataResult updateMusician(@RequestBody MusicianAddAndUpdateDto musicianUpdateDto){
        return musicianService.updateMusician(musicianUpdateDto);
    }

    @DeleteMapping("/musicians/deleteMusicianById")
    public String deleteMusicianById(@RequestParam Integer musicianId){
        musicianService.deleteMusicianById(musicianId);
        return "Başarıyla silindi";
    }

    @GetMapping("/musicians/getMusicianByMusicianId")
    public DataResult<Musician> getMusicianByMusicianId(Integer musicianId){
        return musicianService.getMusicianByMusicianId(musicianId);
    }

    @GetMapping("/musicians/Excel")
    public ResponseEntity<?> exportToExcelMusician(HttpServletResponse response) throws IOException{
        return ResponseEntity.ok(musicianService.exportToExcelMusician(response));
    }

    @GetMapping("/musicians/PDF")
    public ResponseEntity<?> exportToPdfMusician(HttpServletResponse response) throws IOException{
        return ResponseEntity.ok(musicianService.exportToPdfMusician(response));
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
