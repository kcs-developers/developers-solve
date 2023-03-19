package com.developers.solve.problem.repository;

import com.developers.solve.problem.dto.ProblemSortResponseDTO;
import com.developers.solve.problem.entity.Problem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.List;

public interface ProblemRepository extends JpaRepository<Problem, Long> {
    @Query("SELECT p FROM Problem p JOIN ProblemHashtag t on p.problemId = t.hashtagId")
    List<Problem> findByTags(@Param("tags") List<String> tags);
    List<Problem> findByConditionOrderByDesc(String condition);

    List<Problem> findByProblemInSolution(String condition);

    List<Problem> findByProblemNotInSolution(String condition);

    List<Problem> findByProblemLikeLevel(String condition);

    List<Problem> findByProblmeLikeType(String condition);
}




