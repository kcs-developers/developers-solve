package com.developers.solve.problem.Exception;

import com.developers.solve.problem.responseDTO.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Log4j2
@ControllerAdvice
public class ValidException {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> methodValidException(MethodArgumentNotValidException e, HttpServletRequest httpServletRequest) {
        log.warn("MethodArgumentNotValidException Error 발생 trace{},uri{}", e.getStackTrace(), httpServletRequest.getRequestURI());
        ErrorResponse errorResponse = makeErrorResponse(e.getBindingResult());
        return new ResponseEntity<ErrorResponse>(errorResponse,HttpStatus.BAD_REQUEST);
    }
    private ErrorResponse makeErrorResponse(BindingResult bindingResult){
        String msg = "";
        String detail = "";
        if(bindingResult.hasErrors()){
            detail = bindingResult.getFieldError().getDefaultMessage();
            msg = ErrorEnum.NOT_Validate.getMsg();

        }
        return new ErrorResponse(msg,detail);
    }
}
