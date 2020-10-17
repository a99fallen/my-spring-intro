package pl.sda.projects.adverts.model.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "adverts")
@Getter @Setter @ToString (exclude = "user") @EqualsAndHashCode(of = "id")
public class Advert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column (nullable = false)
    @ColumnDefault("now()") //to jest adnotacja z hibernate a nie z javax.persistance
    private LocalDateTime posted;

    @ManyToOne (optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "user_id", insertable = false, updatable = false)
    private Long UserId;

}
