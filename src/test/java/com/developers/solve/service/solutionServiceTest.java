package com.developers.solve.service;

import com.developers.solve.problem.repository.ProblemRepository;
import com.developers.solve.solution.dto.SolutionRequest;
import com.developers.solve.solution.dto.SolutionResponse;
import com.developers.solve.solution.entity.Solution;
import com.developers.solve.solution.service.SolutionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class solutionServiceTest {

    @Autowired
    private SolutionService solutionService;

    @Autowired
    private ProblemRepository problemRepository;

    @Autowired
    private RestTemplate restTemplate;
    @Test
    @DisplayName("정답자 저장")
    public void Save() {
        // given

        String url = "http://localhost:8000/api/member/1";
        Map<String,String> res = restTemplate.getForObject(url, Map.class);
        String restResult = res.get("memberName");
        SolutionRequest request = SolutionRequest.builder()
                .solver(restResult)
                .problemId(2L)
                .id(1L)
                .build();

        Solution savedSolution = Solution.builder()
                .solver(restResult)
                .problem(problemRepository.findById(request.getProblemId()).orElseThrow()) //문제에 해당하는 problemId를 가져온다.
                .build();
        // when
        SolutionResponse response = solutionService.save(request);

        // then

        System.out.println(savedSolution);
        Assertions.assertEquals("200 OK", response.getCode());
        Assertions.assertEquals("정상적으로 처리 되었습니다.", response.getMsg());
        assertNotNull(response.getData());
    }

    @Test
    @DisplayName("정답자 저장")
    public void Save1() {
        // given

        String url = "http://localhost:8000/api/member/1";
        Map<String,String> res = restTemplate.getForObject(url, Map.class);
        String restResult = res.get("memberName");
        System.out.println(restResult);
        SolutionRequest request = SolutionRequest.builder()
                .solver(restResult) //lango
                .problemId(2L)
                .id(1L)
                .build();

        SolutionResponse solutionResponse = solutionService.save(request);
        System.out.println(solutionResponse);

        Solution savedSolution = Solution.builder()
                .solver(request.getSolver())
                .problem(problemRepository.findById(request.getProblemId()).orElseThrow()) //문제에 해당하는 problemId를 가져온다.
                .build();

        // when
        SolutionResponse response = solutionService.save(request);

        // then
        Assertions.assertEquals("200 OK", response.getCode());
        Assertions.assertEquals("정상적으로 처리 되었습니다.", response.getMsg());
        assertNotNull(response.getData());
    }

}
