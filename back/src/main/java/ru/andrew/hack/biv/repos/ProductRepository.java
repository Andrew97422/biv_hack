package ru.andrew.hack.biv.repos;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.andrew.hack.biv.model.Product;


public interface ProductRepository extends MongoRepository<Product, String> {}
