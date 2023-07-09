package com.soeguet.service;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.soeguet.model.Employee;
import com.soeguet.model.StampTime;
import com.soeguet.model.WorkDay;
import com.soeguet.model.dto.AddStampTimeDTO;
import com.soeguet.model.dto.AllStampTimeDTO;
import com.soeguet.model.dto.EditEmployeeDTO;
import com.soeguet.model.dto.IndexPageDTO;
import com.soeguet.model.dto.StampTimeDTO;
import com.soeguet.repository.EmployeeRepository;
import com.soeguet.repository.StampTimeRepository;

@Service
public class HomeService {
    // if someone works for 6+ hours -> 30 Minutes break needed. Overtime buffer of
    // 25 Minutes added -> 360 + 25
    private final Duration thirtyMinutesBreakNeeded = Duration.ofMinutes(385);
    // if someone works for 6+ hours -> 30 Minutes break needed. Overtime buffer of
    // 25 Minutes added -> 540 + 25
    private final Duration fortyfiveMinutesBreakNeeded = Duration.ofMinutes(565);
    private final EmployeeRepository employeeRepository;
    private final StampTimeRepository stampTimeRepository;

    public HomeService(
            EmployeeRepository employeeRepository, StampTimeRepository stampTimeRepository) {
        this.employeeRepository = employeeRepository;
        this.stampTimeRepository = stampTimeRepository;
    }

