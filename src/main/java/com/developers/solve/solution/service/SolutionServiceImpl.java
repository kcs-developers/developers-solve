package com.developers.solve.solution.service;

import com.developers.solve.problem.entity.Problem;
import com.developers.solve.problem.repository.ProblemRepository;
import com.developers.solve.solution.dto.SolutionId;
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
public class SolutionServiceImpl implements SolutionService {
    private final ProblemRepository problemRepository;
    private final RestTemplate restTemplate;
    private final SolutionRepository solutionRepository;

    @Override
    public SolutionResponse save(SolutionRequest request) {
        Problem problem = problemRepository.findById(request.getProblemId())
                .orElseThrow(() -> new RuntimeException("Problem not found"));

        String url = "http://localhost:8000/api/member/"+request.getId();
        Map<String,String> res = restTemplate.getForObject(url, Map.class);
        String restResult = res.get("memberName");


        Solution solution = Solution.builder()
                .solver(restResult)
                .problem(problem)
                .build();

        Long result = solutionRepository.save(solution).getSolutionId();

        SolutionId solutionId = SolutionId.builder()
                .solutionId(result)
                .build();

        SolutionResponse response =  SolutionResponse.builder()
                .code(String.valueOf(HttpStatus.OK))
                .msg("정상적으로 처리 되었습니다.")
                .data(solutionId)
                .build();

        return response;
    }

}
