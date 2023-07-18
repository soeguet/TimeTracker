package com.soeguet.controller;

import com.soeguet.model.dto.*;
import com.soeguet.service.HomeService;
import com.soeguet.util.DateUtils;
import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {

  private final HomeService homeService;

  public HomeController(HomeService homeService) {
    this.homeService = homeService;
  }

  @GetMapping("/")
  public String getHome(Model model) {
    List<IndexPageDTO> listForIndexPage = homeService.getEmployeeDTOForHomePage();
    model.addAttribute("allEmployees", listForIndexPage);
    return "home/index";
  }

  @PostMapping("/delete-stamp-time/{id}")
  public String deleteStampTime(@PathVariable("id") UUID stampTimeId) {
    System.out.println("stampTimeId = " + stampTimeId);
    UUID employeeId = homeService.deleteStampTime(stampTimeId);
    return "redirect:/stamp-time/" + employeeId;
  }

  // Delete Employee with given id from post request and redirect to previous page
  @PostMapping("/delete-employee/{id}")
  public String deleteEmployee(@PathVariable("id") UUID employeeId) {
    homeService.deleteEmployee(employeeId);
    return "redirect:/";
  }

  // @Homepage
  @GetMapping("/edit-employee/{id}")
  public String editEmployee(
      @PathVariable("id") UUID employeeUuid, Model model, HttpServletRequest request) {
    previousPageReferer(model, request);
    EditEmployeeDTO employee = homeService.getEmployeeForEditPage(employeeUuid);
    model.addAttribute("employee", employee);
    return "home/edit-employee";
  }

  // Post Method for editing Employee with id
  @PostMapping("/edit-employee/{id}")
  public String editEmployee(@PathVariable("id") UUID employeeUuid, EditEmployeeDTO employee) {
    homeService.editEmployee(employeeUuid, employee);
    return "redirect:/";
  }

  // @Homepage
//  @GetMapping("/stamp-time/{id}")
//  public String showEmployeeWorkTimes(
//      @PathVariable("id") UUID employeeId, Model model, HttpServletRequest request) {
//    previousPageReferer(model, request);
//    model.addAttribute("employeeName", homeService.getNameOfEmployee(employeeId));
//    model.addAttribute("employeeId", employeeId);
//    model.addAttribute("allStampTimes", homeService.getStampTimesOfEmployee(employeeId));
//    return "home/employee-stamptimes";
//  }

  @GetMapping("/stamp-time/{id}")
  public String showEmployeeWorkTimes(
          @PathVariable("id") UUID employeeId, Model model, HttpServletRequest request) {
    previousPageReferer(model, request);
    //noinspection InstantiationOfUtilityClass
    model.addAttribute("dateUtils", new DateUtils());
    model.addAttribute("employeeOverview",homeService.getTestEmployeeOverview(employeeId));
    return "home/employee-stamptimes";
  }
  @GetMapping("/edit-stamp-time/{id}")
  public String editStampTime(
      @PathVariable("id") UUID stampTimeId, Model model, HttpServletRequest request) {
    previousPageReferer(model, request);
    StampTimeDTO stampTimeById = homeService.getStampTimeById(stampTimeId);
    model.addAttribute("stampTime", stampTimeById);
    return "home/edit-stamp-time";
  }

  // add stamp time for one employee with id and predefined date through path variable
  @GetMapping("/add-stamptime-employee/{id}/{date}")
  public String addStampTime(
      @PathVariable("id") UUID employeeId,
      @PathVariable("date") String date,
      Model model,
      HttpServletRequest request) {
    previousPageReferer(model, request);
    String employeeName = homeService.getNameOfEmployee(employeeId);
    model.addAttribute("employeeName", employeeName);
    model.addAttribute("employees", homeService.getAllEmployees());
    model.addAttribute(
        "stampTime",
        new AddStampTimeDTO(
            employeeName,
            date,
            LocalTime.now().withMinute(0).format(DateTimeFormatter.ofPattern("HH:mm"))));
    return "add/add-from-employee-overview-page";
  }

  @GetMapping("/add-stamptime-employee/{id}")
  public String addStampTimeForEmployee(
      @PathVariable("id") UUID employeeId, Model model, HttpServletRequest request) {
    previousPageReferer(model, request);
    String employeeName = homeService.getNameOfEmployee(employeeId);
    model.addAttribute("employeeName", employeeName);
    model.addAttribute("employees", homeService.getAllEmployees());
    model.addAttribute(
        "stampTime",
        new AddStampTimeDTO(
            employeeName,
            null,
            LocalTime.now().withMinute(0).format(DateTimeFormatter.ofPattern("HH:mm"))));
    return "add/add-from-employee-overview-page";
  }

  // Post Method for editing StampTime with id
  @PostMapping("/edit-stamp-time/{id}")
  public String editStampTime(@PathVariable("id") UUID stampTimeId, StampTimeDTO stampTime) {
    homeService.editStampTime(stampTimeId, stampTime);
    return "redirect:/stamp-time/" + homeService.getEmployeeIdByStampTimeId(stampTimeId);
  }

  private void previousPageReferer(Model model, HttpServletRequest request) {
    String referer = request.getHeader("referer");
    if (referer == null) referer = "/";
    model.addAttribute("previousPage", referer);
  }

  // @WorkTimes Page
  // all existing work times of all employees with AllStampTimeDTO
  @GetMapping("/show-all-work-time")
  public String allStampTimes(Model model) {
    List<AllStampTimeDTO> allStampTimes = homeService.getStampTimeOverview();
    model.addAttribute("allStampTimes", allStampTimes);
    return "overview/all-stamp-times";
  }

  @GetMapping("/edit-stamp-time-overview/{id}")
  public String editStampTimeOverview(@PathVariable("id") UUID stampTimeId, Model model) {
    StampTimeDTO stampTimeById = homeService.getStampTimeById(stampTimeId);
    model.addAttribute("stampTime", stampTimeById);
    return "overview/edit-stamp-time";
  }

  @PostMapping("/edit-stamp-time-overview/{id}")
  public String editStampTimeOverview(
      @PathVariable("id") UUID stampTimeId, StampTimeDTO stampTime) {
    homeService.editStampTime(stampTimeId, stampTime);
    return "redirect:/show-all-work-time";
  }

  // add stamptime for one employee without id
  @GetMapping("/add-stamp-time")
  public String addStampTime(Model model) {
    model.addAttribute("stampTime", new AddStampTimeDTO(null, null, null));
    model.addAttribute("employees", homeService.getAllEmployees());
    return "add/add-stamp-time-on-all-overview-page";
  }

  // Post Method for adding StampTime
  @PostMapping("/add-stamp-time")
  public String addStampTime(AddStampTimeDTO stampTime) {
    homeService.addStampTime(stampTime);
    return "redirect:/show-all-work-time";
  }

  @PostMapping("/add-stamp-time-employee")
  public String addStampTimeEmployee(AddStampTimeDTO stampTime) {
    homeService.addStampTime(stampTime);
    return "redirect:/stamp-time/" + homeService.getEmployeeIdByName(stampTime.employeeName());
  }
  // add employee without id
  @GetMapping("/add-employee")
  public String addEmployee(Model model) {
    model.addAttribute("employee", new EditEmployeeDTO(null, null, null, null, null));
    return "add/add-employee";
  }

  // Post Method for adding Employee
  @PostMapping("/add-employee")
  public String addEmployee(EditEmployeeDTO employee) {
    homeService.addEmployee(employee);
    return "redirect:/";
  }
}
