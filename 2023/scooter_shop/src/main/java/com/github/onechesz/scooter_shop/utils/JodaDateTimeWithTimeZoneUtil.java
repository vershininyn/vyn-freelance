package com.github.onechesz.scooter_shop.utils;

import org.joda.time.*;
import org.joda.time.format.DateTimeFormat;

public class JodaDateTimeWithTimeZoneUtil {
    private final DateTimeZone timeZone;

    private final String formatToString;

    public JodaDateTimeWithTimeZoneUtil(String timeZoneString, String formatToString) {
        this.timeZone = DateTimeZone.forID(timeZoneString);
        this.formatToString = formatToString;
    }

    public DateTime getDateTimeWithTimeZone() {
//        return LocalDateTime.now().toDateTime(timeZone);
        return LocalDateTime.now().toDateTime();
    }

    public Period computeRelativeToStartPeriod(DateTime startDateTime) {
        return getDuration(startDateTime).toPeriod();
    }

    public DateTime toDateTimeFromEpochMilli(long epochMilli) {
//        return new DateTime(epochMilli, timeZone);
        return new DateTime(epochMilli);
    }

    public Duration getDuration(DateTime startDateTime) {
        return new Duration(startDateTime, getDateTimeWithTimeZone());
    }

    public long getDurationAtHours(DateTime startDateTime) {
        return getDuration(startDateTime).toStandardHours().getHours();
    }

    public long getDurationAtMinutes(DateTime startDateTime) {
        return getDuration(startDateTime).toStandardMinutes().getMinutes();
    }

    public long getDurationAtSeconds(DateTime startDateTime) {
        return getDuration(startDateTime).toStandardSeconds().getSeconds();
    }

    public String formatDateTimeWithTimeZoneToString(DateTime dateTime) {
        return dateTime.toString(DateTimeFormat.forPattern(formatToString));
    }
}
