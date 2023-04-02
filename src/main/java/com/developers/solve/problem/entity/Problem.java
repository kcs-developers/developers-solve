package com.developers.solve.problem.entity;

import com.developers.solve.common.entity.BaseTimeEntity;
import com.developers.solve.problem.dto.requestDTO.ProblemUpdateRequestDto;
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
    @Column(name = "writer", length = 10, nullable = false)
    private String writer;
    @Column(name = "title", length = 100, nullable = false)
    private String title;
    @Column(name = "content", columnDefinition = "TEXT")
    private String content;
    @Column(name = "answer", nullable = false)
    private String answer;
    @Column(name = "level", nullable = false)
    private String level;
    @Column(name = "hash_tag")
    private String hashTag;
    @Column(name = "views")
    private Long views;
    @Column(name = "likes")
    private Long likes;
    public void updateProblem(ProblemUpdateRequestDto updateRequestDto) {
        if (updateRequestDto.getTitle() != null) {
            this.title = updateRequestDto.getTitle();
        }
        if (updateRequestDto.getContent() != null) {
            this.content = updateRequestDto.getContent();
        }
        if (updateRequestDto.getAnswer() != null) {
            this.answer = updateRequestDto.getAnswer();
        }
        if (updateRequestDto.getLevel() != null) {
            this.level = updateRequestDto.getLevel();
        }
        if (updateRequestDto.getWriter() != null) {
            this.writer = updateRequestDto.getWriter();
        }
        if (updateRequestDto.getHashtag() != null){
            this.hashTag = updateRequestDto.getHashtag();
        }
        if (updateRequestDto.getType() != null) {
            this.type = updateRequestDto.getType();
        }
    }
}
