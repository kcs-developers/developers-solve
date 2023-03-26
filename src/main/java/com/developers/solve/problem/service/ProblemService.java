package com.developers.solve.problem.service;

import com.developers.solve.problem.dto.ProblemSaveRequestDto;
import com.developers.solve.problem.dto.ProblemSortResponseDTO;
import com.developers.solve.problem.dto.SolutionSaveRequetDto;
import com.developers.solve.problem.entity.Problem;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

public interface ProblemService {
    //List<Problem> searchProblem(String condition);

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

    default ProblemSortResponseDTO EntityToDto(Problem problem) {

            ProblemSortResponseDTO dto = ProblemSortResponseDTO.builder().answer(problem.getAnswer()).title(problem.getTitle()).content(problem.getContent()).level(problem.getLevel()).likes(problem.getLikes()).writer(problem.getWriter()).problemId(problem.getProblemId()).type(problem.getType()).build();
        return dto;
    }
}

