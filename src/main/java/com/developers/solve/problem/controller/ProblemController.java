package com.developers.solve.problem.controller;

import com.developers.solve.problem.dto.ProblemSortResponseDTO;
import com.developers.solve.problem.entity.Level;
import com.developers.solve.problem.entity.Type;
import com.developers.solve.problem.service.ProblemService;
import com.developers.solve.problem.service.ProblemServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Log4j2
@RequiredArgsConstructor
@Controller
public class ProblemController {
    private final ProblemService problemService;
    @GetMapping("/problem/list")
    public ResponseEntity<List<ProblemSortResponseDTO>> sortProblem(@RequestParam("condition") String condition){
        return problemService.sort1(condition);
    }
}
