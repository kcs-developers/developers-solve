package com.developers.solve.solution.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
//문제 풀고 데이터가 입력되어있는지 확인하기 위함
public class SolutionResponse {
    private String code;
    private String msg;
    private SolutionId data;

}
