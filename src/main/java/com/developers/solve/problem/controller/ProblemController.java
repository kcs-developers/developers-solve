package com.developers.solve.problem.controller;

import com.developers.solve.problem.dto.ProblemDetailResponseDto;
import com.developers.solve.problem.dto.ProblemSaveRequestDto;
import com.developers.solve.problem.dto.ProblemSortResponseDTO;
import com.developers.solve.problem.dto.ProblemUpdateRequestDto;
import com.developers.solve.problem.entity.Problem;
import com.developers.solve.problem.service.ProblemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Log4j2
@RequiredArgsConstructor
@RestController
public class ProblemController {
    private final ProblemService problemService;
    @GetMapping("/problem/list")
    public ResponseEntity<List<ProblemSortResponseDTO>> sortProblem(@RequestParam(value = "order", required = true, defaultValue = "localTime") String order,
                                                                    @RequestParam(value = "types", required = false) String types,
                                                                    @RequestParam(value = "level", required = false) String level,
                                                                    @RequestParam(value = "solved", required = false) String solved,
                                                                    @RequestParam(value = "problemId", required = false) Long problemId,
                                                                    @RequestParam(value = "hashTag",required = false) String hashtag){
        List<ProblemSortResponseDTO> response = problemService.FirstSortProblem(order,types,level,solved,problemId,hashtag);
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
    @PostMapping("/problem/update")
    public void update(@RequestBody ProblemUpdateRequestDto problemUpdateRequestDto){
        problemService.updateproblem(problemUpdateRequestDto);
    }
}
