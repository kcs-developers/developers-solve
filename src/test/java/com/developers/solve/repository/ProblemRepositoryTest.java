package com.developers.solve.repository;

import com.developers.solve.problem.entity.Problem;
import com.developers.solve.problem.repository.ProblemRepository;
import com.developers.solve.solution.entity.Solution;
import com.developers.solve.solution.repository.SolutionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.LongStream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class ProblemRepositoryTest {
    Solution solution;
    Problem problem;
    @Autowired
    ProblemRepository problemRepository;
    @Autowired
    SolutionRepository solutionRepository;

    @Test
    public void save() {

        // given
        LongStream.range(0L, 50L).forEach(l -> {
            problem = Problem.builder()
                    .type("answer")
                    .writer("Taeho")
                    .title("Spring Boot의 장/단점 중 잘못된 것은 무엇일까요?")
                    .content("Spring Boot의 장/단점 중 잘못된 것은 무엇인지 4가지 중에 골라주세요.")
                    .answer("1")
                    .level("bronze")
                    .views(200L + l)
                    .likes(4L + l)
                    .hashTag("CS,FrontEnd,BackEnd,Cloud")
                    .build();
            problemRepository.save(problem);
        });

        // when
        List<Problem> problemList = problemRepository.findAll();

        // then
        Problem result = problemList.get(0);
        assertThat(result.getProblemId()).isEqualTo(1L);
        assertThat(result.getWriter()).isEqualTo("lango");
        assertThat(result.getAnswer()).isEqualTo("1");
    }

    @Test
    public void save1() {
        Problem problem = Problem.builder()
                .problemId(205L)
                .type("answer")
                .writer("lango")
                .title("Spring Boot의 장/단점 중 잘못된 것은 무엇일까요?")
                .content("Spring Boot의 장/단점 중 잘못된 것은 무엇인지 4가지 중에 골라주세요.")
                .answer("1")
                .level("gold")
                .views(94L)
                .likes(32L)
                .build();
        LongStream.range(10L, 11L).forEach(l -> {
            solution = Solution.builder()
                    .solver("Woo")
                    .problem(problem).
                    build();
            solutionRepository.save(solution);
        });
    }


    @DisplayName("문제 상세 정보 조회 - 문제 푼 사용자")
    @Test
    public void problemDetailSolved() {
        // given
        Problem problem = Problem.builder()
                .writer("lango")
                .title("test")
                .content("Test111")
                .type("CHOICE")
                .level("silver")
                .answer("2")
                .build();
        problemRepository.save(problem);

        Solution solution = Solution.builder()
                .problem(problem)
                .solver("lango")
                .build();
        solutionRepository.save(solution);

        // when
        Boolean result = solutionRepository.existsBySolverAndProblemProblemId("lango", problem.getProblemId());

        // then
        Assertions.assertTrue(result);

    }

    @DisplayName("문제 상세 정보 조회 - 문제 안 푼 사용자")
    @Test
    public void problemDetailNotSolved() {
        // given
        Problem problem = Problem.builder()
                .writer("lango")
                .title("test")
                .content("Test111")
                .type("CHOICE")
                .level("silver")
                .answer("2")
                .build();
        problemRepository.save(problem);

        // when
        Boolean result = solutionRepository.existsBySolverAndProblemProblemId("lango", problem.getProblemId());

        // then
        Assertions.assertFalse(result);


    }
}