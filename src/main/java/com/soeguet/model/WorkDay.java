package com.soeguet.model;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class WorkDay {
  private final List<StampTime> stampTimeList;
  private String workTime;
  private Duration workTimeDuration;
  private Duration delta;

  public WorkDay() {
    stampTimeList = new ArrayList<>();
  }

  // ignore odd number of stamp times for now
  public String getWorkTime() {

    int validTimes = stampTimeList.size() / 2;
    Duration workTime = Duration.ZERO;

    for (int i = 0; i < validTimes; i++) {
      Duration duration =
          Duration.between(
              stampTimeList.get(i * 2).getTime(), stampTimeList.get(i * 2 + 1).getTime());
      workTime = workTime.plus(duration);
    }

    return getTimeAsString(workTime);
  }

  private String getTimeAsString(Duration overTime) {
    long secondsThisMonth = overTime.getSeconds();
    long HH = secondsThisMonth / 3600;
    long MM = (secondsThisMonth % 3600) / 60;
    return String.format("%02d:%02d", HH, MM);
  }

  public void addStampTime(StampTime stampTime) {
    stampTimeList.add(stampTime);
    workTime = getWorkTime();
  }

  public List<StampTime> getStampTimeList() {
    return stampTimeList;
  }
}
