package ru.sejapoe.dz1.exception.handler;

import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.sejapoe.dz1.exception.BaseException;

@RestControllerAdvice
public class AppExceptionHandler {
    @ExceptionHandler(BaseException.class)
    public ProblemDetail handleBaseException(BaseException baseException) {
        return ProblemDetail.forStatusAndDetail(baseException.getHttpStatus(), baseException.getMessage());
    }
}