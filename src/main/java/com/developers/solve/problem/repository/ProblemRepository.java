package com.developers.solve.problem.repository;

import com.developers.solve.problem.dto.ProblemSortResponseDTO;
import com.developers.solve.problem.entity.Problem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.List;

public interface ProblemRepository extends JpaRepository<Problem, Long> {
    ArrayList<Problem> findAll();

    String finaAll(String sortcondition);

    @Query("select p from Problem p order by p.likes desc limit 10")
    List<ProblemSortResponseDTO> findProblemByLikesIs();
}
