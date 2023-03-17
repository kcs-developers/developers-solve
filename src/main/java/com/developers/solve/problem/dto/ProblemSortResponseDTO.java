package com.developers.solve.problem.dto;


import com.developers.solve.problem.entity.Level;
import com.developers.solve.problem.entity.Type;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ProblemSortResponseDTO {
    private Long problemId;
    private String title;
    private Type type;
    private String writer;
    private String content;
    private Level level;
    private Integer views;
    private Integer likes;

}
