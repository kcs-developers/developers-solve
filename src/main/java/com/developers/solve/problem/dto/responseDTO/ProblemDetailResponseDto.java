package com.developers.solve.problem.dto.responseDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ProblemDetailResponseDto {
    private String code;
    private String msg;
    private ProblemDetailDto data;

}

