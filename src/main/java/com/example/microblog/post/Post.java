package com.example.microblog.post;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;

@Entity
@Table
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Post {

    @Id
    @SequenceGenerator(
            name = "post_sequence",
            sequenceName = "post_sequence",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "post_sequence")
    private Long id;
    private String text;
    private int likes;
    private LocalDate date;

    @Transient
    private double rank;

    public Post(String text) {
        this.text = text;
        this.likes = 0;
        this.date = LocalDate.now();
        this.rank=0;
    }


    public double getRank(){
        long daysBetween = DAYS.between(date, LocalDate.now());
        return likes * Math.pow(0.9,daysBetween);
    }

    public void addLike(){
        this.likes++;
        getRank();
    }



}
