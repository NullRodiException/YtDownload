package com.rodrigo.youtubedownloader.common.exception;


import com.rodrigo.youtubedownloader.common.exception.custom.AppBaseException;
import com.rodrigo.youtubedownloader.contract.exception.ExceptionResponse;
import com.wonkglorg.ytdlp.exception.YtDlpException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AppBaseException.class)
    public ResponseEntity<ExceptionResponse> handleMediaDownloadException(AppBaseException ex){
        return ResponseEntity.status(ex.getHttpStatus())
                .body(new ExceptionResponse(ex.getHttpStatus().value(), ex.getErrors()));
    }

    @ExceptionHandler(YtDlpException.class)
    public ResponseEntity<ExceptionResponse> handleYtDlpException(YtDlpException ex){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ExceptionResponse(500, List.of("Internal server error", ex.getMessage())));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleGeneric(Exception ex){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ExceptionResponse(500, List.of("Internal server error", ex.getMessage())));
    }
}
