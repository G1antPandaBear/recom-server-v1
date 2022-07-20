package com.pandabear.recom.global.config;

import com.pandabear.recom.domain.document.type.ExportType;
import org.springframework.core.convert.converter.Converter;

public class ExportTypeConverter implements Converter<String, ExportType> {

    @Override
    public ExportType convert(String source) {
        return ExportType.valueOf(source);
    }
}
