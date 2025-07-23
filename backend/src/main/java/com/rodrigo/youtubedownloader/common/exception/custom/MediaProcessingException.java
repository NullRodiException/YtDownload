package com.rodrigo.youtubedownloader.common.exception.custom;

import org.springframework.http.HttpStatus;

public class MediaProcessingException extends AppBaseException {
    public MediaProcessingException(String error) {
        super(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
