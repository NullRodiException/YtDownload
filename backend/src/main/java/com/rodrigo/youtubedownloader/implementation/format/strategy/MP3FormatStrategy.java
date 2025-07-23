package com.rodrigo.youtubedownloader.implementation.format.strategy;

import com.rodrigo.youtubedownloader.common.enums.FormatType;
import com.rodrigo.youtubedownloader.implementation.format.FormatStrategy;
import com.wonkglorg.ytdlp.YtDlpRequest;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
public class MP3FormatStrategy implements FormatStrategy {
    @Override
    public FormatType getFormatType() {
        return FormatType.mp3;
    }

    @Override
    public YtDlpRequest createRequest(String url, String tempDir) {
        YtDlpRequest request = new YtDlpRequest(url, tempDir);
        request.setOption("extract-audio");
        request.setOption("audio-format", "mp3");

        return request;
    }

    @Override
    public MediaType getMediaType(){
        return MediaType.valueOf("audio/mpeg");
    }
}
