package pl.sda.springfrontend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sda.springfrontend.model.Comment;
import pl.sda.springfrontend.model.Post;
import pl.sda.springfrontend.model.User;
import pl.sda.springfrontend.repository.CommentRepository;
import pl.sda.springfrontend.repository.PostRepository;
import pl.sda.springfrontend.repository.RoleRepository;
import pl.sda.springfrontend.repository.UserRepository;

import java.util.List;


@Service
public class PostService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PostRepository postRepository;

    private final CommentRepository commentRepository;

  /*  public String addComment(Long post_id, Long user_id, String message) {
        if (postRepository.findById(post_id).isPresent() && userRepository.findById(user_id).isPresent()) {
            Post post = postRepository.getOne(post_id);
            User user = userRepository.getOne(user_id);
            Comment comment = new Comment(message, user, post);
            commentRepository.save(comment);
            return "dodano komentarz";
        }
        return "błędne id posta lub usera";
    }
    public void deleteComment(Long comment_id, Long user_id){
        if(userRepository.findById(user_id).isPresent() && commentRepository.findById(comment_id).isPresent()) {
            User user = userRepository.getOne(user_id);
            Comment comment = commentRepository.getOne(comment_id);
            if (comment.getUser() == user) {
                commentRepository.delete(comment);
            }
        }
    }*/
    @Autowired
    public PostService(UserRepository userRepository, RoleRepository roleRepository, PostRepository postRepository, CommentRepository commentRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    public List<Post> getAll() {
        return postRepository.findAll();
    }

    public Post getPost(Long id) {
//        if (postRepository.findById(id).isPresent()){
        return postRepository.getOne(id);
//        }
//        return new Post();
    }

    public List<Comment> getAllComentsForPost(Long id) {

        return commentRepository.findAllByPostId(id);
    }


    public void addComentToPost(Long post_id, Long user_id, Comment comment) {
        User user = userRepository.getOne(user_id);
        comment.setUser(user);
        Post post = postRepository.getOne(post_id);
        comment.setPost(post);
        commentRepository.saveAndFlush(comment);
    }

    public Post addPost(Post post, Long user_id) {
        post.setUser(userRepository.getOne(user_id));
        return postRepository.saveAndFlush(post);
    }

    public void deleteById(Long postId, Long userId) {

        if (userRepository.existsById(userId)) {
            User user = userRepository.findFirstById(userId);
            if (user == postRepository.findFirstById(postId).getUser()
                    || user.getRoles().stream().anyMatch(role -> role.getRole().equals("ADMIN"))) {
                postRepository.deleteById(postId);
            }
        }
    }

    public void updatePost(Long post_id, Post new_post) {
        Post post = postRepository.getOne(post_id);
        post.setTitle(new_post.getTitle());
        post.setContent(new_post.getContent());
        post.setCategory(new_post.getCategory());
        postRepository.saveAndFlush(post);
    }

    public boolean isOwner(Long post_id, Long userId) {
        return postRepository.findFirstById(post_id).getUser().getId().equals(userId);
    }

    public void removeAllPosts() {
        postRepository.deleteAll();
    }
}
