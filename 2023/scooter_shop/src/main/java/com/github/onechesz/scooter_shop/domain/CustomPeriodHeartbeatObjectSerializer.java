package com.github.onechesz.scooter_shop.domain;

import com.fasterxml.jackson.datatype.joda.cfg.JacksonJodaPeriodFormat;
import com.fasterxml.jackson.datatype.joda.ser.PeriodSerializer;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;
import org.springframework.stereotype.Component;

@Component
public class CustomPeriodHeartbeatObjectSerializer extends PeriodSerializer {
    private static final String SEPARATOR = ":";

    private static final PeriodFormatter
            format = new PeriodFormatterBuilder()
                            .printZeroAlways()
                            .minimumPrintedDigits(2) // gives the '01'
                            .appendHours()
                            .appendSeparator(SEPARATOR)
                            .appendMinutes()
                            .appendSeparator(SEPARATOR)
                            .appendSeconds()
                            .toFormatter();

    private static final JacksonJodaPeriodFormat jodaPeriodFormat = new JacksonJodaPeriodFormat(format);

    public CustomPeriodHeartbeatObjectSerializer() {
        super(jodaPeriodFormat);
    }
}
