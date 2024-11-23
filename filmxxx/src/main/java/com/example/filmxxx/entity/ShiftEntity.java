package com.example.filmxxx.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Time;

@Getter
@Setter
@Entity
@Table(name = "shifts")
public class ShiftEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "shift_start", nullable = false)
    private Time shiftStart;

    @Column(name = "shift_end", nullable = false)
    private Time shiftEnd;

}
