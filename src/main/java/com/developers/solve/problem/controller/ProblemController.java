package com.developers.solve.problem.controller;

import com.developers.solve.problem.dto.ProblemSortResponseDTO;
import com.developers.solve.problem.entity.Level;
import com.developers.solve.problem.entity.Problem;
import com.developers.solve.problem.entity.Type;
import com.developers.solve.problem.service.ProblemService;
import com.developers.solve.problem.service.ProblemServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Log4j2
@RequiredArgsConstructor
@RestController
public class ProblemController {
    private final ProblemService problemService;
    @GetMapping("/problem/list")
    public ResponseEntity<ProblemSortResponseDTO> sortProblem(@RequestParam("condition") String condition){
        System.out.println(condition);

        // 서비스 메소드에 단일 검색조건이든, 해시태그 목록이든 넘겨줌
        List<Problem> data = problemService.searchProblem(condition);

        ProblemSortResponseDTO response = ProblemSortResponseDTO.builder()
                .data(data)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @GetMapping("/url")
    public void getList(@RequestParam Map<String, String> paramMap) {
        String sort1 = paramMap.get("sort1");
        String sort2 = paramMap.get("sort2");
        String sort3 = paramMap.get("sort3");
        String sort4 = paramMap.get("sort4");
        String sort5 = paramMap.get("sort5");
        String sort6 = paramMap.get("sort6");
        String[] tags = paramMap.get("tag").split(",");
        System.out.println(sort1+ " " + sort2 + " " + Arrays.toString(tags));
    }
}
