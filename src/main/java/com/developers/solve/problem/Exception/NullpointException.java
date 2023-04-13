package com.developers.solve.problem.Exception;

public class NullpointException extends CommonBusineesException {
    public NullpointException(){
        super(ErrorEnum.NOT_NULL);
    }
}
