package com.developers.solve.problem.dto;


import com.developers.solve.problem.entity.Level;
import com.developers.solve.problem.entity.Problem;
import com.developers.solve.problem.entity.Type;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ProblemSortResponseDTO {

    private List<Problem> data;

}
