package com.developers.solve.problem.repository;

import com.developers.solve.problem.entity.Problem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;
import java.util.List;

public interface ProblemRepository extends JpaRepository<Problem, Long> {
    @Query("SELECT p.views FROM Problem p where p.problemId = :problemId")
    Long getViewsCnt(Long problemId);

    @Query("SELECT p.likes FROM Problem p where p.problemId = :problemId")
    Long getLikesCnt(Long problemId);

    @Override
    ArrayList<Problem> findAll();
    List<Problem> findByTitleContaining(String param);
}
