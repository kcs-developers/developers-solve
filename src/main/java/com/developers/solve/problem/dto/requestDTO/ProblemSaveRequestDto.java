package com.developers.solve.problem.dto.requestDTO;

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
    private String hashtag;
    private String level;
    private Long views;
    private Long likes;
    private Long id;

}


