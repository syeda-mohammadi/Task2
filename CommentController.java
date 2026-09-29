package com.example.blog_api.controller;

import com.example.blog_api.dto.CommentRequest;
import com.example.blog_api.entity.Comment;
import com.example.blog_api.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Comment createComment(
            @Valid @RequestBody CommentRequest request) {

        return commentService.createComment(request);
    }

    @GetMapping
    public List<Comment> getAllComments() {
        return commentService.getAllComments();
    }

    @GetMapping("/{id}")
    public Comment getCommentById(@PathVariable Long id) {

        return commentService.getCommentById(id);
    }

    @PutMapping("/{id}")
    public Comment updateComment(
            @PathVariable Long id,
            @Valid @RequestBody CommentRequest request) {

        return commentService.updateComment(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable Long id) {

        commentService.deleteComment(id);
    }
}
