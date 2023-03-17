package com.developers.solve.problem.service;

import com.developers.solve.problem.dto.ProblemSaveRequestDto;

public interface ProblemService {
    Long save(ProblemSaveRequestDto request);
    Long sort1(String sortcondition);
}
