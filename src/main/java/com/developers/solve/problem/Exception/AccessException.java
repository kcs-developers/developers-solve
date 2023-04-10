package com.developers.solve.problem.Exception;

public class AccessException extends CommonBusineesException{
    public AccessException(){
        super(ErrorEnum.FORBIDDEN_ACCESS);
    }
}
