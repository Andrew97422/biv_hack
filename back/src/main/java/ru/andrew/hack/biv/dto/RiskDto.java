package ru.andrew.hack.biv.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONObject;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RiskDto {
    private String id;
    private String name;
    private String description;
    private JSONObject parameters;
}
