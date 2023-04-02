package com.developers.solve.problem.repository;

import com.developers.solve.problem.entity.Problem;
import com.developers.solve.problem.entity.QProblem;
import com.developers.solve.solution.entity.QSolution;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.util.StringUtils;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import util.OrderByNull;

import java.time.LocalDateTime;
import java.util.List;

import static com.querydsl.jpa.JPAExpressions.select;

@Repository
@RequiredArgsConstructor
public class ProblemQueryDsl {
    private final JPAQueryFactory queryFactory;

    QProblem problem = QProblem.problem;
    QSolution solution = QSolution.solution;


    private OrderSpecifier<Long> getProblemSortedByViews(String order){
        if (!StringUtils.isNullOrEmpty(order) && order == "views") {
        return problem.views.desc();
    }
        else
            return OrderByNull.getDefault();
    }
    private OrderSpecifier<LocalDateTime> getProblemSortedByLocalTime(String order){
        if (!StringUtils.isNullOrEmpty(order) && order == "createdTime"){
            return problem.createdAt.desc();}
        else
            return OrderByNull.getDefault();
    }
    private OrderSpecifier<Long> getProblemSortedByLikes(String order){
        if (!StringUtils.isNullOrEmpty(order) && order == "likes")
        {return problem.likes.desc();}
        else
            return OrderByNull.getDefault();
    }
    public void addViewCntFromRedis(Long problemId, Long views){
        queryFactory.update(problem).set(problem.views, views).where(problem.problemId.eq(problemId)).execute();
    }
    public void addLikesCntFromRedis(Long problemId, Long likes){
        queryFactory.update(problem).set(problem.likes, likes).where(problem.problemId.eq(problemId)).execute();
    }
    public List<Problem> getProblemSortedByNotSolved(String order,String types,String level,String hashtag,Long likes,Long views,String createdTime){
        List<Problem> content = queryFactory.selectFrom(problem).where(containHashTag(hashtag),CreatedTimeNoOffset(createdTime),LikesNoOffset(likes), ViewsNoOffset(views),containLevel(level),containType(types)).orderBy(getProblemSortedByLikes(order),getProblemSortedByViews(order),getProblemSortedByLocalTime(order)).limit(500).fetch();
        return content;
    }
    public List<Problem> getProblemSortedBySolved(String order,String types,String level,String hashtag,Long likes,Long views,String createdTime, String writer){
        List<Problem> content = queryFactory.selectFrom(problem).where(problem.problemId.in(JPAExpressions.select(solution.problemId.problemId).from(solution).where(containWriter(writer))),containHashTag(hashtag),CreatedTimeNoOffset(createdTime),LikesNoOffset(likes), ViewsNoOffset(views),containLevel(level),containType(types)).orderBy(getProblemSortedByLikes(order),getProblemSortedByViews(order),getProblemSortedByLocalTime(order)).limit(100).fetch();
        return content;
    }
    private BooleanExpression containHashTag(String hashtag){
        if (StringUtils.isNullOrEmpty(hashtag)) {
            return null;
        }
        if (!StringUtils.isNullOrEmpty(hashtag) && (hashtag.contains("CS")||hashtag.contains("FrontEnd")||hashtag.contains("BackEnd")||hashtag.contains("Cloud"))||hashtag.contains("Algorithm")){
            return problem.hashtag.contains(hashtag);
        }
        return null;
    }
    private BooleanExpression CreatedTimeNoOffset(String createdTime){
        if(StringUtils.isNullOrEmpty(createdTime)){
            return null;
        }
        return problem.createdAt.gt(LocalDateTime.parse(createdTime));
    }
    private BooleanExpression LikesNoOffset(Long likes){
        if(likes == null){
            return null;
        }
        return problem.likes.lt(likes);
    }
    private BooleanExpression ViewsNoOffset(Long views){
        if(views == null){
            return null;
        }
        return problem.views.lt(views);
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
    private BooleanExpression containWriter(String writer){
        if(!StringUtils.isNullOrEmpty(writer)){
            return  solution.solver.contains(writer);
        }
        return null;
    }
    }

