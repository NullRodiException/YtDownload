package com.rodrigo.youtubedownloader.implementation.format.strategy;

import com.rodrigo.youtubedownloader.common.enums.FormatType;
import com.rodrigo.youtubedownloader.implementation.format.FormatStrategy;
import com.wonkglorg.ytdlp.YtDlpRequest;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
public class MP4FormatStrategy implements FormatStrategy {
    @Override
    public FormatType getFormatType() {
        return FormatType.mp4;
    }

    @Override
    public YtDlpRequest createRequest(String url, String tempDir) {
        YtDlpRequest request = new YtDlpRequest(url, tempDir);
        request.setOption("format", "b[ext=mp4]/b");

        return request;
    }

    @Override
    public MediaType getMediaType() {
        return MediaType.valueOf("video/mp4");
    }
}
