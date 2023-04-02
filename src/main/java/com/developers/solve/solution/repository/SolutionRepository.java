package com.developers.solve.solution.repository;

import com.developers.solve.solution.entity.Solution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public interface SolutionRepository extends JpaRepository<Solution, Long> {
    @Override
    ArrayList<Solution> findAll();
    Boolean existsBySolverAndProblemProblemId(String member, Long problemId);


//    @Query("SELECT s.solver FROM Solution s WHERE s.solver = :p.writer ")
//    List<String> findSolver();

}
