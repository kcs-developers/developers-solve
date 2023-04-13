package com.developers.solve.solution.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SolutionResponse {
    private String msg;
    private String status;
    private Long solutionId;
}
