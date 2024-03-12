package com.konasl.tokengeneration.util;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    public static LocalDateTime getCurrentDateInUTC() {
        return LocalDateTime.now(ZoneOffset.UTC);
    }

    public static String getStringFromLocalDateTime(LocalDateTime time, String pattern) {
        return time.format(DateTimeFormatter.ofPattern(pattern));
    }

}
