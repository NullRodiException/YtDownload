package com.rodrigo.youtubedownloader.implementation.service;

import com.rodrigo.youtubedownloader.common.enums.FormatType;
import com.rodrigo.youtubedownloader.common.exception.custom.MediaNotFoundException;
import com.rodrigo.youtubedownloader.common.exception.custom.MediaProcessingException;
import com.rodrigo.youtubedownloader.common.utils.ExtractIdFromMedia;
import com.rodrigo.youtubedownloader.contract.mediaDownload.request.MediaDownloadRequest;
import com.rodrigo.youtubedownloader.implementation.format.FormatStrategy;
import com.wonkglorg.ytdlp.YtDlpResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

@Service
@Slf4j
@AllArgsConstructor
public class MediaDownloadService {
    private final YtDlpDownloadService ytDlpDownloadService;
    private final ExtractIdFromMedia extractIdFromMedia;
    private final Map<FormatType, FormatStrategy> discoveryFormat;

    public ResponseEntity<Resource> downloadMedia(MediaDownloadRequest payload){
        YtDlpResponse response = ytDlpDownloadService.download(payload.url(), payload.format());
        log.debug("YtDlp response: {}", response.getOut());

        String tempDir = System.getProperty("java.io.tmpdir");

        String videoId = extractIdFromMedia.output(response.getOut());
        log.info("Video ID extracted from yt-dlp output: {}", videoId);
        if (videoId == null) {
            log.error("Could not extract video ID from yt-dlp output");
            throw new MediaProcessingException("Could not extract video ID from download output");
        }

        Path filePath = Paths.get(tempDir, videoId + "." + payload.format().name().toLowerCase());
        File file = filePath.toFile();

        if (!file.exists()) {
            log.warn("Downloaded file not found for video ID: {} in directory: {}", videoId, tempDir);
            throw new MediaNotFoundException("Downloaded file not found for video ID: " + videoId);
        }

        log.info("Serving file: {}", file.getAbsolutePath());
        MediaType mediaType = discoveryFormat.get(payload.format()).getMediaType();
        Resource resource = new FileSystemResource(file);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName());
        headers.add(HttpHeaders.CONTENT_TYPE, mediaType.toString());

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(mediaType)
                .body(resource);
    }
}
