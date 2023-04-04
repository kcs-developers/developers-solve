package com.developers.solve.problem.responseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ProblemDetailDto { // view, likes, tag추가...
        private Long problemId;
        private String type;
        private String writer;
        private String title;
        private String content;
        private String answer;
        private String level;
        private String hashTag;
        private Long views;
        private Long likes;
        private Boolean solved;
    }

