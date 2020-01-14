package by.babanin.newsportalrest.model;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Data
@EqualsAndHashCode(of = "id")
@ToString(exclude = {"publication"})
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private User author;

    @ManyToOne
    @JoinColumn(name = "publication_id", referencedColumnName = "id")
    private NewsItem publication;

    @Column(nullable = false)
    private @NotNull LocalDateTime commentDate;

    @Column(length = 2000, nullable = false)
    @Size(min = 5, max = 2000)
    private @NotEmpty String message;
}
