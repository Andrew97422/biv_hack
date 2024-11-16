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

    public Object post(String path, String body) throws JSONException {
        String[] ids = path.split("\\.");
        if (!productRepository.existsById(ids[0])) {
            return "";
        }
        JSONObject jsonObj = new JSONObject(body);
        log.info(jsonObj.toString());
        switch (ids.length) {
            case 1, 2 -> {
                var object = objectRepository.findByPath(path);
                if (object == null) {
                    log.info("null");
                    object = new ObjectDoc();
                } else {
                    log.info("not null");
                }
                object.setName(jsonObj.getString("name"));
                object.setDescription(jsonObj.getString("description"));
                object.setParameters(jsonObj.getString("parameters"));
                if (ids.length == 1) {
                    objectRepository.save(object);
                    object.setPath(path + "." + object.getId());
                    log.info(object.toString());
                    objectRepository.save(object);
                } else {
                    String id = object.getId();
                    if (object.getId() == null) {
                        return "";
                    }
                    object.setPath(path + "." + id);
                    objectRepository.save(object);
                    log.info(objectRepository.findById(id).toString());
                }

                return object.getId();
            }
            case 3 -> {
                var type = typeRepository.findByPath(path);
                if (type == null) {
                    type = new Type();
                }
                type.setName(jsonObj.getString("name"));
                type.setDescription(jsonObj.getString("description"));
                if (type.getPath() == null) {
                    var newType = new Type();
                    newType.setName(jsonObj.getString("name"));
                    newType.setDescription(jsonObj.getString("description"));
                    newType.setParameters(jsonObj.getString("parameters"));
                    typeRepository.save(newType);
                    newType.setPath(path + "." + newType.getId());
                    typeRepository.save(newType);
                    return newType.getId();
                } else {
                    type.setPath(path + "." + type.getId());
                }
                type.setParameters(jsonObj.getString("parameters"));
                typeRepository.save(type);
                return type.getId();
            }
            case 4 -> {
                var risk = riskRepository.findByPath(path);
                if (risk == null) {
                    risk = new ru.andrew.hack.biv.model.Risk();
                }
                risk.setName(jsonObj.getString("name"));
                risk.setDescription(jsonObj.getString("description"));
                if (risk.getPath() == null) {
                    var newRisk = new ru.andrew.hack.biv.model.Risk();
                    newRisk.setName(jsonObj.getString("name"));
                    newRisk.setDescription(jsonObj.getString("description"));
                    newRisk.setParameters(jsonObj.getString("parameters"));
                    riskRepository.save(newRisk);
                    newRisk.setPath(path + "." + newRisk.getId());
                    riskRepository.save(newRisk);
                    return newRisk.getId();
                }
                risk.setPath(path);
                risk.setParameters(jsonObj.getString("parameters"));
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
}
