package com.developers.solve.problem.controller;

import com.developers.solve.problem.requestDTO.ProblemSaveRequestDto;
import com.developers.solve.problem.responseDTO.ProblemDetailResponseDto;
import com.developers.solve.problem.responseDTO.ProblemSortResponseDTO;
import com.developers.solve.problem.requestDTO.ProblemUpdateRequestDto;
import com.developers.solve.problem.responseDTO.SortResponseDTO;
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
    @GetMapping("/problem/list")
    public ResponseEntity<SortResponseDTO> sortProblem(@RequestParam(value = "order", required = true, defaultValue = "localTime") String order,
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
            List<ProblemSortResponseDTO> result = problemService.NotIncludeSolvedSort(order, types, level, solved, hashtag, views, likes, createdTime, writer);
            response = SortResponseDTO.
                    builder().
                    msg("Success sort").
                    status("stauts Code 200").
                    data(result).
                    build();
        }
        else{
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
    @GetMapping("/problem/{problemId}")
    public ResponseEntity<ProblemDetailResponseDto> detailProblem(@PathVariable Long problemId) {
        problemService.addViewCntToRedis(problemId);
        return null;
    }
    @GetMapping("/problem/likes/{problemId}")
    public void likesAdd(@PathVariable Long problemId){
        problemService.addLikesCntToRedis(problemId);
    }
    @PostMapping("/problem/save")
    public Long save(@RequestBody ProblemSaveRequestDto problemSaveRequestDto){
        Long result = problemService.save(problemSaveRequestDto);
        return result;
    }
}
