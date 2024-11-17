package ru.andrew.hack.biv.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import ru.andrew.hack.biv.model.ObjectDoc;
import ru.andrew.hack.biv.model.Risk;
import ru.andrew.hack.biv.model.Type;
import ru.andrew.hack.biv.repos.ObjectRepository;
import ru.andrew.hack.biv.repos.ProductRepository;
import ru.andrew.hack.biv.repos.RiskRepository;
import ru.andrew.hack.biv.repos.TypeRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommonService {

    private final ObjectRepository objectRepository;
    private final RiskRepository riskRepository;
    private final TypeRepository typeRepository;
    private final ProductRepository productRepository;

    public Object get(String path) throws JSONException {
        String[] ids = path.split("\\.");
        if (!productRepository.existsById(ids[0])) {
            return "";
        }
        switch (ids.length) {
            case 2 -> {
                var objectDoc = objectRepository.findByPath(path);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", objectDoc.getId());
                jsonObject.put("name", objectDoc.getName());
                jsonObject.put("description", objectDoc.getDescription());
                jsonObject.put("path", objectDoc.getPath());
                jsonObject.put("parameters", objectDoc.getParameters());
                return jsonObject.toString();
            }
            case 3 -> {
                var type = typeRepository.findByPath(path);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", type.getId());
                jsonObject.put("name", type.getName());
                jsonObject.put("description", type.getDescription());
                jsonObject.put("path", type.getPath());
                jsonObject.put("parameters", type.getParameters());

                return type.toString();
            }
            case 4 -> {
                var risk = riskRepository.findByPath(path);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", risk.getId());
                jsonObject.put("name", risk.getName());
                jsonObject.put("description", risk.getDescription());
                jsonObject.put("path", risk.getPath());
                jsonObject.put("parameters", risk.getParameters());

                return risk.toString();
            }
            default -> {
                return "";
            }
        }
    }

    public Object post(String path, String body) throws JSONException, IllegalArgumentException {
        String[] ids = path.split("\\.");
        if (!productRepository.existsById(ids[0])) {
            return "";
        }
        JSONObject jsonObj = new JSONObject(body);
        log.info(jsonObj.toString());
        switch (ids.length) {
            case 1 -> {
                var object = new ObjectDoc();
                object.setName(jsonObj.getString("name"));
                object.setDescription(jsonObj.getString("description"));
                object.setParameters(jsonObj.getString("parameters"));
                objectRepository.save(object);
                object.setPath(path + "." + object.getId());
                objectRepository.save(object);
                return object.getId();
            }
            case 2 -> {
                if (!objectRepository.existsById(ids[1])) {
                    throw new IllegalArgumentException();
                }
                var type = new Type();
                type.setName(jsonObj.getString("name"));
                type.setDescription(jsonObj.getString("description"));
                type.setParameters(jsonObj.getString("parameters"));
                typeRepository.save(type);
                String id = type.getId();
                type.setPath(path + "." + id);
                typeRepository.save(type);
                return type.getId();
            }
            case 3 -> {
                if (!typeRepository.existsById(ids[2])) {
                    throw new IllegalArgumentException();
                }
                var risk = new Risk();
                risk.setName(jsonObj.getString("name"));
                risk.setDescription(jsonObj.getString("description"));
                risk.setParameters(jsonObj.getString("parameters"));
                riskRepository.save(risk);
                String id = risk.getId();
                risk.setPath(path + "." + id);
                riskRepository.save(risk);
                return risk.getId();
            }
            default -> {
                return "";
            }
        }
    }

    public String delete(String path) {
        String[] ids = path.split("\\.");
        if (!productRepository.existsById(ids[0])) {
            return "";
        }
        switch (ids.length) {
            case 2 -> {
                objectRepository.deleteByPath(path);
                return "ok";
            }
            case 3 -> {
                typeRepository.deleteByPath(path);
                return "ok";
            }
            case 4 -> {
                riskRepository.deleteByPath(path);
                return "ok";
            }
            default -> {
                return "";
            }
        }
    }

    public Object getAll() {
        List<ObjectDoc> objects = objectRepository.findAll();
        List<Type> types = typeRepository.findAll();
        List<Risk> risks = riskRepository.findAll();
        var result = new ArrayList<>();
        objects.forEach(obj-> {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("id", obj.getId());
                jsonObject.put("name", obj.getName());
                jsonObject.put("description", obj.getDescription());
                jsonObject.put("path", obj.getPath());
                jsonObject.put("parameters", obj.getParameters());
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            result.add(jsonObject);
        });
        types.forEach(obj-> {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("id", obj.getId());
                jsonObject.put("name", obj.getName());
                jsonObject.put("description", obj.getDescription());
                jsonObject.put("path", obj.getPath());
                jsonObject.put("parameters", obj.getParameters());
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            result.add(jsonObject);
        });
        risks.forEach(obj-> {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("id", obj.getId());
                jsonObject.put("name", obj.getName());
                jsonObject.put("description", obj.getDescription());
                jsonObject.put("path", obj.getPath());
                jsonObject.put("parameters", obj.getParameters());
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            result.add(jsonObject);
        });

        return result;
    }

    public String deleteAll() {
        objectRepository.deleteAll();
        typeRepository.deleteAll();
        riskRepository.deleteAll();
        return "ok";
    }

    public String put(String path, String body) throws JSONException {
        String[] ids = path.split("\\.");
        if (!productRepository.existsById(ids[0])) {
            return "";
        }
        JSONObject jsonObj = new JSONObject(body);
        log.info(jsonObj.toString());
        switch (ids.length) {
            case 2 -> {
                if (!objectRepository.existsByPath(path)) {
                    throw new IllegalArgumentException();
                }
                var objectDoc = objectRepository.findByPath(path);
                objectDoc.setName(jsonObj.getString("name"));
                objectDoc.setDescription(jsonObj.getString("description"));
                objectDoc.setParameters(jsonObj.getString("parameters"));
                objectRepository.save(objectDoc);
                return "ok";
            }
            case 3 -> {
                if (!typeRepository.existsByPath(path)) {
                    throw new IllegalArgumentException();
                }
                var type = typeRepository.findByPath(path);
                type.setName(jsonObj.getString("name"));
                type.setDescription(jsonObj.getString("description"));
                type.setParameters(jsonObj.getString("parameters"));
                typeRepository.save(type);
                return "ok";
            }
            case 4 -> {
                if (!riskRepository.existsByPath(path)) {
                    throw new IllegalArgumentException();
                }
                var risk = riskRepository.findByPath(path);
                risk.setName(jsonObj.getString("name"));
                risk.setDescription(jsonObj.getString("description"));
                risk.setParameters(jsonObj.getString("parameters"));
                riskRepository.save(risk);
                return "ok";
            }
            default -> {
                return "";
            }
        }
    }
}
