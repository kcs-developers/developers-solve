package com.developers.solve.solution.controller;

import com.developers.solve.solution.dto.SolutionRequest;
import com.developers.solve.solution.dto.SolutionResponse;
import com.developers.solve.solution.service.SolutionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;



@Validated
@Log4j2
@RequiredArgsConstructor
@RestController
public class SolutionController {


    private final SolutionService solutionService;
    @PostMapping("/api/solution")
    public ResponseEntity<SolutionResponse> save(@Valid @RequestBody SolutionRequest request){
        SolutionResponse response = solutionService.save(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}