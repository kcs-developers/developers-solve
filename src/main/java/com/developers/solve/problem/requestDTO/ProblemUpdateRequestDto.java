package com.developers.solve.problem.requestDTO;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ProblemUpdateRequestDto {
    @NotNull(message = "수정 권한이 없습니다.")
    private Long problemId;
    @Nullable
    private String type;
    @NotBlank(message = "수정 권한이 없습니다.")
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
