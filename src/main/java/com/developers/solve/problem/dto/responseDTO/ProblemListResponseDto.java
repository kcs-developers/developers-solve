package com.developers.solve.problem.dto.responseDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ProblemListResponseDto {
    private String code;
    private String msg;
    private List<ProblemDetailDto> data;
}
