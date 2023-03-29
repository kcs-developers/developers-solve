package com.developers.solve.problem.repository;

import com.developers.solve.problem.dto.ProblemSortResponseDTO;
import com.developers.solve.problem.entity.Problem;
import com.developers.solve.problem.entity.QProblem;
import com.developers.solve.problem.entity.QProblemHashtag;
import com.developers.solve.solution.entity.QSolution;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.util.StringUtils;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import util.OrderByNull;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProblemQueryDsl {
    private final JPAQueryFactory queryFactory;

    QProblem problem = QProblem.problem;
    QSolution solution = QSolution.solution;
    QProblemHashtag problemHashtag = QProblemHashtag.problemHashtag;

    private OrderSpecifier<Long> getProblemSortedByViews(String order){
        if (!StringUtils.isNullOrEmpty(order)) {
        return problem.views.desc();
    }
        else
            return OrderByNull.getDefault();
    }
    private OrderSpecifier<LocalDateTime> getProblemSortedByLocalTime(String order){
        if (!StringUtils.isNullOrEmpty(order)){
            return problem.createdAt.desc();}
        else
            return OrderByNull.getDefault();
    }
    private OrderSpecifier<Long> getProblemSortedByLikes(String order){
        if (!StringUtils.isNullOrEmpty(order))
        {return problem.likes.desc();}
        else
            return OrderByNull.getDefault();
    }
    //Null 값 처리에 대한 것 정리, Nooffset에 대한 정리, Cache 시스템 구현 과 그것을 안쓰고 Indexed DB에 위임한 이유에 대해 정리!!
    public List<Problem> getProblemSortedByLevel(String order,String types,String level,Long problemId) {
        List<Problem> content = queryFactory.selectFrom(problem).where(ProblemId(problemId),containLevel(level),containType(types)).orderBy(getProblemSortedByLikes(order),getProblemSortedByViews(order),getProblemSortedByLocalTime(order)).limit(100).fetch();
        return content;
    }
    public List<Problem> getProblemSortedBySolved(String order,String types,String level,Long problemId){
        List<Problem> content1 = queryFactory.selectFrom(problem).join(solution).on(problem.problemId.eq(solution.problemId.problemId)).where(ProblemId(problemId),containLevel(level),containType(types)).orderBy(getProblemSortedByLikes(order),getProblemSortedByViews(order),getProblemSortedByLocalTime(order)).limit(100).fetch();
        return content1;
    }
    public void addViewCntFromRedis(Long problemId, Long views){
        queryFactory.update(problem).set(problem.views, views).where(problem.problemId.eq(problemId)).execute();
    }
    public void addLikesCntFromRedis(Long problemId, Long likes){
        queryFactory.update(problem).set(problem.likes, likes).where(problem.problemId.eq(problemId)).execute();
    }
    public List<Problem> getProblemSortedByHashTag(String order,String types,String level,Long problemId,String hashtag){
        List<Problem> content = queryFactory.selectFrom(problem).where(problem.problemId.in(JPAExpressions.select(problemHashtag.problemId.problemId).from(problemHashtag).where(containHashTag(hashtag))),ProblemId(problemId),containLevel(level),containType(types)).orderBy(getProblemSortedByLikes(order),getProblemSortedByViews(order),getProblemSortedByLocalTime(order)).limit(100).fetch();
        return content;
    }
    public List<Problem> getProblemSortedByHashTagAndSolved(String order,String types,String level,Long problemId,String hashtag){
        List<Problem> content = queryFactory.selectFrom(problem).join(solution).on(problem.problemId.eq(solution.problemId.problemId)).where(problem.problemId.in(JPAExpressions.select(problemHashtag.problemId.problemId).from(problemHashtag).where(containHashTag(hashtag))),ProblemId(problemId),containLevel(level),containType(types)).orderBy(getProblemSortedByLikes(order),getProblemSortedByViews(order),getProblemSortedByLocalTime(order)).limit(100).fetch();
        return content;
    }
    private BooleanExpression containHashTag(String hashtag){
        if (!StringUtils.isNullOrEmpty(hashtag) && (hashtag.contains("CS")||hashtag.contains("FrontEnd")||hashtag.contains("BackEnd")||hashtag.contains("Cloud"))||hashtag.contains("Algorithm")){
            return problemHashtag.hashtagName.contains(hashtag);
        }
        return null;
    }
    private BooleanExpression ProblemId(Long problemId){
        if (problemId == null){
            return null;
        }
        return problem.problemId.gt(problemId);
    }
    private BooleanExpression containLevel(String level){
        if (!StringUtils.isNullOrEmpty(level) && (level.contains("gold")||level.contains("silver")||level.contains("bronze"))){
            return problem.level.contains(level);
        }
        return null;
    }
    private BooleanExpression containType(String types){
        if (!StringUtils.isNullOrEmpty(types) && (types.contains("choice") || types.contains("answer"))){
            return problem.type.contains(types);
        }
        return null;
    }
    }

