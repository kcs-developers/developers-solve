package com.developers.solve.problem.service;

import com.developers.solve.problem.dto.ProblemSaveRequestDto;
import com.developers.solve.problem.entity.Problem;

import java.util.List;

public interface ProblemService {
    Long save(ProblemSaveRequestDto request);
    List<Problem> searchProblem(String condition);
}
