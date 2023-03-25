package com.developers.solve.problem.dto;


import com.developers.solve.problem.entity.Problem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String tag;
    private Long views;
    private Long likes;

    }
