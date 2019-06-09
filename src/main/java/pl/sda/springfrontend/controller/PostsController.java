package pl.sda.springfrontend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.sda.springfrontend.model.CategoryEnum;
import pl.sda.springfrontend.model.Comment;
import pl.sda.springfrontend.model.Post;
import pl.sda.springfrontend.service.CommentService;
import pl.sda.springfrontend.service.PostService;

import javax.jws.WebParam;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller

public class PostsController {
    PostService postService;
    CommentService commentService;

    @Autowired
    public PostsController(PostService postService, CommentService commentService) {
        this.postService = postService;
        this.commentService = commentService;
    }

    @GetMapping("/")
    public String  home(Model model){
        List<Post> postList = postService.getPosts();
        System.out.println(postList);
        model.addAttribute("postList",postList);
        return "posts";
    }



    @GetMapping("/post/{id}")
    public String showPost(Model model, @PathVariable Long id){
        Post currentPost = postService.getPost(id);

        if (currentPost != null){
            model.addAttribute("post",currentPost);
            List<Comment> postComment = commentService.getPostComment(id);
            model.addAttribute("com", postComment);
            return "post";

        }

        return "/posts";
    }
    @GetMapping("/addComment/{post_id}")
    public String addComment(@PathVariable Long post_id, Model model){
        model.addAttribute("post",postService.getPost(post_id));
        model.addAttribute("comment", new Comment());
        return "addcomment";
    }

    @PostMapping(value = "/addComment/{post_id}/{user_id}")
    public String addComment(@ModelAttribute Comment comment, @PathVariable Long post_id, @PathVariable Long user_id){
        commentService.addComment(post_id,user_id,comment);
        return "redirect:/post/"+post_id;
    }
    @GetMapping("/addpost")
    public String addPost(Model model){
        model.addAttribute("post",new Post());
        List<CategoryEnum> categories =
                new ArrayList<>(Arrays.asList(CategoryEnum.values()));
        System.out.println(categories);

        model.addAttribute("categories", categories);
        return "addpost";
    }

    @PostMapping("/addpost")
    public String addPost(@ModelAttribute Post post){
        postService.addPost(post);

        return "redirect:/";
    }

    @DeleteMapping("/delete/{post_id}")
    public String delete(@PathVariable Long post_id){
        postService.removePost(5L,post_id);
        return "redirect:/";
    }
    @GetMapping("/update/{post_id}")
    public String editPost(@PathVariable Long post_id, Model model) {
        model.addAttribute(postService.getPost(post_id));
        return "edit";
    }

    @PutMapping("/update/{post_id}")
    public String editPost(@ModelAttribute Post post, @PathVariable Long post_id){
        postService.updatePost(post_id, post);
        return "redirect:/post/"+post_id;
    }

}
