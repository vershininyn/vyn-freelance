package com.github.onechesz.scooter_shop.config;

import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.joda.cfg.JacksonJodaDateFormat;
import com.fasterxml.jackson.datatype.joda.cfg.JacksonJodaPeriodFormat;
import com.fasterxml.jackson.datatype.joda.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.joda.ser.DateTimeSerializer;
import com.fasterxml.jackson.datatype.joda.ser.PeriodSerializer;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.onechesz.scooter_shop.utils.JodaDateTimeWithTimeZoneUtil;
import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.joda.time.Period;
import org.joda.time.ReadablePeriod;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.math.BigDecimal;
import java.math.BigInteger;

@Configuration
public class JodaDateTimeWithTimeZoneConfiguration implements Jackson2ObjectMapperBuilderCustomizer, Ordered {
    @Value("${scooter.shop.timezone}")
    private String timeZoneString;

    @Value("${scooter.shop.jodaDateTimeFormat}")
    private String jodaDateTimeWithTimeZoneFormat;

    @Bean
    public JodaDateTimeWithTimeZoneUtil getDateTimeWithTimeZoneUtil() {
        return new JodaDateTimeWithTimeZoneUtil(timeZoneString, jodaDateTimeWithTimeZoneFormat);
    }

    @Override
    public void customize(Jackson2ObjectMapperBuilder builder) {
        // Set the format of serialization and deserialization of the java.util.Date.
        builder.simpleDateFormat(jodaDateTimeWithTimeZoneFormat);

        // JSR 310 Date Time Processing
        JavaTimeModule javaTimeModule = new JavaTimeModule();

        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(jodaDateTimeWithTimeZoneFormat);
        JacksonJodaDateFormat jodaDateTimeFormatter = new JacksonJodaDateFormat(dateTimeFormatter);

        javaTimeModule.addSerializer(DateTime.class, new DateTimeSerializer(jodaDateTimeFormatter));
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(jodaDateTimeFormatter));

        builder.modules(javaTimeModule);

        // global configuration for serializing Long types to String, which solves the problem of lost precision of JSs numeric types in the browser client.
        builder.serializerByType(BigInteger.class, ToStringSerializer.instance);
        builder.serializerByType(BigDecimal.class, ToStringSerializer.instance);
        builder.serializerByType(Long.class, ToStringSerializer.instance);
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
