package com.example.scheduler.repository;

import com.example.scheduler.model.Appointment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.List;

public interface AppointmentRepository extends MongoRepository<Appointment, String> {
    List<Appointment> findByOperatorNameAndDate(String operatorName, LocalDate date);
}
