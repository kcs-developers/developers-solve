package com.developers.solve.problem.controller;

import com.developers.solve.problem.dto.ProblemSaveRequestDto;
import com.developers.solve.problem.dto.ProblemSortResponseDTO;
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
    public ResponseEntity<List<ProblemSortResponseDTO>> sortProblem(@RequestParam(value = "order", required = true, defaultValue = "localTime") String order,@RequestParam(value = "types", required = false) String types, @RequestParam(value = "level", required = false) String level, @RequestParam(value = "solved", required = false) String solved, @RequestParam(value = "ProblemId", required = false) Long problemId,@RequestParam(value = "HashTag",required = false) String HashTag){
        List<ProblemSortResponseDTO> response = problemService.FirstSortProblem(order,types,level,solved,problemId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
//    @GetMapping("/url")
//    public void getList(@RequestParam Map<String, String> paramMap) {
//        String sort1 = paramMap.get("sort1");
//        String sort2 = paramMap.get("sort2");
//        String sort3 = paramMap.get("sort3");
//        String sort4 = paramMap.get("sort4");
//        String sort5 = paramMap.get("sort5");
//        String sort6 = paramMap.get("sort6");
//        String[] tags = paramMap.get("tag").split(",");
//        System.out.println(sort1+ " " + sort2 + " " + Arrays.toString(tags));
//    }
    @PostMapping("/url")
    public void resgister(@RequestBody ProblemSaveRequestDto dto){
        this.problemService.save(dto);
    }
}
