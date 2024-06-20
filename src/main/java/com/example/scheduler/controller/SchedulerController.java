package com.example.scheduler.controller;

import com.example.scheduler.service.SchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/scheduler")
public class SchedulerController {

    @Autowired
    private SchedulerService schedulerService;

    @PostMapping("/book")
    public ResponseEntity<?> bookAppointment(@RequestParam String operatorName, @RequestParam String date, @RequestParam int startHour, @RequestParam int endHour) {
        return schedulerService.bookAppointment(operatorName, date, startHour, endHour);
    }

    @PutMapping("/reschedule/{id}")
    public ResponseEntity<?> rescheduleAppointment(@PathVariable String id, @RequestParam int newStartHour, @RequestParam int newEndHour) {
        return schedulerService.rescheduleAppointment(id, newStartHour, newEndHour);
    }

    @DeleteMapping("/cancel/{id}")
    public ResponseEntity<?> cancelAppointment(@PathVariable String id) {
        return schedulerService.cancelAppointment(id);
    }

    @GetMapping("/appointments/{operatorName}")
    public List<String> getAppointments(@PathVariable String operatorName, @RequestParam String date) {
        return schedulerService.getAppointments(operatorName, date);
    }

    @GetMapping("/open-slots/{operatorName}")
    public List<String> getOpenSlots(@PathVariable String operatorName, @RequestParam String date) {
        return  schedulerService.getOpenSlots(operatorName, date);
    }
}
