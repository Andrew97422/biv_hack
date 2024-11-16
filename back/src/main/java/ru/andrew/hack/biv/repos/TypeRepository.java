package ru.andrew.hack.biv.repos;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.andrew.hack.biv.model.Type;

@Repository
public interface TypeRepository extends MongoRepository<Type, String> {
    Type findByPath(String path);
    void deleteByPath(String path);
}
