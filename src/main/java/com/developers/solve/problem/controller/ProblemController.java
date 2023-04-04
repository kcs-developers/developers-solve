package com.developers.solve.problem.controller;

import com.developers.solve.problem.requestDTO.AttachedDto;
import com.developers.solve.problem.requestDTO.ProblemSaveRequestDto;
import com.developers.solve.problem.responseDTO.*;
import com.developers.solve.problem.requestDTO.ProblemUpdateRequestDto;
import com.developers.solve.problem.service.ProblemService;
import com.querydsl.core.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RequiredArgsConstructor
@RestController
public class ProblemController {
    private final ProblemService problemService;
    //문제 다중 조건 검색
    @GetMapping("/problem/list")
    public ResponseEntity<SortResponseDTO> sortProblem(@RequestParam(value = "order", required = true, defaultValue = "createdTime") String order,
                                                                    @RequestParam(value = "types", required = false) String types,
                                                                    @RequestParam(value = "level", required = false) String level,
                                                                    @RequestParam(value = "solved", required = false) String solved,
                                                                    @RequestParam(value = "views", required = false) Long views,
                                                                    @RequestParam(value = "likes", required = false) Long likes,
                                                                    @RequestParam(value = "createdTime", required = false) String createdTime,
                                                                    @RequestParam(value = "hashTag",required = false) String hashtag,
                                                                    @RequestParam(value = "writer", required = true) String writer)
    {
        SortResponseDTO response;
        if(StringUtils.isNullOrEmpty(solved)) {
            if (StringUtils.isNullOrEmpty(types) && StringUtils.isNullOrEmpty(level) && StringUtils.isNullOrEmpty(hashtag) && order.equals("createdTime")){
                List<ProblemSortResponseDTO> result = problemService.CreatedTimeSortList();
                response = SortResponseDTO.
                        builder().
                        msg("Success sort").
                        status("stauts Code 200").
                        data(result).
                        build();
            }
            List<ProblemSortResponseDTO> result = problemService.NotIncludeSolvedSort(order, types, level, solved, hashtag, views, likes, createdTime, writer);
            response = SortResponseDTO.
                    builder().
                    msg("Success sort").
                    status("stauts Code 200").
                    data(result).
                    build();
        }
         else {
            List<ProblemSortResponseDTO> result = problemService.IncludeSolvedSort(order, types, level, solved, hashtag, views, likes, createdTime, writer);
            response = SortResponseDTO.
                    builder().
                    msg("Success sort").
                    status("stauts Code 200").
                    data(result).
                    build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    //문제 등록
    @PostMapping("/problem")
    public ResponseEntity<ProblemSaveResponseDto> register(@RequestBody ProblemSaveRequestDto saveRequestDto){
        ProblemSaveResponseDto problemSaveResponseDto = problemService.save(saveRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(problemSaveResponseDto);
    }
    //문제 상세조회
    @GetMapping("/problem/{problemId}/{member}")
    public ResponseEntity<ProblemDetailResponseDto> detailProblem(@PathVariable Long problemId, @PathVariable String member) {
        ProblemDetailResponseDto detailResponseDto = problemService.problemDetail(problemId, member);
        problemService.addViewCntToRedis(problemId);
        return ResponseEntity.status(HttpStatus.OK).body(detailResponseDto);
    }
    //문제 좋아요 처리
    @GetMapping("/problem/likes/{problemId}")
    public void likesAdd(@PathVariable Long problemId){
        problemService.addLikesCntToRedis(problemId);
    }
    //게시물 수정
    @PatchMapping("/problem")
    public ResponseEntity<ProblemUpdateResponseDto> update(@RequestBody ProblemUpdateRequestDto problemUpdateRequestDto){
        System.out.println(problemUpdateRequestDto);
        ProblemUpdateResponseDto updateResponseDto = problemService.update(problemUpdateRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(updateResponseDto);
    }

    //게시물 삭제
    @DeleteMapping("/problem/{problemId}")
    public ResponseEntity<String> deleteProblem(@PathVariable Long problemId){
        String result = problemService.deleteProblem(problemId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
    //게시물 검색
    @GetMapping("/problem")
    public ResponseEntity<List<ProblemSortResponseDTO>> searchProblem(@RequestParam(value = "search") String search){
        List<ProblemSortResponseDTO> result = problemService.getListWithSearch(search);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
    //첨부파일 업로드
    @PostMapping("/S3/upload")
    public ResponseEntity <Object> S3register(@RequestBody AttachedDto attachedDto){
        String result = problemService.uploadAttached(attachedDto);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
