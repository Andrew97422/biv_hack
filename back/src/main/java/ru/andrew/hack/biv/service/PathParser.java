package ru.andrew.hack.biv.service;

import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

public class PathParser {

    public static Optional<PathComponents> parsePath(String path) {
        if (path == null || path.isEmpty()) {
            return Optional.empty();
        }

        String[] parts = path.split("\\.");
        PathComponents components = new PathComponents();

        if (parts.length > 0) components.setProductId(parts[0]);
        if (parts.length > 1) components.setObjectId(parts[1]);
        if (parts.length > 2) components.setTypeId(parts[2]);
        if (parts.length > 3) components.setRiskId(parts[3]);

        return Optional.of(components);
    }

    @Setter
    @Getter
    public static class PathComponents {
        // getters and setters
        private String productId;
        private String objectId;
        private String typeId;
        private String riskId;

    }
}
