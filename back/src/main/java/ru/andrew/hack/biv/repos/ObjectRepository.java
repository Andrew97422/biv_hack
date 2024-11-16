package ru.andrew.hack.biv.repos;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.andrew.hack.biv.model.ObjectDoc;

@Repository
public interface ObjectRepository extends MongoRepository<ObjectDoc, String> {
    ObjectDoc findByPath(String path);
    void deleteByPath(String path);
}
