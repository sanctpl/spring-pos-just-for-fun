package pl.sda.springfrontend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.sda.springfrontend.model.Comment;

import java.util.List;


public interface CommentRepository extends JpaRepository <Comment,Long> {
    List<Comment> findAllByPostId (Long id);
}