    public void saveEmployee(Employee employee) {
        employeeRepository.save(employee);
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee getEmployeeByFirstname(String firstname) {
        return employeeRepository.findByFirstname(firstname).orElseThrow();
    }

    public void saveStampTime(StampTime stampTime) {
        stampTimeRepository.save(stampTime);
    }

    public List<IndexPageDTO> getEmployeeDTOForHomePage() {
        List<Employee> employeeList = employeeRepository.findAll();
        List<IndexPageDTO> indexPageDTOList = new ArrayList<>();

        employeeList.forEach(
                employee -> {
                    List<StampTime> stampTimeList = stampTimeRepository.findAllByEmployeeId(employee.getId());

                    Duration overTime = fetchInitialDurationByEmployee(employee);

                    if (stampTimeList.isEmpty()) {
                        String overTimeAsString = getTimeAsString(overTime);
                        String hoursPerWeekAsString = getTimeAsString(employee.getWorkingHours());
                        IndexPageDTO employeeIndex = new IndexPageDTO(
                                String.valueOf(employee.getId()),
                                employee.getFirstname(),
                                employee.getStartOfEmployment().toString(),
                                employee.getStartOfTimeTracking().toString(),
                                hoursPerWeekAsString,
                                overTimeAsString,
                                "logged out");
                        indexPageDTOList.add(employeeIndex);
                        return;
                    }

                    Map<LocalDate, List<StampTime>> groupedByDate = stampTimeList.stream()
                            .collect(Collectors.groupingBy(StampTime::getDate));

                    for (Map.Entry<LocalDate, List<StampTime>> entry : groupedByDate.entrySet()) {
                        List<StampTime> stampTimesOnSameDate = entry.getValue();
                        for (int i = 0; i < stampTimesOnSameDate.size(); i += 2) {

                            Duration duration;
                            if (i + 1 <= stampTimesOnSameDate.size() - 1) {
                                // regular -> 2 times subtraction
                                duration = Duration.between(
                                        stampTimesOnSameDate.get(i).getTime(),
                                        stampTimesOnSameDate.get(i + 1).getTime());

                                if (groupedByDate.entrySet().size() < 4) {
                                    duration = subtractBreakIfNeeded(duration);
                                }

                            } else {
                                // irregular -> working right now OR forgot to log out
                                duration = checkIfSingleStampTimeIsBeforeOrAfter1830(stampTimesOnSameDate.get(i));

                                if (groupedByDate.entrySet().size() < 3) {
                                    duration = subtractBreakIfNeeded(duration);
                                }
                            }
                            overTime = overTime.plus(duration);
                        }
                    }

                    String overTimeAsString = getTimeAsString(overTime);
                    String hoursPerWeekAsString = getTimeAsString(employee.getWorkingHours());
                    String workingStatus = getWorkingStatusOfEmployee(groupedByDate.get(LocalDate.now()));
                    IndexPageDTO employeeIndex = new IndexPageDTO(
                            String.valueOf(employee.getId()),
                            employee.getFirstname(),
                            employee.getStartOfEmployment().toString(),
                            employee.getStartOfTimeTracking().toString(),
                            hoursPerWeekAsString,
                            overTimeAsString,
                            workingStatus);
                    indexPageDTOList.add(employeeIndex);
                });

        return indexPageDTOList;
    }

    private Duration subtractBreakIfNeeded(Duration duration) {
        // subtract the necessary break, if stamp times are less than 3 on one day
        if (duration.compareTo(fortyfiveMinutesBreakNeeded) > 0) {
            duration = duration.minusMinutes(45);
        } else if (duration.compareTo(thirtyMinutesBreakNeeded) > 0) {
            duration = duration.minusMinutes(30);
        }
        return duration;
    }

    private Duration fetchInitialDurationByEmployee(Employee employee) {

        int workingDaysSinceStartOfRecording = getWorkingDaysSinceStartOfRecording(LocalDate.now(),
                employee.getStartOfTimeTracking());

        return employee
                .getWorkingHours()
                .dividedBy(5)
                .multipliedBy(workingDaysSinceStartOfRecording)
                .negated();
    }

    private int getWorkingDaysSinceStartOfRecording(LocalDate today, LocalDate startOfEmployment) {
        int workDays = 0;
        LocalDate date = startOfEmployment;

        if (date == null) {
            return 0;
        }

        while (date.isBefore(today) || date.isEqual(today)) {
            DayOfWeek dayOfWeek = date.getDayOfWeek();

            if (dayOfWeek != DayOfWeek.SATURDAY && dayOfWeek != DayOfWeek.SUNDAY) {
                workDays++;
            }

            date = date.plusDays(1);
        }

        return workDays;
    }

    private String getWorkingStatusOfEmployee(List<StampTime> stampTimes) {
        if (stampTimes == null)
            return "logged out";

        return stampTimes.size() % 2 != 0 ? "working" : "logged out";
    }

    private String getTimeAsString(Duration overTime) {

        if (!overTime.isNegative()) {

            long secondsThisMonth = overTime.getSeconds();
            long HH = secondsThisMonth / 3600;
            long MM = (secondsThisMonth % 3600) / 60;
            return String.format("%02d:%02d", HH, MM);
        } else {
            long secondsThisMonth = overTime.negated().getSeconds();
            long HH = secondsThisMonth / 3600;
            long MM = (secondsThisMonth % 3600) / 60;
            return String.format("-%02d:%02d", HH, MM);
        }
    }

    // if someone forgets to stamp out -> automatically enter the shift end as stamp
    // time
    private Duration checkIfSingleStampTimeIsBeforeOrAfter1830(StampTime stampTime) {

        LocalTime workFinish;
        if (stampTime.getDate().getDayOfWeek() == DayOfWeek.SATURDAY) {
            workFinish = LocalTime.of(15, 0);
        } else {
            workFinish = LocalTime.of(18, 30);
        }

        if (LocalTime.now().isBefore(workFinish)) {
            workFinish = LocalTime.now();
        }

        if (stampTime.getTime().isAfter(workFinish)) {
            return Duration.ZERO;
        }

        return Duration.between(stampTime.getTime(), workFinish);
    }

    public String getNameOfEmployee(UUID employeeId) {
        return employeeRepository.getReferenceById(employeeId).getFirstname();
    }

    public EditEmployeeDTO getEmployeeForEditPage(UUID employeeUuid) {
        Employee employee = employeeRepository.findById(employeeUuid).orElseThrow();
        return new EditEmployeeDTO(
                employee.getId(),
                employee.getFirstname(),
                getTimeAsString(employee.getWorkingHours()),
                employee.getStartOfEmployment().toString(),
                employee.getStartOfTimeTracking().toString());
    }

    public StampTimeDTO getStampTimeById(UUID stampTimeId) {
        StampTime stampTime = stampTimeRepository.getReferenceById(stampTimeId);
        return new StampTimeDTO(
                stampTime.getId(),
                String.valueOf(stampTime.getDate()),
                String.valueOf(stampTime.getTime()));
    }

    public UUID deleteStampTime(UUID stampTimeId) {
        StampTime stampTime = stampTimeRepository.getReferenceById(stampTimeId);
        stampTimeRepository.delete(stampTime);
        return stampTime.getEmployeeId();
    }

    public void deleteEmployee(UUID employeeId) {
        Employee employee = employeeRepository.getReferenceById(employeeId);
        employeeRepository.delete(employee);
    }

    public void editEmployee(UUID employeeUuid, EditEmployeeDTO employee) {

        Employee employeeToEdit = employeeRepository.getReferenceById(employeeUuid);
        employeeToEdit.setFirstname(employee.name());
        employeeToEdit.setWorkingHours(
                Duration.parse("PT" + employee.hoursPerWeek().replace(":", "H") + "M"));
        employeeToEdit.setStartOfTimeTracking(LocalDate.parse(employee.startOfTimeTracking()));
        employeeRepository.save(employeeToEdit);
    }

    public void editStampTime(UUID stampTimeId, StampTimeDTO stampTime) {
        StampTime stampTimeToEdit = stampTimeRepository.getReferenceById(stampTimeId);
        stampTimeToEdit.setDate(LocalDate.parse(stampTime.date()));
        stampTimeToEdit.setTime(LocalTime.parse(stampTime.time()));
        stampTimeRepository.save(stampTimeToEdit);
    }

    public String getEmployeeIdByStampTimeId(UUID stampTimeId) {
        return stampTimeRepository.getReferenceById(stampTimeId).getEmployeeId().toString();
    }

    public List<AllStampTimeDTO> getStampTimeOverview() {

        List<StampTime> stampTimes = stampTimeRepository.findAll();
        HashMap<UUID, String> employeeIdToName = new HashMap<>();
        employeeRepository
                .findAll()
                .forEach(employee -> employeeIdToName.put(employee.getId(), employee.getFirstname()));

        List<AllStampTimeDTO> allStampTimeDTOList = new ArrayList<>();

        for (StampTime stampTime : stampTimes) {
            allStampTimeDTOList.add(
                    new AllStampTimeDTO(
                            stampTime.getId(),
                            employeeIdToName.get(stampTime.getEmployeeId()),
                            stampTime.getDate().toString(),
                            stampTime.getTime().toString()));
        }

        return allStampTimeDTOList;
    }

    public void addEmployee(EditEmployeeDTO employee) {
        Employee newEmployee = new Employee();
        newEmployee.setFirstname(employee.name());
        newEmployee.setWorkingHours(Duration.parse("PT" + employee.hoursPerWeek().replace(":", "H") + "M"));
		newEmployee.setStartOfEmployment(LocalDate.parse(employee.startOfEmployment()));
		newEmployee.setStartOfTimeTracking(LocalDate.parse(employee.startOfEmployment()));
        employeeRepository.save(newEmployee);
    }

    public void addStampTime(AddStampTimeDTO stampTime) {

        Employee employee = employeeRepository.getEmployeeByFirstname(stampTime.employeeName()).orElseThrow();

        StampTime newStampTime = new StampTime();
        newStampTime.setDate(LocalDate.parse(stampTime.date()));
        newStampTime.setTime(LocalTime.parse(stampTime.time()));
        newStampTime.setEmployeeId(employee.getId());
        newStampTime.setWorkingHoursPerWeek(employee.getWorkingHours());
        stampTimeRepository.save(newStampTime);
    }

    // create service which returns a HashMap<LocalDate, WorkDay> for a given
    // employee
    public HashMap<LocalDate, WorkDay> getStampTimesOfEmployee(UUID employeeId) {
        List<StampTime> stampTimes = stampTimeRepository.findAllByEmployeeId(employeeId);
        HashMap<LocalDate, WorkDay> stampTimesByDate = new HashMap<>();

        for (StampTime stampTime : stampTimes) {
            if (stampTimesByDate.containsKey(stampTime.getDate())) {
                stampTimesByDate.get(stampTime.getDate()).addStampTime(stampTime);
            } else {
                WorkDay newWorkDay = new WorkDay();
                newWorkDay.addStampTime(stampTime);
                stampTimesByDate.put(stampTime.getDate(), newWorkDay);
            }
        }

        // sort reversed stampTimesByDate
        // also sort the list of stampTimes by time
        for (Map.Entry<LocalDate, WorkDay> entry : stampTimesByDate.entrySet()) {
            entry.getValue().getStampTimeList().sort(Comparator.comparing(StampTime::getTime));
        }

        return stampTimesByDate;
    }

    public UUID getEmployeeIdByName(String employeeName) {
        return employeeRepository.getEmployeeByFirstname(employeeName).orElseThrow().getId();
    }
}
