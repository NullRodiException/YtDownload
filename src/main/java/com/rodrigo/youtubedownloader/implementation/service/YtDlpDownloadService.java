package com.rodrigo.youtubedownloader.implementation.service;

import com.rodrigo.youtubedownloader.common.exception.custom.MediaDownloadException;
import com.wonkglorg.ytdlp.YtDlp;
import com.wonkglorg.ytdlp.YtDlpRequest;
import com.wonkglorg.ytdlp.YtDlpResponse;
import com.wonkglorg.ytdlp.exception.YtDlpException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class YtDlpDownloadService {

    public YtDlpResponse download(String url) {
        try {
            String tempDir = System.getProperty("java.io.tmpdir");

            YtDlpRequest request = new YtDlpRequest(url, tempDir);
            request.setOption("ignore-errors");		// --ignore-errors
            request.setOption("output", "%(id)s");	// --output "%(id)s"
            request.setOption("retries", 10);

            log.info("Executing yt-dlp request: {}", request);
            return YtDlp.execute(request);
        } catch (YtDlpException e) {
            throw new MediaDownloadException("Failed to download video: " + e.getMessage());
        }
    }
}
