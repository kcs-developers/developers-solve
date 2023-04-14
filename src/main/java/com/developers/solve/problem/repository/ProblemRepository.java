package com.developers.solve.problem.repository;

import com.developers.solve.problem.entity.Problem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ProblemRepository extends JpaRepository<Problem, Long> {
    @Query("SELECT p.views FROM Problem p where p.problemId = :problemId")
    Long getViewsCnt(Long problemId);

    @Query("SELECT p.likes FROM Problem p where p.problemId = :problemId")
    Long getLikesCnt(Long problemId);
    List<Problem> findByTitleContaining(String search);
    @Query("SELECT p from Problem p order by p.createdAt desc limit 500")
    List<Problem> CreatedTimeSort();

    @Query("SELECT p.writer from Problem p where p.problemId = :problemId")
    String UpdateLicense(Long problemId);
    @Query("SELECT p.answerCandidate from Problem p where p.problemId = :problemId")
    String ListAnswerCandidate(Long problemId);

}