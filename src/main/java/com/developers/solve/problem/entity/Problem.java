package com.developers.solve.problem.entity;

import com.developers.solve.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.io.Serializable;
@AllArgsConstructor
@Builder
@NoArgsConstructor
@ToString
@Getter
@Where(clause = "deleted_at is NULL")
@SQLDelete(sql = "update problem set deleted_at = CURRENT_TIMESTAMP where problem_id = ?")
@Entity
public class Problem extends BaseTimeEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "problem_id", nullable = false)
    private Long problemId;
    @Column(name = "type", nullable = false)
    private String type;
    @Column(name = "writer", length = 100, nullable = false)
    private String writer;
    @Column(name = "title", length = 100, nullable = false)
    private String title;
    @Column(name = "content")
    private String content;
    @Column(name = "answer", nullable = false)
    private String answer;
    //Enum 타입으로 인해 Query dsl 작성 중 오류 Level 조건에 대한 처리는 전적으로 클라이언트에게 넘김.
    @Column(name = "level", nullable = false)
    private String level;
    @Column(name = "views")
    private Long views;
    @Column(name = "likes")
    private Long likes;
    @Column(name = "hashtag")
    private String hashtag;

}
