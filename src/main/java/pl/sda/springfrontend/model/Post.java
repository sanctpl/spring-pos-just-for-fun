package pl.sda.springfrontend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    @Type(type="text")
    private String content;
    @Enumerated
    private CategoryEnum categoryEnum;
    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;


    public Post(String title, String content, User user, CategoryEnum categoryEnum) {
        this.title = title;
        this.content = content;
        this.user = user;
        this.categoryEnum = categoryEnum;
    }
}
