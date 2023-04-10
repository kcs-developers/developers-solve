package com.developers.solve.problem.requestDTO;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ProblemUpdateRequestDto {
    @NotBlank
    private Long problemId;
    @Nullable
    private String type;
    @NotBlank
    private String writer;
    @Nullable
    private String title;
    @Nullable
    private String content;
    @Nullable
    private String answer;
    @Nullable
    private String hashTag;
    @Nullable
    private String level;
}
