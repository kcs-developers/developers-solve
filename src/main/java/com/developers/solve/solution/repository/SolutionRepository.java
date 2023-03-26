package com.developers.solve.solution.repository;

import com.developers.solve.problem.entity.Problem;
import com.developers.solve.solution.entity.Solution;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


import java.util.ArrayList;


public interface SolutionRepository extends JpaRepository<Solution, Long> {
    @Override
    ArrayList<Solution> findAll();


}
