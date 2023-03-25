package com.developers.solve.solution.dto;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
//문제 푸는 것과 동시에 Id, 풀이자, 해당 문제 번호가 DB에 입력된다.
public class SolutionRequest {
    private Long solver; // memberId를 받아오는것으로 계획중
    private Long problemId; // 문제 번호를 받아온다.
}

