package com.developers.solve.solution.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SolutionDTO {
    private Long id;

    private String solver;

    private String solved;


}
