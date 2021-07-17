package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itmo.wp.domain.Comment;
import ru.itmo.wp.domain.Post;
import ru.itmo.wp.security.Guest;
import ru.itmo.wp.service.PostService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class PostPage extends Page {
    private final PostService postService;

    public PostPage(PostService postService) {
        this.postService = postService;
    }

    @Guest
    @GetMapping("/post/{id}")
    public String postGet(@PathVariable String id,
                             HttpSession httpSession,
                             Model model) {

        Post post = getPost(id);

        if (post == null) {
            putMessage(httpSession, "Wrong post id=" + id);
            return "redirect:/";
        }

        model.addAttribute("currentPost", post);
        model.addAttribute("commentForm", new Comment());
        return "PostPage";
    }

    @PostMapping("/post/{id}")
    public String postPost(@PathVariable String id,
                           @Valid @ModelAttribute("commentForm") Comment comment,
                           BindingResult bindingResult,
                           HttpSession httpSession,
                           Model model) {

        Post post = getPost(id);

        if (post != null) {
            if (bindingResult.hasErrors()) {
                model.addAttribute("currentPost", post);
                return "PostPage";
            }
            comment.setUser(getUser(httpSession));
            postService.addComment(post, comment);
            putMessage(httpSession, "You added new comment");
        } else {
            putMessage(httpSession, "Wrong post id=" + id);
        }

        return "redirect:/post/" + id;
    }

    private Post getPost(String postId) {
        long numericId;
        try {
            numericId = Long.parseLong(postId);
        } catch (NumberFormatException e) {
            numericId = -1;
        }

        return postService.findById(numericId);
    }
}
