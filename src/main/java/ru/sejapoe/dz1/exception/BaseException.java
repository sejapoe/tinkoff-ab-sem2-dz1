package ru.sejapoe.dz1.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
public class BaseException extends RuntimeException {
    private final HttpStatus httpStatus;
    private final String message;

    public BaseException(HttpStatus httpStatus, String message) {
        super(httpStatus.toString() + ": " + message);
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
