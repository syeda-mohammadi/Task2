package com.example.blog_api.service;

import com.example.blog_api.dto.PostRequest;
import com.example.blog_api.entity.Post;
import com.example.blog_api.entity.User;
import com.example.blog_api.exception.ResourceNotFoundException;
import com.example.blog_api.repository.PostRepository;
import com.example.blog_api.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostService(PostRepository postRepository,
                       UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public Post createPost(PostRequest request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with id: "
                                        + request.getUserId()));

        Post post = new Post();

        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setUser(user);

        return postRepository.save(post);
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Post getPostById(Long id) {

        return postRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Post not found with id: " + id));
    }

    public Post updatePost(Long id, PostRequest request) {

        Post post = getPostById(id);

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with id: "
                                        + request.getUserId()));

        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setUser(user);

        return postRepository.save(post);
    }

    public void deletePost(Long id) {

        Post post = getPostById(id);

        postRepository.delete(post);
    }
}
