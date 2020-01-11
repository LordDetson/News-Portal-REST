package by.babanin.newsportalrest.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Data
@EqualsAndHashCode(of = "id")
@ToString(exclude = {"author"})
@NoArgsConstructor
public class NewsItem{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    @Size(min = 1, max = 50)
    private @NotEmpty String title;

    @Column(length = 5000, nullable = false)
    @Size(min = 1, max = 5000)
    private @NotEmpty String content;

    @Column(nullable = false)
    private @NotNull LocalDateTime publicationData;

    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private User author;
}
