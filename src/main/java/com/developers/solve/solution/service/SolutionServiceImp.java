package com.developers.solve.solution.service;

import com.developers.solve.problem.Exception.NullpointException;
import com.developers.solve.problem.entity.Problem;
import com.developers.solve.problem.repository.ProblemRepository;
import com.developers.solve.solution.dto.SolutionRequest;
import com.developers.solve.solution.dto.SolutionResponse;
import com.developers.solve.solution.entity.Solution;
import com.developers.solve.solution.repository.SolutionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;


@RequiredArgsConstructor
@Service
@Log4j2
public class SolutionServiceImp implements SolutionService {
    private final ProblemRepository problemRepository;
    private final SolutionRepository solutionRepository;

    @Override
    public SolutionResponse save(SolutionRequest request) {
        Problem problem = problemRepository.findById(request.getProblemId())
                .orElseThrow(NullpointException::new);

        Solution solution = Solution.builder()
                .solver(request.getSolver())
                .problemId(problem)
                .build();

        Long result = solutionRepository.save(solution).getSolutionId();


        SolutionResponse response =  SolutionResponse.builder()
                .status(String.valueOf(HttpStatus.OK))
                .msg("정상적으로 처리 되었습니다.")
                .solutionId(result)
                .build();

        return response;
    }

}