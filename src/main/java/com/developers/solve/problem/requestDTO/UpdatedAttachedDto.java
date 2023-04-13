package com.developers.solve.problem.requestDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UpdatedAttachedDto {
    private List<String> pathname;
    private Long problemId;
}
