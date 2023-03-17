package com.developers.solve.problem.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Getter
@ToString
@Entity
@Table(name = "hashtag")
public class Hashtag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hashTagId", nullable = false)
    private Long hasTagId;
    @Column(name = "hashTagName", nullable = false)
    private String hastTagName;

}
