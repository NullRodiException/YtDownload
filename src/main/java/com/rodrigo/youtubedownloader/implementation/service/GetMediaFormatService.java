package com.rodrigo.youtubedownloader.implementation.service;

import com.rodrigo.youtubedownloader.common.enums.FormatType;
import com.rodrigo.youtubedownloader.contract.mediaDownload.response.MediaFormatResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class GetMediaFormatService {

    public ResponseEntity<MediaFormatResponse> listFormats(){
        List<String> formats = Arrays
                .stream(FormatType.values())
                .map(FormatType::name)
                .toList();

        MediaFormatResponse response = new MediaFormatResponse(formats);
        log.info("Available formats: {}", formats);
        return ResponseEntity.ok(response);
    }
}
