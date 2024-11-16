package ru.andrew.hack.biv.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "types")
public class Type {
    @Id
    private String id;
    private String name;
    private String description;
    @JsonProperty("parameters")
    private String parameters;
    @Indexed
    private String path;
}