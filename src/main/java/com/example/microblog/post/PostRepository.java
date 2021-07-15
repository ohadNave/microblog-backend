package com.example.microblog.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;


@Repository
public interface PostRepository extends JpaRepository<Post,Long> {


    @Query(value = "SELECT * FROM post p ORDER BY p.likes * pow(0.9,(DATE_PART('day',NOW()) - DATE_PART('day',p.date))) DESC LIMIT :n", nativeQuery = true)
    Collection<Post> findAllActiveUsers( @Param("n") int n);

}
