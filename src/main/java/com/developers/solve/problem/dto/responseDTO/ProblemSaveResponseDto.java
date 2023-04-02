package com.developers.solve.problem.dto.responseDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
//안쓰는거 아닌가'
public class ProblemSaveResponseDto {
    private String code;
    private String msg;
    private ProblemSaveDetailResponseDto data;
}