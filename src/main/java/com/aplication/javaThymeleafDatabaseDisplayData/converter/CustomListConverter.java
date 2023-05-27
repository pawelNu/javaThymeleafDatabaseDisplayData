package com.aplication.javaThymeleafDatabaseDisplayData.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CustomListConverter implements Converter<String, List<String>> {

    @Override
    public List<String> convert(String source) {
        if (StringUtils.hasText(source)) {
            // If the value is non-empty, return it as a single list item
            return Collections.singletonList(source);
        } else {
            // Otherwise, split the value and return a list of items
            return Arrays.asList(source.split(","));
        }
    }
}
