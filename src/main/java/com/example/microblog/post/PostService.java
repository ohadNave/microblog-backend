package com.example.microblog.post;

import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;

@Service
@Builder
@Transactional
public class PostService {

    private final PostRepository repository;

    @Autowired
    public PostService(PostRepository repository){
        this.repository = repository;
    }

    public Post create(PostDto dto){
        Post post = new Post(dto.getContent());
        return repository.save(post);
    }

    public List<Post> getAll(){
        return repository.findAll();
    }

    public Post update(Long id, PostUpdateDto postUpdateDto){
        try{
            Post oldPost = getByID(id);
            oldPost.setContent(postUpdateDto.getContent());
            return oldPost;
        }
        catch (IllegalStateException e){
            return null;
        }
    }

    public Post getByID(Long id){
        return repository.findById(id).orElseThrow( () -> new IllegalStateException() );
    }

    public boolean delete(Long id){
        try{
            Post toDelete = getByID(id);
            repository.delete(toDelete);
            return true;
        }
        catch (IllegalStateException e){
            return false;
        }
    }

    public Post like(Long id){
        try{
            Post toLike = getByID(id);
            toLike.setLikes(toLike.getLikes() + 1);
            return toLike;
        }
        catch (IllegalStateException e){
            return null;
        }
    }

    public Collection<Post> getTrending(int n){
        return repository.findAllActiveUsers(n);
    }

}
