package com.developers.solve.problem.entity;

import com.developers.solve.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@ToString(exclude = "problemId")
@Getter
@Entity
@Table(name = "problemHashtag")
public class ProblemHashtag extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hashtagId", nullable = false)
    private Long hashtagId;
    @Column(name = "hashtagName", nullable = false)
    private String hashtagName;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "problemId", nullable = false)
    private Problem problemId;
}