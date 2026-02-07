package com.tp.accessguard_web.model;

import com.tp.accessguard_web.model.enums.EventResult;
import jakarta.persistence.AttributeConverter;

import java.util.Locale;

public class EventResultConvert implements AttributeConverter<EventResult, String> {
    @Override
    public String convertToDatabaseColumn(EventResult attribute) {
        return attribute == null ? null : attribute.name().toLowerCase();
    }

    @Override
    public EventResult convertToEntityAttribute(String dbData) {
        return dbData == null ? null : EventResult.valueOf(dbData.toUpperCase());
    }
}
