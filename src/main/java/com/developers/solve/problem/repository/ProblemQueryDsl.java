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
    //Service Test 중 오류가 났던 이유는 우리는 이전 코드는 Entitiy manager와 JpaquerFactory랑 매치를 제대로 안시켰기 때문에 JPAQueryFactory가 Null깂이기 떄문에
    //아무리 Query dsl로 구축해도 조건이 안먹었던 것이다. 해결방안 : 기존에 Query dsl 을 위한 인터페이스를 생성해주었지만, 굳이 만들필요없이 config파일과 함께 class 하나로 구축할 수 있다.
    //confing를 통해 JPAQeuryFactory를 빈으로 만들고 가져온다. 그리고 참고로 클래스로 만들었기에 레포지토리 어노테이션을 붙이고 추가적으로 config class를 의존성 주입을 받기 때문에
    //@RequiredArgsConstructor를 설정해주자
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
    //Paging 기능을 추가함
    //JPA에서 제공하는 Page 인터페이스?를 활용하기 시작, query dsl은 offset과 limit으로 paging 을 구한할 수 있지만. Paging기능을 활용하여 좀 더 쉽게 객체들을 받아오고자 함
    //추가적으로 PageableExecutionUtils를 활용하여 맨 첫 페이지 content 개수가 size 미달이거나, 마지막 page인 경우 count query 실행 X하여 최적화
    //Count query를 따로 만들어준 이유에 대한 설명(fetchCount 사장됨) 따로 쿼리를 만들어서 던져줘야한다.
    //Null 값 처리에 대한 것 정리, Nooffset에 대한 정리, Cache 시스템 구현 과 그것을 안쓰고 Indexed DB에 위임한 이유에 대해 정리!!
//    public Page<Problem> getProblemSortedByLevel(String condition, Pageable pageable) {
//        List<Problem> content = queryFactory.selectFrom(problem).where(containLevel(condition),containType(condition)).orderBy(getProblemSortedByLikes(condition),getProblemSortedByViews(condition),getProblemSortedByLocalTime(condition)).offset(pageable.getOffset()).limit(pageable.getPageSize()).fetch();
//        JPAQuery<Long> Count = queryFactory.select(problem.count()).from(problem).where(containLevel(condition),containType(condition)).orderBy(getProblemSortedByLikes(condition),getProblemSortedByViews(condition),getProblemSortedByLocalTime(condition));
//        return PageableExecutionUtils.getPage(content,pageable,Count::fetchOne);
//    }
//    public Page<Problem> getProblemSortedBySolved(String condition, Pageable pageable){
//        List<Problem> content1 = queryFactory.selectFrom(problem).join(solution.problemId, problem).where(containLevel(condition),containType(condition)).orderBy(getProblemSortedByLikes(condition),getProblemSortedByViews(condition),getProblemSortedByLocalTime(condition)).offset(pageable.getOffset()).limit(pageable.getPageSize()).fetch();
//        JPAQuery<Long> Count1 = queryFactory.select(problem.count()).from(problem).join(solution.problemId, problem).where(containLevel(condition),containType(condition)).orderBy(getProblemSortedByLikes(condition),getProblemSortedByViews(condition),getProblemSortedByLocalTime(condition));
//        return PageableExecutionUtils.getPage(content1,pageable,Count1::fetchOne);
//    }
    public List<Problem> getProblemSortedByLevel(String order,String types,String level,Long problemId) {
        List<Problem> content = queryFactory.selectFrom(problem).where(ProblemId(problemId),containLevel(level),containType(types)).orderBy(getProblemSortedByLikes(order),getProblemSortedByViews(order),getProblemSortedByLocalTime(order)).limit(100).fetch();
        return content;
    }
    public List<Problem> getProblemSortedBySolved(String order,String types,String level,Long problemId){
        List<Problem> content1 = queryFactory.selectFrom(problem).join(solution).on(problem.problemId.eq(solution.problemId.problemId)).where(ProblemId(problemId),containLevel(level),containType(types)).orderBy(getProblemSortedByLikes(order),getProblemSortedByViews(order),getProblemSortedByLocalTime(order)).limit(100).fetch();
        return content1;
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
//    private BooleanExpression containSolved(String condition){
//        int condition1 = Integer.parseInt(condition);
//        if (condition1 != 1){
//            return null;
//        }
//        else
//            return solution.solved.eq(condition1);
//    }
//    public List<Problem> getProblemBySolved(String condition){
//        return queryFactory.select(problem).from(solution).innerJoin(solution.problemId, problem).where(containSolved(condition)).limit(20).fetch();
    }

