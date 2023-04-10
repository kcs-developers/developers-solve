package com.developers.solve.problem.Exception;

import lombok.Getter;

@Getter
public class CommonBusineesException extends RuntimeException{
    private ErrorEnum errorEnum;
    public CommonBusineesException(ErrorEnum errorEnum){
        super(errorEnum.getMsg());
        this.errorEnum = errorEnum;
    }
}
