package com.developers.solve.problem.service;

import com.developers.solve.problem.dto.*;
import com.developers.solve.problem.entity.Problem;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ProblemService {
    //List<Problem> searchProblem(String condition);

    //Page<Entity> 에서 Page<DTO>변환은 단순 map을 통해서 바꿀수 있다???
    //condition = 0&likes&solved
    //condition = 1&likes&soleved&type

    //    @Override
    //    public List<Problem> searchProblem(String condition){
    //        List<Problem> result = null;
    //        if(condition.contains(",")) {
    //            List<String> tags = List.of(condition.split(","));
    //            result = problemRepository.findByTags(tags);
    //        }
    //        else {
    //            switch (condition) {
    //                case "likes": case "views": case "times":
    //                    result = problemRepository.findByConditionOrderByDesc(condition);
    //                    break;
    //                // 난이도(하, 중, 상) 별 검색
    //                case "bronze": case "silver": case "gold":
    //                    result = problemRepository.findByProblemLikeLevel(condition);
    //                    break;
    //                //유형(객관식, 주관식) 별 검색
    //                case "choice": case "answer":
    //                    result = problemRepository.findByProblmeLikeType(condition);
    //                    // 푼 문제들
    //                case "solved":
    //                    result = problemRepository.findByProblemInSolution(condition);
    //                    break;
    //                // 안 푼 문제들
    //                case "noneSolved":
    //                    result = problemRepository.findByProblemNotInSolution(condition);
    //                    break;
    //                default:
    //                    break;
    //            }
    //        }
    //        return null;
//    //    }
//    @Cacheable(cacheNames = "FirstSortProblem")
    List<ProblemSortResponseDTO> FirstSortProblem(String order,String types,String level,String solved,Long problemId,String hashtag);
//
//    @Cacheable(cacheNames = "NotFirstSortProblem")
//    List<ProblemSortResponseDTO> NotFirstSortProblem(String order,String types,String level,String solved,Long problemId);

    Long save(ProblemSaveRequestDto request);
    Long saveSolution(SolutionSaveRequetDto solutiondto);
    Long saveHashTag(ProblemSaveRequestDto requestDto);
    @Transactional
    void addViewCntToRedis(Long problemId);
    @Transactional
    void addLikesCntToRedis(Long problemId);
    @Transactional
    void deleteViewCntToRedis();
    @Transactional
    void deleteLikesCntToRedis();
    @Transactional
    void updateproblem(ProblemUpdateRequestDto problemUpdateRequestDto);

    default ProblemSortResponseDTO EntityToDto(Problem problem) {

            ProblemSortResponseDTO dto = ProblemSortResponseDTO.builder().answer(problem.getAnswer()).title(problem.getTitle()).content(problem.getContent()).level(problem.getLevel()).likes(problem.getLikes()).writer(problem.getWriter()).problemId(problem.getProblemId()).type(problem.getType()).build();
        return dto;
    }
}

