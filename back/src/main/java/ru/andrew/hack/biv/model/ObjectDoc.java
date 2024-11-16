package ru.andrew.hack.biv.model;

import lombok.Data;
import org.json.JSONObject;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "objects")
public class ObjectDoc {
    @Id
    private String id;
    private String name;
    private String description;
    private JSONObject parameters;
    @Indexed
    private String path;
}
