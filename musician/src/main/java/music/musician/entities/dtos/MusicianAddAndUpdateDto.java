package music.musician.entities.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MusicianAddAndUpdateDto {
    private Integer musicianId;
    private String musicianName;
    private String musicianSurname;
    private String musicianGender;
    private String musicianBirth;
    private Integer bandId;
    private Integer instrumentId;
    private Integer styleId;
    private String musicianTitle;
}
