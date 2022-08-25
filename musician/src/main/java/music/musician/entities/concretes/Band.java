package music.musician.entities.concretes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@Entity
@Data
@Table(name = "bands")
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer","handler","musicians"})
public class Band {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "band_Id")
    private Integer bandId;

    @Column(name = "band_Name")
    private String bandName;

    //cascade={CascadeType.PERSIST,CascadeType.REMOVE} bunu aynı foreign keylere sahip tablolarda çakışma yaşanmaması için ekledik
    @ManyToOne(cascade=CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "style_Id")
    private Style style;

    @Column(name = "band_Birth_Year")
    private String bandBirthYear;

    @Column(name = "band_Origin")
    private String bandOrigin;

    @OneToMany(mappedBy = "band")
    private List<Musician> musicians;

}
