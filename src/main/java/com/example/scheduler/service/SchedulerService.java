package com.example.scheduler.service;

import com.example.scheduler.model.Appointment;
import com.example.scheduler.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class SchedulerService {
    @Autowired
    private AppointmentRepository appointmentRepository;

    public ResponseEntity<?> bookAppointment(@RequestParam String operatorName, @RequestParam String date, @RequestParam int startHour, @RequestParam int endHour) {
        if (endHour - startHour != 1) {
            //Only one hour appointments are taken
            return ResponseEntity.badRequest().body("Only one-hour appointments are allowed.");
        }

        LocalDate localDate = LocalDate.parse(date);
        List<Appointment> appointments = appointmentRepository.findByOperatorNameAndDate(operatorName, localDate);
        for (Appointment appointment : appointments) {
            if ((startHour < appointment.getEndHour() && endHour > appointment.getStartHour())) {
                //Handles the case if the slot is already booked
                return ResponseEntity.status(409).body("Operator is already booked for the requested time slot.");
            }
        }

        Appointment appointment = new Appointment(operatorName, localDate, startHour, endHour);
        return ResponseEntity.ok(appointmentRepository.save(appointment));
    }

    public ResponseEntity<?> rescheduleAppointment(@PathVariable String id, @RequestParam int newStartHour, @RequestParam int newEndHour) {
        if (newEndHour - newStartHour != 1) {
            return ResponseEntity.badRequest().body("Only one-hour appointments are allowed.");
        }

        Appointment appointment = appointmentRepository.findById(id).orElseThrow(() -> new RuntimeException("Appointment not found"));

        LocalDate localDate = LocalDate.parse(appointment.getDate().toString());
        List<Appointment> appointments = appointmentRepository.findByOperatorNameAndDate(appointment.getOperatorName(), localDate);
        for (Appointment existingAppointment : appointments) {
            if (!existingAppointment.getId().equals(id) && (newStartHour < existingAppointment.getEndHour() && newEndHour > existingAppointment.getStartHour())) {
                //Handles the case if the slot is already booked
                return ResponseEntity.status(409).body("Operator is already booked for the requested time slot.");
            }
        }

        appointment.setDate(localDate);
        appointment.setStartHour(newStartHour);
        appointment.setEndHour(newEndHour);
        return ResponseEntity.ok(appointmentRepository.save(appointment));
    }

    public ResponseEntity<?> cancelAppointment(@PathVariable String id) {
        appointmentRepository.deleteById(id);
        return ResponseEntity.ok("Appointment canceled successfully.");
    }

    public List<String> getAppointments(@PathVariable String operatorName, @RequestParam String date) {
        LocalDate localDate = LocalDate.parse(date);
        List<Appointment> appointments = appointmentRepository.findByOperatorNameAndDate(operatorName, localDate);

        List<String> filledSlots = new ArrayList<>();
        for (Appointment appointment : appointments) {
            filledSlots.add(appointment.getStartHour()+"-"+appointment.getEndHour());
        }
        return filledSlots;
    }

    public List<String> getOpenSlots(@PathVariable String operatorName, @RequestParam String date) {
        LocalDate localDate = LocalDate.parse(date);
        List<Appointment> appointments = appointmentRepository.findByOperatorNameAndDate(operatorName, localDate);

        boolean[] slots = new boolean[24];

        for (Appointment appointment : appointments) {
            slots[appointment.getStartHour()] = true;
        }

        List<String> openSlots = new ArrayList<>();
        int start = -1;
        for (int i = 0; i < 24; i++) {
            if (!slots[i] && start == -1) {
                start = i;
            } else if (slots[i] && start != -1) {
                openSlots.add(start + "-" + i);
                start = -1;
            }
        }
        if (start != -1) {
            openSlots.add(start + "-24");
        }

        return openSlots;
    }
}
