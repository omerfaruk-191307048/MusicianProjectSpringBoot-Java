package music.musician.entities.concretes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Data
@Table(name = "instruments")
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer","handler","musicians"})
public class Instrument {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "instrument_Id")
    private Integer instrumentId;

    @Column(name = "instrument_Name")
    private String instrumentName;

    @Column(name = "instrument_Mark")
    private String instrumentMark;

    @Column(name = "instrument_Kind")
    private String instrumentKind;

    @OneToMany(mappedBy = "instrument")
    private List<Musician> musicians;

}
