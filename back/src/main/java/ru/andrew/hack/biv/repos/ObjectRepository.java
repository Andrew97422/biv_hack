package ru.andrew.hack.biv.repos;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.andrew.hack.biv.model.ObjectDoc;

public interface ObjectRepository extends MongoRepository<ObjectDoc, String> {
    ObjectDoc findByPath(String path);
    void deleteByPath(String path);
}
