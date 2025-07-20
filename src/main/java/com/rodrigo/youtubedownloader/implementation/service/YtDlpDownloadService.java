package com.rodrigo.youtubedownloader.implementation.service;

import com.rodrigo.youtubedownloader.common.enums.FormatType;
import com.rodrigo.youtubedownloader.common.exception.custom.MediaDownloadException;
import com.rodrigo.youtubedownloader.implementation.format.FormatStrategy;
import com.wonkglorg.ytdlp.YtDlp;
import com.wonkglorg.ytdlp.YtDlpRequest;
import com.wonkglorg.ytdlp.YtDlpResponse;
import com.wonkglorg.ytdlp.exception.YtDlpException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
@AllArgsConstructor
public class YtDlpDownloadService {

    private final Map<FormatType, FormatStrategy> discoveryFormat;

    public YtDlpResponse download(String url, FormatType format) {
        try {
            String tempDir = System.getProperty("java.io.tmpdir");
            YtDlpRequest request = discoveryFormat.get(format).createRequest(url, tempDir);
            request.setOption("ignore-errors");
            request.setOption("output", "%(id)s.%(ext)s"); // id.mp4
            request.setOption("retries", 10);
            request.setOption("no-playlist");

            log.info("Executing yt-dlp request: {}", request);
            return YtDlp.execute(request);
        } catch (YtDlpException e) {
            throw new MediaDownloadException("Failed to download video: " + e.getMessage());
        }
    }
}
