package com.developers.solve.solution.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class SolutionRequest {
    private Long problemId;
    private String nickname;
    private Long id;
}
