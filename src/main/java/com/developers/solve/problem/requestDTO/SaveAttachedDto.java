package com.developers.solve.problem.requestDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class SaveAttachedDto {
    @NotEmpty(message = "파일 업로드에 실패했습니다.")
    private List<String> pathname;
    @NotNull(message = "올바르지 않는 경로입니다.")
    private Long problemId;
}
