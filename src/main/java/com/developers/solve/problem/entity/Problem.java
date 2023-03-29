package com.developers.solve.problem.entity;

import com.developers.solve.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.io.Serializable;

@NoArgsConstructor
@ToString
@Getter
@Where(clause = "deleted_at is NULL")
@SQLDelete(sql = "update problem set deleted_at = CURRENT_TIMESTAMP where problem_id = ?")
@Entity
public class Problem extends BaseTimeEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "problemId", nullable = false)
    private Long problemId;
    @Column(name = "type", nullable = false)
    private String type;
    @Column(name = "writer", length = 10, nullable = false)
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

    @Builder
    public Problem(Long problemId, String type, String writer, String title, String content, String answer, String level, Long views, Long likes) {
        this.problemId = problemId;
        this.type = type;
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.answer = answer;
        this.level = level;
        this.views = views;
        this.likes = likes;
    }
}
