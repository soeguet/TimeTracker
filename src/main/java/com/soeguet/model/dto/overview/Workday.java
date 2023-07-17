package com.soeguet.model.dto.overview;

import java.time.Duration;
import java.util.List;

public record Workday(String date,
                      boolean isWorking,
                      Duration worktimeTotal,
                      Duration breakTime,
                      Duration netWorktime,
                      Duration overtime,
                      Duration targetTime,
                      List<WorkdayEntry> workdayEntryList) {}
