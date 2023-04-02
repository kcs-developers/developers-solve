package com.developers.solve.problem.dto.responseDTO;

import com.developers.solve.problem.entity.Problem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ProblemUpdateResponseDto {
    private String code;
    private String msg;
    private Problem data;
}
