package com.developers.solve.problem.dto;

import com.developers.solve.problem.entity.Level;
import com.developers.solve.problem.entity.Type;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ProblemSaveRequestDto {
    private Long problemId;
    private Type type;
    private String writerId;
    private String title;
    private String content;
    private String answer;
    private Level level;
    private String tag;
}
