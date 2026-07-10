package com.doryann.flowpilot.mapper;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import org.springframework.stereotype.Component;

@Component
public class DateTimeMapper {

    public OffsetDateTime map(LocalDateTime value) {
        if (value == null) {
            return null;
        }

        return value.atOffset(ZoneOffset.UTC);
    }

    public LocalDateTime map(OffsetDateTime value) {
        if (value == null) {
            return null;
        }

        return value.toLocalDateTime();
    }
}