package com.developers.solve.problem.Exception;

import com.developers.solve.problem.responseDTO.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalException{
    @ExceptionHandler(CommonBusineesException.class)
    protected ResponseEntity methodGlobalException(CommonBusineesException c){
        log.error("Bussines Exception",c);

        return ResponseEntity.status(c.getErrorEnum().getHttpStatus()).body(
                new ErrorResponse(c.getErrorEnum().name(), c.getErrorEnum().getMsg()));
    }
}
