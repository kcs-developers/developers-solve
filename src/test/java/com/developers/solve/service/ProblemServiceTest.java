package com.developers.solve.service;

import com.developers.solve.problem.dto.requestDTO.ProblemSaveRequestDto;
import com.developers.solve.problem.dto.requestDTO.ProblemUpdateRequestDto;
import com.developers.solve.problem.dto.responseDTO.ProblemDetailResponseDto;
import com.developers.solve.problem.dto.responseDTO.ProblemListResponseDto;
import com.developers.solve.problem.dto.responseDTO.ProblemSaveResponseDto;
import com.developers.solve.problem.dto.responseDTO.ProblemUpdateResponseDto;
import com.developers.solve.problem.entity.Problem;
import com.developers.solve.problem.repository.ProblemRepository;
import com.developers.solve.problem.service.ProblemService;
import com.developers.solve.solution.dto.SolutionRequest;
import com.developers.solve.solution.dto.SolutionResponse;
import com.developers.solve.solution.entity.Solution;
import com.developers.solve.solution.repository.SolutionRepository;
import com.developers.solve.solution.service.SolutionService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
public class ProblemServiceTest {

    @Autowired
    private ProblemService problemService;
    @Autowired
    private ProblemRepository problemRepository;
    @Autowired
    private SolutionService solutionService;
    @Autowired
    private SolutionRepository solutionRepository;
    @Autowired
    private RestTemplate restTemplate;

    @Test
    @DisplayName("Default(created_time) Test")
    public void DefaultTest() {

        List response = problemService.NotIncludeSolvedSort("createdTime", null, null, null, null, null, null, null, null);
        System.out.println(response);

    }

    @Test
    @DisplayName("LikesOrder Test")
    public void OrderLikesTest() {
        List response = problemService.NotIncludeSolvedSort("likes", null, null, null, null, null, null, null, null);
        System.out.println(response);
    }

    @Test
    @DisplayName("ViewsOrder Test")
    public void OrderViewsTest() {
        List response = problemService.NotIncludeSolvedSort("views", null, null, null, null, null, null, null, null);
        System.out.println(response);
    }

    @Test
    @DisplayName("OrderAndType")
    public void OrderAndType() {
        List response = problemService.NotIncludeSolvedSort("views", "choice", null, null, null, null, null, null, null);
        System.out.println(response);
    }

    @Test
    @DisplayName("OrderAndTypeLevel")
    public void OrderAndLevelType() {
        List response = problemService.NotIncludeSolvedSort("views", null, "bronze", null, null, null, null, null, null);
        System.out.println(response);
    }

    @Test
    @DisplayName("OrderAndTypeLevelSolved")
    public void OrderAndLevelTypeSolved() {
        List response = problemService.IncludeSolvedSort("createdTime", null, null, "True", null, null, null, null, "Taeho");
        System.out.println(response);
    }
    @DisplayName("문제 등록")
    @Test
    public void testSave(){
        //given

        String url = "http://localhost:8000/api/member/1";
        Map<String,String> res = restTemplate.getForObject(url, Map.class);
        String restResult = res.get("memberName");
        System.out.println(restResult);

        ProblemSaveRequestDto requestDto = ProblemSaveRequestDto.builder()
                .type("CHOICE")
                .writer(restResult)
                .title("Spring Boot의 장/단점 중 잘못된 것은 무엇일까요?")
                .content("Spring Boot의 장/단점 중 잘못된 것은 무엇인지 4가지 중에 골라주세요.")
                .answer("1")
                .level("bronze")
                .views(0L)
                .likes(0L)
                .id(1L)
                .build();

        //when
        ProblemSaveResponseDto responseDto = problemService.save(requestDto);
        Optional<Problem> result = problemRepository.findById(responseDto.getData().getProblemId());

        //then
        Assertions.assertEquals(responseDto.getData().getAnswer(), "1");
        Assertions.assertTrue(result.isPresent());
    }


