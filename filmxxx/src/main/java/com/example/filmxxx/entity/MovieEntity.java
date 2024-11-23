package com.example.filmxxx.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "movies")
public class MovieEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "movie_name")
    private String movieName;

    @JsonBackReference
    @ManyToOne(cascade = {
            CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH
    })
    @JoinColumn(name = "movie_category_id", nullable = false)
    private CategoryEntity category;

    @Column(name = "movie_description")
    private String description;

    @Column(name = "movie_directors")
    private String directors;

    @Column(name = "movie_cast")
    private String cast;

    @Column(name = "running_time")
    private Time runningTime;

    @Column(name = "image")
    private String image;

    @Column(name = "status")
    private Integer status;

    @Column(name="trailer_url")
    private String trailerUrl;

    @JsonIgnore
    @OneToMany(mappedBy = "movie")
    private List<ScheduleEntity> schedules;

    @JsonIgnore
    @OneToMany(mappedBy = "movie")
    private List<DiscountEntity> discounts;
}
