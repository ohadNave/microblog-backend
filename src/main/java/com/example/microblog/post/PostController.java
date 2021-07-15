package com.example.microblog.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping(path = "post")
public class PostController {


    private final PostService service;

    @Autowired
    public PostController(PostService service){
        this.service = service;
    }


    @PostMapping()
    public Post create(@RequestBody PostDto dto){
        return service.create(dto);
    }

    @GetMapping("/all")
    public List<Post> getAll(){
        return service.getAll();
    }

    @PatchMapping("/{id}")
    public Post update(@PathVariable Long id,@RequestBody PostUpdateDto dto){
        Post post = service.update(id,dto);
        if(post == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found");
        }
        return post;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        boolean deleted = service.delete(id);
        if(!deleted){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found");
        }
    }

    @GetMapping("/{id}")
    public Post get(@PathVariable Long id){
        Post post = service.getByID(id);
        if(post != null){
            return post;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found");
    }


    @PostMapping("/{id}/like")
    public Post like(@PathVariable Long id){
        Post post = service.like(id);
        if(post != null){
            return post;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found");
    }

    @GetMapping("/trending/{n}")
    public Collection<Post> getTrending(@PathVariable int n){
        return service.getTrending(n);
    }


}
