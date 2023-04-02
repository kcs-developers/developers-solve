package com.developers.solve.problem.service;

import com.developers.solve.problem.requestDTO.*;
import com.developers.solve.problem.entity.Problem;
import com.developers.solve.problem.responseDTO.*;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ProblemService {
    List<ProblemSortResponseDTO> NotIncludeSolvedSort(String order, String types, String level, String solved, String hashtag, Long views, Long likes, String createdTime, String writer);
    List<ProblemSortResponseDTO> IncludeSolvedSort(String order, String types, String level, String solved, String hashtag, Long views, Long likes, String createdTime, String writer);
    ProblemSaveResponseDto save(ProblemSaveRequestDto request);
    List<ProblemSortResponseDTO> getListWithSearch(String search);
    @Transactional
    void addViewCntToRedis(Long problemId);
    @Transactional
    void addLikesCntToRedis(Long problemId);
    @Transactional
    void deleteViewCntToRedis();
    @Transactional
    void deleteLikesCntToRedis();
    @Transactional
    ProblemUpdateResponseDto update(ProblemUpdateRequestDto updateRequestDto);
    @Transactional
    String deleteProblem(Long problemId);
    default ProblemSortResponseDTO EntityToDto(Problem problem) {

            ProblemSortResponseDTO dto = ProblemSortResponseDTO.
                    builder().
                    answer(problem.getAnswer()).
                    title(problem.getTitle()).
                    content(problem.getContent()).
                    level(problem.getLevel()).
                    likes(problem.getLikes()).
                    writer(problem.getWriter()).
                    views(problem.getViews()).
                    problemId(problem.getProblemId()).
                    type(problem.getType()).
                    createdTime(problem.getCreatedAt()).
                    hashTag(problem.getHashtag()).
                    build();
        return dto;
    }

    ProblemDetailResponseDto problemDetail(Long problemId, String member);
}

