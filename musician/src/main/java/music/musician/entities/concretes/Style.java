package music.musician.entities.concretes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@Entity
@Data
@Table(name = "styles")
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer","handler","bands","musicians"})
public class Style {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "style_Id")
    private Integer styleId;

    @Column(name = "style_Name")
    private String styleName;

    @OneToMany(mappedBy = "style")
    private List<Band> bands;

    @OneToMany(mappedBy = "style")
    private List<Musician> musicians;
}