    @DisplayName("문제 상세 정보 조회 - 문제 푼 사용자")
    @Test
    public void problemDetailSolved() {
        // given
        String url = "http://localhost:8000/api/member/1";
        Map<String,String> res = restTemplate.getForObject(url, Map.class);
        String restResult = res.get("memberName");

        ProblemSaveRequestDto saveRequestDto = ProblemSaveRequestDto.builder()
                .writer(restResult)
                .title("test")
                .content("Test111")
                .type("CHOICE")
                .level("silver")
                .answer("2")
                .id(1L)
                .build();


        ProblemSaveResponseDto problemSaveResponseDto = problemService.save(saveRequestDto);

        SolutionRequest request = SolutionRequest.builder()
                .problemId(problemSaveResponseDto.getData().getProblemId())
                .solver(saveRequestDto.getWriter())
                .id(1L)
                .build();
        // when
        SolutionResponse solutionResponse = solutionService.save(request);
        System.out.println("응답" + solutionResponse);

        ProblemDetailResponseDto responseDto = problemService.problemDetail(problemSaveResponseDto.getData().getProblemId(), "lango");

        System.out.println("상세조회:" + responseDto);
        System.out.println("답:" + solutionRepository.findById(responseDto.getData().getProblemId()));


        // then
        Optional<Solution> solution = solutionRepository.findById(solutionResponse.getData().getSolutionId());
        Assertions.assertNotNull(solution.get());
        Assertions.assertTrue(responseDto.getData().getSolved());
    }

    @DisplayName("문제 상세 정보 조회 - 문제 안푼 사용자")
    @Test
    public void problemDetailNotSolved() {
        // given
        String url = "http://localhost:8000/api/member/1";
        Map<String,String> res = restTemplate.getForObject(url, Map.class);
        String restResult = res.get("memberName");

        ProblemSaveRequestDto saveRequestDto = ProblemSaveRequestDto.builder()
                .writer(restResult)
                .title("test")
                .content("Test111")
                .type("CHOICE")
                .level("silver")
                .answer("2")
                .id(1L)
                .build();

        ProblemSaveResponseDto problemSaveResponseDto = problemService.save(saveRequestDto);
        SolutionRequest request = SolutionRequest.builder()
                .problemId(problemSaveResponseDto.getData().getProblemId())
                .solver("saveRequest")
                .id(1L)
                .build();
        // when

        SolutionResponse solutionResponse = solutionService.save(request);

        ProblemDetailResponseDto responseDto = problemService.problemDetail(problemSaveResponseDto.getData().getProblemId(), "test123");

        // then
        Assertions.assertFalse(responseDto.getData().getSolved());
        System.out.println(responseDto.getData().getSolved());
    }



    @DisplayName("문제 수정")
    @Test
    @Transactional
    public void testUpdate(){
        //given
        ProblemUpdateRequestDto requestDto = ProblemUpdateRequestDto.builder()
                .problemId(2L)
                .writer("wjdduq")
                .answer("3")
                .content("goodgood")
                .level("gold")
                .title("13")
                .hashtag("goog,goof")
                .type("answer")
                .build();
        //when
        ProblemUpdateResponseDto responseDto = problemService.update(requestDto);
        //then
        System.out.println(responseDto);
        Assertions.assertEquals(responseDto.getData().getAnswer(), "3");
        Assertions.assertEquals(responseDto.getData().getLevel(), "gold");
    }

    @DisplayName("문제 삭제")
    @Test
    @Transactional
    public void testDeleteProblem() {
        // given
        Long problemId = 1L;
        Problem target = problemRepository.findById(problemId)
                .orElseThrow(() -> new IllegalArgumentException("삭제 대상이 없다."));

        // when
        Long deletedProblemId = problemService.deleteProblem(target.getProblemId()).getBody();
        Optional<Problem> result = problemRepository.findById(deletedProblemId);

        // then
        System.out.println(deletedProblemId);
        assertFalse(result.isPresent());
    }

    @DisplayName("문제 Like검색")
    @Test
    void problemLike(){
        //given
        String param= "spring boot";

        //when
        ProblemListResponseDto problemList = problemService.getListWithSearch(param);

        //then
        System.out.println(param+"으로 찾은 문제 개수"+problemList.getData().size());
        System.out.println(param+"으로 찾은 문제 조회"+problemList);

    }

}


