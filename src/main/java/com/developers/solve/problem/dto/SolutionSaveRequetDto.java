package com.developers.solve.problem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SolutionSaveRequetDto {
    private Long solutionId;
    private Long userId;
    private Long problemId;
}
