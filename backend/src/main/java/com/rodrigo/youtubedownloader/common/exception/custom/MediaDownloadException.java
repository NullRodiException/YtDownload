package com.rodrigo.youtubedownloader.common.exception.custom;

import org.springframework.http.HttpStatus;

public class MediaDownloadException extends AppBaseException{
    public MediaDownloadException(String message) {
        super(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
