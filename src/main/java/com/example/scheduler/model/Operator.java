package com.example.scheduler.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@NoArgsConstructor
public class Operator {
    @Id
    private String id;
    private String name;
    public Operator(String name) {
        this.name = name;
    }
}
