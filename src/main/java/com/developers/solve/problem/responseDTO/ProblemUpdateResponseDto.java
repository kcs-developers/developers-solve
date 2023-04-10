package com.developers.solve.problem.responseDTO;

import com.developers.solve.problem.entity.Problem;
import jakarta.annotation.Nullable;
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
}
