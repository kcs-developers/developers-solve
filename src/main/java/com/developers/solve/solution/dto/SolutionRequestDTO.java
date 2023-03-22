package com.developers.solve.solution.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NotFound;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SolutionRequestDTO {
    private Long id; // user 아이디로 보냄

    private String solver;

    private String solved;

    private Long problemId; //문제 번호가 있어야하므로 외래키 삽입

}
