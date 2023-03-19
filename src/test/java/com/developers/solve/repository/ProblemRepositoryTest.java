package com.developers.solve.repository;

import com.developers.solve.problem.entity.Level;
import com.developers.solve.problem.entity.Problem;
import com.developers.solve.problem.entity.Type;
import com.developers.solve.problem.repository.ProblemRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class ProblemRepositoryTest {
    @Autowired
    ProblemRepository problemRepository;

    @Test
    public void save() {
        // given
        Problem problem = Problem.builder()
                .type(Type.CHOICE)
                .writer("lango")
                .title("Spring Boot의 장/단점 중 잘못된 것은 무엇일까요?")
                .content("Spring Boot의 장/단점 중 잘못된 것은 무엇인지 4가지 중에 골라주세요.")
                .answer("1")
                .level(Level.bronze)
                .views(0L)
                .likes(0L)
                .build();
        problemRepository.save(problem);

        // when
        List<Problem> problemList = problemRepository.findAll();

        // then
        Problem result = problemList.get(0);
        assertThat(result.getProblemId()).isEqualTo(1L);
        assertThat(result.getWriter()).isEqualTo("lango");
        assertThat(result.getAnswer()).isEqualTo("1");
    }

}
