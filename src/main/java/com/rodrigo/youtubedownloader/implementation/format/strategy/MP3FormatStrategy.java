package com.rodrigo.youtubedownloader.implementation.format.strategy;

import com.rodrigo.youtubedownloader.common.enums.FormatType;
import com.rodrigo.youtubedownloader.implementation.format.FormatStrategy;
import com.wonkglorg.ytdlp.YtDlpRequest;
import org.springframework.stereotype.Component;

@Component
public class MP3FormatStrategy implements FormatStrategy {
    @Override
    public FormatType getFormatType() {
        return FormatType.MP3;
    }

    @Override
    public YtDlpRequest createRequest(String url, String tempDir) {
        YtDlpRequest request = new YtDlpRequest(url, tempDir);
        request.setOption("ignore-errors");
        request.setOption("output", "%(id)s.%(ext)s"); // id.mp4
        request.setOption("format", "b[ext=MP3]/b");
        request.setOption("retries", 10);

        return request;
    }
}
