package com.developers.solve.problem.requestDTO;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ProblemSaveRequestDto {
    @NotBlank(message = "유형을 설정해주세요.")
    private String type;
    @NotBlank(message = "로그인이 만료되었습니다.")
    private String writer;
    @NotBlank(message = "제목을 입력해주세요.")
    private String title;
    @NotBlank(message = "내용을 입력해주세요.")
    private String content;
    @NotBlank(message = "답안을 입력해주세요.")
    private String answer;
    @NotBlank(message = "난이도를 설정해주세요.")
    private String level;
    @Nullable
    private String tag;

}


