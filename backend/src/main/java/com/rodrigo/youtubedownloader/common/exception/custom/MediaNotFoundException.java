package com.rodrigo.youtubedownloader.common.exception.custom;

import org.springframework.http.HttpStatus;

public class MediaNotFoundException extends AppBaseException {
    public MediaNotFoundException(String error) {
        super(error, HttpStatus.NOT_FOUND);
    }
}
