package com.developers.solve.problem.dto.responseDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SortResponseDTO {
    private String msg;
    private String status;
    private List<ProblemSortResponseDTO> data;
}
