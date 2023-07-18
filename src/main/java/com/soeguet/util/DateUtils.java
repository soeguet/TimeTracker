package com.soeguet.util;

import java.time.LocalDate;

public class DateUtils {
  public static boolean isFuture(LocalDate date) {
    return date != null && date.isAfter(LocalDate.now());
  }
}
