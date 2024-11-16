package ru.andrew.hack.biv.repos;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.andrew.hack.biv.model.Product;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {}
