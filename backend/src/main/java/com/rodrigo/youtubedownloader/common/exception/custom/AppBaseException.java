package com.rodrigo.youtubedownloader.common.exception.custom;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Getter
public class AppBaseException extends RuntimeException {
    private final List<String> errors;
    private final HttpStatus httpStatus;

    public AppBaseException(List<String> errors, HttpStatus httpStatus) {
        super(errors.getFirst());
        this.errors = errors;
        this.httpStatus = httpStatus;
    }

    public AppBaseException(String message, HttpStatus httpStatus) {
        super(message);
        this.errors = List.of(message);
        this.httpStatus = httpStatus;
    }
}
