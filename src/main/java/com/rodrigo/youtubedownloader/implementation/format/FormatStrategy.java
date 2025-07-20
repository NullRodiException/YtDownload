package com.rodrigo.youtubedownloader.implementation.format;

import com.rodrigo.youtubedownloader.common.enums.FormatType;
import com.wonkglorg.ytdlp.YtDlpRequest;
import org.springframework.http.MediaType;

public interface FormatStrategy {
    FormatType getFormatType();
    YtDlpRequest createRequest(String url, String tempDir);
    MediaType getMediaType();
}
