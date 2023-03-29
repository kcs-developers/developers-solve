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

public interface ProblemRepository extends JpaRepository<Problem, Long>{
//    @Query("SELECT p FROM Problem p JOIN ProblemHashtag t on t.hashtagName where t.hashtagName in :tags")
//    List<Problem> findByTags(@Param("tags") List<String> tags);
//
//    List<Problem> findByConditionOrderByDesc(String condition);
//
//    List<Problem> findByProblemLikeLevel(String condition);
//
//    List<Problem> findByProblmeLikeType(String condition);
//
//    List<Problem> findByProblemInSolution(String condition);
//
//    List<Problem> findByProblemNotInSolution(String condition);
//    @Query("SELECT p FROM Solution s INNER JOIN Problem p on s.problemId = p")
//    Page<Problem> getProblemBySolved(String condition, Pageable pageable);
    @Query("SELECT p.views FROM Problem p where p.problemId = :problemId")
    Long getViewsCnt(Long problemId);
    @Query("SELECT p.likes FROM Problem p where p.problemId = :problemId")
    Long getLikesCnt(Long problemId);

    @Modifying
    @Transactional
    @Query("UPDATE Problem p SET p.answer = :answer, p.writer = :writer, p.title = :title WHERE p.problemId = :problemId")
    void updateViews(Long problemId, String answer, String writer, String title);
}
