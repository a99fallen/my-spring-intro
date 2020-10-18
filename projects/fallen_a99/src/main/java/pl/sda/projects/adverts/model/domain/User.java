package pl.sda.projects.adverts.model.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "users")
@Getter @Setter @ToString(exclude = "password") @EqualsAndHashCode (of = "id")
@AllArgsConstructor @NoArgsConstructor @Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (nullable = false, unique = true)
    private String username;

    @Column (nullable = false)
    private String password;

    @Column (name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;
    private Boolean active;
}
