package com.rodrigo.youtubedownloader.common.utils;

import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ExtractIdFromMedia {
    public String output(String output) {
        Pattern pattern = Pattern.compile("\\[youtube\\] ([a-zA-Z0-9_-]+):");
        Matcher matcher = pattern.matcher(output);

        if (matcher.find()) {
            return matcher.group(1);
        }

        Pattern downloadPattern = Pattern.compile("\\[download\\] ([a-zA-Z0-9_-]+)\\.[a-zA-Z0-9]+ has already been downloaded");
        Matcher downloadMatcher = downloadPattern.matcher(output);

        if (downloadMatcher.find()) {
            return downloadMatcher.group(1);
        }

        return null;
    }
}
