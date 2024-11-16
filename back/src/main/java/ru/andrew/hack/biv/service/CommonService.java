package ru.andrew.hack.biv.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import ru.andrew.hack.biv.dto.ObjectDto;
import ru.andrew.hack.biv.dto.RiskDto;
import ru.andrew.hack.biv.dto.TypeDto;
import ru.andrew.hack.biv.model.ObjectDoc;
import ru.andrew.hack.biv.model.Risk;
import ru.andrew.hack.biv.model.Type;
import ru.andrew.hack.biv.repos.ObjectRepository;
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
    private final ObjectMapper mapper;

    public Object get(String path) throws JSONException {
        String[] ids = path.split("\\.");
        switch (ids.length) {
            case 2 -> {
                var objectDoc = objectRepository.findByPath(path);
                log.info(objectDoc.toString());
                //return mapper.convertValue(objectDoc.toString(), ObjectDto.class);
                //return objectDoc.toString();
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
                if (type == null) {
                    return "";
                }
                //return mapper.convertValue(type.toString(), TypeDto.class);
                return type.toString();
            }
            case 4 -> {
                var risk = riskRepository.findByPath(path);
                if (risk == null) {
                    return "";
                }
                //return mapper.convertValue(risk.toString(), RiskDto.class);
                return risk.toString();
            }
            default -> {
                return "";
            }
        }
    }

    public Object post(String path, String body) throws JSONException {
        String[] ids = path.split("\\.");
        JSONObject jsonObj = new JSONObject(body);
        log.info(jsonObj.toString());
        switch (ids.length) {
            case 1, 2 -> {
                var object = objectRepository.findByPath(path);
                if (object == null) {
                    object = new ObjectDoc();
                }
                object.setName(jsonObj.getString("name"));
                object.setDescription(jsonObj.getString("description"));
                jsonObj.remove("name");
                jsonObj.remove("description");
                object.setParameters(jsonObj.getJSONObject("parameters"));
                if (ids.length == 1) {
                    objectRepository.save(object);
                    object.setPath(path + "." + object.getId());
                    objectRepository.save(object);
                } else {
                    object.setPath(path);
                    objectRepository.save(object);
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
                type.setPath(path);
                jsonObj.remove("name");
                jsonObj.remove("description");
                type.setParameters(jsonObj);
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
                risk.setPath(path);
                jsonObj.remove("name");
                jsonObj.remove("description");
                risk.setParameters(jsonObj);
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
        var result = new ArrayList<Object>();
        result.addAll(objects);
        result.addAll(types);
        result.addAll(risks);
        return result;
    }
}
