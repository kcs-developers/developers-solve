package com.developers.solve.problem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ProblemDetailResponseDto {
    private Long problemId;
    private String type;
    private String writer;
    private String title;
    private String content;
    private String answer;
    private String level;
    private String tag;
    private Long views;
    private Long likes;
    private String attached;
}
