package com.developers.solve.solution.dto;

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
public class SolutionRequest {
    @NotNull(message = "존재하지 않는 문제입니다.")
    private Long problemId;
    @NotBlank(message = "올바르지 않는 경로입니다.")
    private String solver;
    private Long id;
}
