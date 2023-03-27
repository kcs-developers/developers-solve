package com.developers.solve.solution.service;

import com.developers.solve.solution.dto.SolutionRequest;
import com.developers.solve.solution.dto.SolutionResponse;

public interface SolutionService {

    SolutionResponse save(SolutionRequest request);

}
