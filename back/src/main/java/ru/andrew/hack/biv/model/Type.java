package ru.andrew.hack.biv.model;

import lombok.Data;
import org.json.JSONObject;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "types")
public class Type {
    @Id
    private String id;
    private String name;
    private String description;
    private JSONObject parameters;
    @Indexed
    private String path;
}