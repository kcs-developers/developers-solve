package com.developers.solve.problem.requestDTO;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ProblemSaveRequestDto {
    private Long problemId;
    private String type;
    private String writer;
    private String title;
    private String content;
    private String answer;
    private String level;
    private String tag;
    private Long id;

}


