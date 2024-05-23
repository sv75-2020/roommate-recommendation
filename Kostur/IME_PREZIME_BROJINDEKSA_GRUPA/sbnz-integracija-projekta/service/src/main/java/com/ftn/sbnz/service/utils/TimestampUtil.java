package com.ftn.sbnz.service.utils;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

public class TimestampUtil {
    public static String formatTimestamp(Instant instant) {
        Date timestamp = Date.from(instant);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(timestamp);
    }
}
