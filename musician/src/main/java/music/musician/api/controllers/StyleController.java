package music.musician.api.controllers;

import music.musician.business.abstracts.StyleService;
import music.musician.core.utilities.results.DataResult;
import music.musician.core.utilities.results.ErrorDataResult;
import music.musician.entities.concretes.Style;
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
@RequestMapping(name = "/api/styles")
public class StyleController {

    @Autowired
    private StyleService styleService;

    @PostMapping(value = "/addStyle")
    public ResponseEntity<?> addStyle(@RequestBody Style style){
        return ResponseEntity.ok(styleService.addStyle(style));
    }

    @GetMapping("/styles")
    public DataResult<List<Style>> getAllStyle(){
        return styleService.getAllStyle();
    }

    @PutMapping("/styles/updateStyle")
    public String updateStyle(@RequestBody Style style){
        styleService.updateStyle(style);
        return "Başarıyla güncellendi.";
    }

    @DeleteMapping("/styles/deleteStyleById")
    public String deleteStyleById(@RequestParam Integer styleId){
        styleService.deleteStyleById(styleId);
        return "Başarıyla silindi.";
    }

    @GetMapping("/styles/Excel")
    public ResponseEntity<?> exportToExcelStyle(HttpServletResponse response) throws IOException{
        return ResponseEntity.ok(styleService.exportToExcelStyle(response));
    }

    @GetMapping("/styles/PDF")
    public ResponseEntity<?> exportToPdfStyle(HttpServletResponse response) throws IOException{
        return ResponseEntity.ok(styleService.exportToPdfStyle(response));
    }

}
