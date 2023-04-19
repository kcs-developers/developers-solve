package com.developers.solve.problem.requestDTO;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
    @NotBlank(message = "제목을 작성해주세요")
    private String title;
    @NotBlank(message = "내용을 수정해주세요")
    private String content;
    @NotBlank(message = "정답을 수정해주세요")
    private String answer;
    private List<String> answerCandidate;
    @Nullable
    private String hashTag;
    @Nullable
    private String level;
}
