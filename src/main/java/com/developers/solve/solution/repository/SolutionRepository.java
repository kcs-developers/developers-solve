package com.developers.solve.solution.repository;

import com.developers.solve.solution.entity.Solution;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;


public interface SolutionRepository extends JpaRepository<Solution, Long> {
    @Override
    ArrayList<Solution> findAll();
    Boolean existsBySolverAndProblemProblemId(String member, Long problemId);


}
