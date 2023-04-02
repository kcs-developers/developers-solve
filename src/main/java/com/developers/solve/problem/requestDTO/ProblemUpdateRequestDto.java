package com.developers.solve.problem.requestDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProblemUpdateRequestDto {
    private Long problemId;
    private String answer;
    private String writer;
    private String title;
}
