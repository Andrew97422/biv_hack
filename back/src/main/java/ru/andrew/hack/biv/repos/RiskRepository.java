package ru.andrew.hack.biv.repos;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.andrew.hack.biv.model.Risk;

@Repository
public interface RiskRepository extends MongoRepository<Risk, String> {
    Risk findByPath(String path);
    void deleteByPath(String path);
}
