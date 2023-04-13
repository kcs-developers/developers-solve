package com.developers.solve.problem.responseDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ProblemDetailResponseDto {
    private String code;
    private String msg;
    private ProblemDetailDto data;

}
