package com.example.Personal.Blog.controller;

import com.example.Personal.Blog.entity.Blog;
import com.example.Personal.Blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/blog")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @GetMapping("/allblog")
    public List<Blog> getAllBlogs(){
        return blogService.getAllBlogs();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Blog> getBlogById(@PathVariable Long id){
        return blogService.getBlogById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Blog createBlog(@RequestBody Blog blog){
        return blogService.saveBlog(blog);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Blog> updateBlog(@PathVariable Long id,@RequestBody Blog blog){
        return blogService.getBlogById(id).map(existingBlog -> {
            existingBlog.setTitle(blog.getTitle());
            existingBlog.setContent(blog.getContent());
            Blog updateBlog = blogService.saveBlog(existingBlog);
            return ResponseEntity.ok(updateBlog);
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBlog(@PathVariable Long id){
        blogService.deleteBog(id);
        return ResponseEntity.ok().build();
    }

}
