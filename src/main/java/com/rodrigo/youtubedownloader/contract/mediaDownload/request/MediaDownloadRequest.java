package com.rodrigo.youtubedownloader.contract.mediaDownload.request;

import com.rodrigo.youtubedownloader.common.enums.FormatType;

public record MediaDownloadRequest(
        String url,
        FormatType format
) {
}
