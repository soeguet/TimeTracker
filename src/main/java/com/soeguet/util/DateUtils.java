package com.soeguet.util;

import java.time.Duration;
import java.time.LocalDate;

public class DateUtils {
  public static boolean isFuture(LocalDate date) {
    return date != null && date.isAfter(LocalDate.now());
  }

  public static String convertDurationToString(Duration duration) {
    boolean neg = false;
    if (duration.isNegative()){
      neg = true;
      duration = duration.multipliedBy(-1);
    }
    long seconds = duration.getSeconds();
    int minutes = (int) seconds / 60;
    int hours = minutes / 60;

    if (neg){
      return String.format("-%02d:%02d", hours, minutes % 60);
    }
    return String.format("%02d:%02d", hours, minutes % 60);
  }
}
