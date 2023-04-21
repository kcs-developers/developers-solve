package com.developers.solve.problem.responseDTO;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ProblemSortResponseDTO {

    private Long problemId;
    private String type;
    private String writer;
    private String title;
    private String content;
    private String answer;
    private String level;
    private Long views;
    private Long likes;
    private LocalDateTime createdTime;
    private List<String> hashTag;
    private List<String> answerCandidate;
    private String pathname;

    }
