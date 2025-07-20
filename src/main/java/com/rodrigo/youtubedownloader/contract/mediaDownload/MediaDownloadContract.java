package com.rodrigo.youtubedownloader.contract.mediaDownload;

import com.rodrigo.youtubedownloader.contract.mediaDownload.request.MediaDownloadRequest;
import com.rodrigo.youtubedownloader.implementation.service.MediaDownloadService;
import lombok.Builder;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/download")
@Builder
public class MediaDownloadContract {

    private final MediaDownloadService mediaDownloadService;

    //TODO: colocar o YtDLP nas Resources do Spring
    @PostMapping
    public ResponseEntity<Resource> downloadMedia(@RequestBody MediaDownloadRequest payload){
        return mediaDownloadService.downloadMedia(payload);
    }
}
