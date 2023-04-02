package com.developers.solve.problem.service;

import com.developers.solve.problem.dto.requestDTO.ProblemSaveRequestDto;
import com.developers.solve.problem.dto.requestDTO.ProblemUpdateRequestDto;
import com.developers.solve.problem.dto.responseDTO.*;
import com.developers.solve.problem.entity.Problem;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ProblemService {
    List<ProblemSortResponseDTO> NotIncludeSolvedSort(String order, String types, String level, String solved, String hashtag, Long views, Long likes, String createdTime, String writer);
    List<ProblemSortResponseDTO> IncludeSolvedSort(String order, String types, String level, String solved, String hashtag, Long views, Long likes, String createdTime, String writer);
    @Transactional
    void addViewCntToRedis(Long problemId);
    @Transactional
    void addLikesCntToRedis(Long problemId);
    @Transactional
    void deleteViewCntToRedis();
    @Transactional
    void deleteLikesCntToRedis();

    //문제 등록
    ProblemSaveResponseDto save(ProblemSaveRequestDto problemSaveRequestDto);
    //    CommonResponeDto save(ProblemSaveRequestDto problemSaveRequestDto);
    //문제 상세조회
    ProblemDetailResponseDto problemDetail(Long problemId, String member);
    //문제 수정
    ProblemUpdateResponseDto update(ProblemUpdateRequestDto updateRequestDto);
    //문제 삭제
    ResponseEntity<Long> deleteProblem(Long problemId);
    //문제 title Like검색
    ProblemListResponseDto getListWithSearch(String param);
    default ProblemDetailDto entityToSearchDTO(Problem problem) {
        ProblemDetailDto dto = ProblemDetailDto.builder()
                .type(problem.getType())
                .writer(problem.getWriter()) //writerId?
                .title(problem.getTitle())
                .content(problem.getContent())
                .answer(problem.getAnswer())
                .level(problem.getLevel())
                .build();
        return dto;

    }


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
                    hashTag(problem.getHashTag()).
                    build();
        return dto;
    }
}

