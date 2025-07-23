package com.rodrigo.youtubedownloader.contract.mediaDownload.response;

import java.util.List;

public record MediaFormatResponse(
        List<String> formats
) {
}
