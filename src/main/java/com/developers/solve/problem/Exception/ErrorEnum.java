package com.developers.solve.problem.Exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@RequiredArgsConstructor
public enum ErrorEnum {
    NOT_Validate("유효성 검증에 실패했습니다.",HttpStatus.BAD_REQUEST),
    NOT_NULL("해당하는 문제가 존재하지 않습니다.",HttpStatus.BAD_REQUEST),
    FORBIDDEN_ACCESS("해당 문제에 접근 권한이 없습니다.",HttpStatus.BAD_REQUEST);

    private final String msg;
    private final  HttpStatus httpStatus;
}
