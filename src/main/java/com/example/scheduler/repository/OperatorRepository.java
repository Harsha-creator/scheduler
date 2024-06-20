package com.example.scheduler.repository;

import com.example.scheduler.model.Operator;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OperatorRepository extends MongoRepository<Operator, String> {
}
