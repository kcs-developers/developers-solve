package com.developers.solve.problem.entity;

import com.developers.solve.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@NoArgsConstructor
@ToString
@Getter
@Where(clause = "deleted_at is NULL")
@SQLDelete(sql = "update problem set deleted_at = CURRENT_TIMESTAMP where problem_id = ?")
@Entity
@Table(name="problem")
public class Problem extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "problemId", nullable = false)
    private Long problemId;
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private Type type;
    @Column(name = "writer", length = 10, nullable = false)
    private String writer;
    @Column(name = "title", length = 100, nullable = false)
    private String title;
    @Column(name = "content", columnDefinition = "TEXT")
    private String content;
    @Column(name = "answer", nullable = false)
    private String answer;
    @Enumerated(EnumType.STRING)
    @Column(name = "level", nullable = false)
    private Level level;
    @Column(name = "views")
    private Long views;
    @Column(name = "likes")
    private Long likes;

    @Builder
    public Problem(Type type, String writer, String title, String content, String answer, Level level, Long views, Long likes) {
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
