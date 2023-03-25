package com.developers.solve.repository;

import com.developers.solve.problem.entity.Problem;
import com.developers.solve.problem.repository.ProblemRepository;
import com.developers.solve.solution.entity.Solution;
import com.developers.solve.solution.repository.SolutionRepository;
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
        LongStream.range(0L,100L).forEach(l -> {problem = Problem.builder()
                .type("answer")
                .writer("lango")
                .title("Spring Boot의 장/단점 중 잘못된 것은 무엇일까요?")
                .content("Spring Boot의 장/단점 중 잘못된 것은 무엇인지 4가지 중에 골라주세요.")
                .answer("1")
                .level("gold")
                .views(1L+l)
                .likes(1L+l)
                .build();
        problemRepository.save(problem);});

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
                .problemId(4L)
                .type("answer")
                .writer("lango")
                .title("Spring Boot의 장/단점 중 잘못된 것은 무엇일까요?")
                .content("Spring Boot의 장/단점 중 잘못된 것은 무엇인지 4가지 중에 골라주세요.")
                .answer("1")
                .level("gold")
                .views(999L)
                .likes(999L)
                .build();
        LongStream.range(0L,3L).forEach(l -> {
            solution = Solution.builder()
                    .userId(1L+l)
                    .problemId(problem).
                    build();
            solutionRepository.save(solution);
        });
    }

}
