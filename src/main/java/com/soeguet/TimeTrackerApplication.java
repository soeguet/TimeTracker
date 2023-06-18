package com.soeguet;

import com.soeguet.model.Employee;
import com.soeguet.model.StampTime;
import com.soeguet.service.HomeService;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TimeTrackerApplication implements CommandLineRunner {

  private final HomeService homeService;

  public TimeTrackerApplication(HomeService homeService) {
    this.homeService = homeService;
  }

  public static void main(String[] args) {
    SpringApplication.run(TimeTrackerApplication.class, args);
  }

  @Override
  public void run(String... args) {

    // some test data for the time being
    Employee employee1 = new Employee();
    employee1.setFirstname("Alex");
    employee1.setWorkingHours(Duration.ofHours(40));
    employee1.setStartOfEmployment(LocalDate.of(2020, 1, 1));
    employee1.setStartOfTimeTracking(LocalDate.now().minusDays(7));
    homeService.saveEmployee(employee1);

    Employee employee2 = new Employee();
    employee2.setFirstname("Berta");
    employee2.setWorkingHours(Duration.ofHours(35));
    employee2.setStartOfEmployment(LocalDate.of(2020, 1, 1));
    employee2.setStartOfTimeTracking(LocalDate.now().minusDays(7));
    homeService.saveEmployee(employee2);

    Employee employee3 = new Employee();
    employee3.setFirstname("Cecilia");
    employee3.setWorkingHours(Duration.ofHours(35));
    employee3.setStartOfEmployment(LocalDate.of(2020, 1, 1));
    employee3.setStartOfTimeTracking(LocalDate.now().minusDays(7));
    homeService.saveEmployee(employee3);

    Employee employee4 = new Employee();
    employee4.setFirstname("Damian");
    employee4.setWorkingHours(Duration.ofHours(40));
    employee4.setStartOfEmployment(LocalDate.of(2020, 1, 1));
    employee4.setStartOfTimeTracking(LocalDate.now().minusDays(7));
    homeService.saveEmployee(employee4);

    Employee employee5 = new Employee();
    employee5.setFirstname("Emil");
    employee5.setWorkingHours(Duration.ofHours(40));
    employee5.setStartOfEmployment(LocalDate.of(2020, 1, 1));
    employee5.setStartOfTimeTracking(LocalDate.now().minusDays(7));
    homeService.saveEmployee(employee5);

    Employee employee6 = new Employee();
    employee6.setFirstname("Franziska");
    employee6.setWorkingHours(Duration.ofHours(30));
    employee6.setStartOfEmployment(LocalDate.of(2020, 1, 1));
    employee6.setStartOfTimeTracking(LocalDate.now().minusDays(7));
    homeService.saveEmployee(employee6);

    Employee employee7 = new Employee();
    employee7.setFirstname("Gustaf");
    employee7.setWorkingHours(Duration.ofHours(10));
    employee7.setStartOfEmployment(LocalDate.of(2020, 1, 1));
    employee7.setStartOfTimeTracking(LocalDate.now().minusDays(7));
    homeService.saveEmployee(employee7);

    Employee emp1 = homeService.getEmployeeByFirstname(employee1.getFirstname());
    StampTime emp1StampTime1 = new StampTime();
    emp1StampTime1.setEmployeeId(emp1.getId());
    emp1StampTime1.setDate(LocalDate.now());
    emp1StampTime1.setTime(LocalTime.of(3, 10));
    homeService.saveStampTime(emp1StampTime1);

    StampTime emp1StampTime2 = new StampTime();
    emp1StampTime2.setEmployeeId(emp1.getId());
    emp1StampTime2.setDate(LocalDate.now());
    emp1StampTime2.setTime(LocalTime.of(5, 55));
    homeService.saveStampTime(emp1StampTime2);

    StampTime emp1StampTime3 = new StampTime();
    emp1StampTime3.setEmployeeId(emp1.getId());
    emp1StampTime3.setDate(LocalDate.now());
    emp1StampTime3.setTime(LocalTime.of(7, 40));
    homeService.saveStampTime(emp1StampTime3);

    StampTime emp1StampTime4 = new StampTime();
    emp1StampTime4.setEmployeeId(emp1.getId());
    emp1StampTime4.setDate(LocalDate.now());
    emp1StampTime4.setTime(LocalTime.of(11, 6));
    homeService.saveStampTime(emp1StampTime4);

    StampTime emp1StampTime7 = new StampTime();
    emp1StampTime7.setEmployeeId(emp1.getId());
    emp1StampTime7.setDate(LocalDate.now());
    emp1StampTime7.setTime(LocalTime.of(12, 18));
    homeService.saveStampTime(emp1StampTime7);

    StampTime emp1StampTime5 = new StampTime();
    emp1StampTime5.setEmployeeId(emp1.getId());
    emp1StampTime5.setDate(LocalDate.now().minusDays(1));
    emp1StampTime5.setTime(LocalTime.of(7, 22));
    homeService.saveStampTime(emp1StampTime5);

    StampTime emp1StampTime6 = new StampTime();
    emp1StampTime6.setEmployeeId(emp1.getId());
    emp1StampTime6.setDate(LocalDate.now().minusDays(1));
    emp1StampTime6.setTime(LocalTime.of(10, 5));
    homeService.saveStampTime(emp1StampTime6);

    StampTime emp1StampTime9 = new StampTime();
    emp1StampTime9.setEmployeeId(emp1.getId());
    emp1StampTime9.setDate(LocalDate.of(2023, 4, 27));
    emp1StampTime9.setTime(LocalTime.of(10, 0));
    homeService.saveStampTime(emp1StampTime9);

    StampTime emp1StampTime10 = new StampTime();
    emp1StampTime10.setEmployeeId(emp1.getId());
    emp1StampTime10.setDate(LocalDate.of(2023, 4, 27));
    emp1StampTime10.setTime(LocalTime.of(18, 35));
    homeService.saveStampTime(emp1StampTime10);
  }
}
