package com.group3.camping_project.controller;
//package com.group3.camping_project.Controller;

import com.group3.camping_project.entities.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.group3.camping_project.service.IPostService;

import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {
    @Autowired
     IPostService postService;
    @PostMapping("/addPost")
    public Post addPost(@RequestBody Post post){
        return postService.addPost(post);    }
    @GetMapping ("/allPosts")
    public List<Post> retrieveAllPosts(){
        return postService.retrieveAllPosts();
    }
    @GetMapping("/postById/{id}")
    public Post getPostById(@PathVariable("id") Integer id){
        return postService.getPostById(id);
    }
    @PutMapping("updatePost")
    public Post updtaePost(@RequestBody Post post){
        return postService.updatePost(post);
    }
    @DeleteMapping("/deletePost/{id}")
    public void deletePost(@PathVariable("id")Integer id){
        postService.deletePost(id);
    }
    @PostMapping("/addPostWithUser/{userId}")
    public Post addPostAndAssignUserById(@RequestBody Post post, @PathVariable Integer userId){
        if (userId == null) {
            throw new IllegalArgumentException("The user ID value cannot be null!!!!");
        }
        return postService.addPostAndAssignUserById(post,userId);
        }
    @GetMapping("/postsByuser/{userId}")
    public List<Post> getAllPostsByUser(@PathVariable int userId) {
        return postService.getAllPostsByUser(userId);
    }

    @GetMapping("/postsearch")
    public List<Post> searchPosts(@RequestParam(required = false) String authorName, @RequestParam(required = false) String title) {
        return postService.searchPosts(authorName, title);
    }


}

