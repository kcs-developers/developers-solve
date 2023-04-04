package com.developers.solve.solution.repository;

import com.developers.solve.solution.entity.Solution;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SolutionRepository extends JpaRepository<Solution, Long> {
    Boolean existsBySolverAndProblemIdProblemId(String member, Long problemId);
}
