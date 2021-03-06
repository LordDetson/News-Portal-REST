package by.babanin.newsportalrest.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data
@EqualsAndHashCode(of = "id")
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewsItem{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    @Size(min = 10, max = 50)
    private @NotEmpty String title;

    @Column(length = 5000, nullable = false)
    @Size(min = 255, max = 5000)
    private @NotEmpty String content;

    @Column(nullable = false)
    private @NotNull LocalDateTime publicationData;

    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private User author;

    @OneToMany(mappedBy = "publication", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<CommentItem> comments;
}
