package dev.projects.sspSoft.javaCore.spring.service;

import org.springframework.stereotype.Service;
import pl.allegro.finance.tradukisto.ValueConverters;

@Service
public class NumberTransformationService {
    public String transformNumber(String number) {
        ValueConverters converter = ValueConverters.RUSSIAN_INTEGER;

        return converter.asWords(Integer.parseInt(number));
    }
}
