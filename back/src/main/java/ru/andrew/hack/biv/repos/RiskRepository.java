package ru.andrew.hack.biv.repos;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.andrew.hack.biv.model.Risk;

public interface RiskRepository extends MongoRepository<Risk, String> {
    Risk findByPath(String path);
    void deleteByPath(String path);
}
