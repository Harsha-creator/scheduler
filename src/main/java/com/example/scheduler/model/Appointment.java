package com.example.scheduler.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document
@Data
@NoArgsConstructor
public class Appointment {
    @Id
    private String id;
    private String operatorName;
    private LocalDate date;
    private int startHour;
    private int endHour;

    public Appointment(String operatorName, LocalDate date, int startHour, int endHour) {
        this.operatorName = operatorName;
        this.date = date;
        this.startHour = startHour;
        this.endHour = endHour;
    }
}
