package com.kai.check.utils;


import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 日期转换
 */
@Component
public class DataConverter implements Converter<String, LocalDateTime> {

    @Override
    public LocalDateTime convert(String s) {

        try {
            return LocalDateTime.parse(s, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
