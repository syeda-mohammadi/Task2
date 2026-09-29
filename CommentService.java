package com.example.blog_api.service;

import com.example.blog_api.dto.CommentRequest;
import com.example.blog_api.entity.Comment;
import com.example.blog_api.entity.Post;
import com.example.blog_api.entity.User;
import com.example.blog_api.exception.ResourceNotFoundException;
import com.example.blog_api.repository.CommentRepository;
import com.example.blog_api.repository.PostRepository;
import com.example.blog_api.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public CommentService(CommentRepository commentRepository,
                          UserRepository userRepository,
                          PostRepository postRepository) {

        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    public Comment createComment(CommentRequest request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with id: "
                                        + request.getUserId()));

        Post post = postRepository.findById(request.getPostId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Post not found with id: "
                                        + request.getPostId()));

        Comment comment = new Comment();

        comment.setContent(request.getContent());
        comment.setUser(user);
        comment.setPost(post);

        return commentRepository.save(comment);
    }

    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    public Comment getCommentById(Long id) {

        return commentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Comment not found with id: " + id));
    }

    public Comment updateComment(Long id, CommentRequest request) {

        Comment comment = getCommentById(id);

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with id: "
                                        + request.getUserId()));

        Post post = postRepository.findById(request.getPostId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Post not found with id: "
                                        + request.getPostId()));

        comment.setContent(request.getContent());
        comment.setUser(user);
        comment.setPost(post);

        return commentRepository.save(comment);
    }

    public void deleteComment(Long id) {

        Comment comment = getCommentById(id);

        commentRepository.delete(comment);
    }
}
