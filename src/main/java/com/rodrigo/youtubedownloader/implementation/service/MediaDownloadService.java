package com.rodrigo.youtubedownloader.implementation.service;

import com.rodrigo.youtubedownloader.common.exception.custom.MediaNotFoundException;
import com.rodrigo.youtubedownloader.common.exception.custom.MediaProcessingException;
import com.rodrigo.youtubedownloader.common.utils.ExtractIdFromMedia;
import com.rodrigo.youtubedownloader.contract.mediaDownload.request.MediaDownloadRequest;
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

@Service
@Slf4j
@AllArgsConstructor
public class MediaDownloadService {
    private final YtDlpDownloadService ytDlpDownloadService;
    private final ExtractIdFromMedia extractIdFromMedia;

    public ResponseEntity<Resource> downloadMedia(MediaDownloadRequest payload){
        YtDlpResponse response = ytDlpDownloadService.download(payload.url());
        log.debug("YtDlp response: {}", response.getOut());

        String tempDir = System.getProperty("java.io.tmpdir");

        String videoId = extractIdFromMedia.output(response.getOut());
        if (videoId == null) {
            log.error("Could not extract video ID from yt-dlp output");
            throw new MediaProcessingException("Could not extract video ID from download output");
        }

        String[] extensions = {".mp4", ".webm", ".mkv", ".avi", ".mov"};
        File file = null;

        for (String ext : extensions) {
            Path filePath = Paths.get(tempDir, videoId + ext);
            File candidate = filePath.toFile();
            if (candidate.exists()) {
                file = candidate;
                break;
            }
        }

        if (file == null || !file.exists()) {
            log.warn("Downloaded file not found for video ID: {} in directory: {}", videoId, tempDir);
            throw new MediaNotFoundException("Downloaded file not found for video ID: " + videoId);
        }

        log.info("Serving file: {}", file.getAbsolutePath());

        Resource resource = new FileSystemResource(file);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName());
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}
