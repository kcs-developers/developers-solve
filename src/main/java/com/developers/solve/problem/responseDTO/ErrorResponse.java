package com.developers.solve.problem.responseDTO;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ErrorResponse {

    private String ErrorMessage;
    private String Detail;
    public ErrorResponse(String errorMessage, String detail) {
        this.ErrorMessage = errorMessage;
        this.Detail = detail;
    }

//    public ErrorResponse(String message, String detail)   {
//        this.Detail = detail;
//        this.ErrorMessage =  message;
//    }

//    public ErrorResponse(ErrorEnum errorEnum){
//        this.ErrorMessage=errorEnum.getMsg();
//    }

}
