package music.musician.entities.concretes;

import lombok.*;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Data
@Table(name = "musicians")
@AllArgsConstructor
@NoArgsConstructor
public class Musician {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @Column(name = "musician_Id")
    private Integer musicianId;

    @Column(name = "musician_Name")
    private String musicianName;

    @Column(name = "musician_Surname")
    private String musicianSurname;

    @Column(name = "musician_Gender")
    private String musicianGender;

    @Column(name = "musician_Birth")
    private String musicianBirth;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "band_Id")
    private Band band;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "style_Id")
    private Style style;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "instrument_Id")
    private Instrument instrument;

    @Column(name = "musician_Title")
    private String musicianTitle;
}
