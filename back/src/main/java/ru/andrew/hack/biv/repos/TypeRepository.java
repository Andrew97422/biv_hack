package ru.andrew.hack.biv.repos;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.andrew.hack.biv.model.Type;

public interface TypeRepository extends MongoRepository<Type, String> {
    Type findByPath(String path);
    void deleteByPath(String path);
    boolean existsByPath(String path);
}
