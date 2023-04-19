package com.developers.solve.problem.service;

import com.developers.solve.problem.requestDTO.*;
import com.developers.solve.problem.entity.Problem;
import com.developers.solve.problem.responseDTO.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface ProblemService {
    void CreatedTimeSortCacheEvict();
    void CreatedCacheUpdate();
    @Transactional
    String uploadAttached(SaveAttachedDto saveAttachedDto);
    @Transactional
    String UpdateAttached(UpdatedAttachedDto updatedAttachedDto);
    List<ProblemSortResponseDTO> CreatedTimeSortList();
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
        List<String> answerCandidate;
        List<String> hashTag;
        if (problem.getHashtag() != null) {
            hashTag = Arrays.stream(problem.getHashtag().split(",")).toList();
        } else {
            hashTag = new ArrayList<>();
        }
        if (problem.getAnswerCandidate() != null) {
            answerCandidate = Arrays.stream(problem.getAnswerCandidate().split(",")).toList();
        } else {
            answerCandidate = new ArrayList<>();
        }
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
                    hashTag(hashTag).
                    answerCandidate(answerCandidate).
                    build();
        return dto;
    }
    ProblemDetailResponseDto problemDetail(Long problemId, String member);




}

