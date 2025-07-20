package com.rodrigo.youtubedownloader.implementation.format;

import com.rodrigo.youtubedownloader.common.enums.FormatType;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Configuration
@AllArgsConstructor
public class ConfigurationStrategy {

    List<FormatStrategy> strategies;

    @Bean
    public Map<FormatType, FormatStrategy> discoveryFormat() {
        Map<FormatType, FormatStrategy> formatsByType = new EnumMap<>(FormatType.class);
        strategies.forEach(formatStrategy -> formatsByType.put(formatStrategy.getFormatType(), formatStrategy));
        return formatsByType;
    }
}
