package ru.andrew.hack.biv.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.andrew.hack.biv.service.CommonService;

import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;

@RestController
@RequestMapping("api/v1/path")
@CrossOrigin
@RequiredArgsConstructor
@Slf4j
public class CommonController {

    private final CommonService commonService;

    @GetMapping
    public ResponseEntity<String> get(
            @RequestParam(value = "path") String path
    ) {
        log.info("Got request for path: {}", path);
        Object result;
        try {
            result = commonService.get(path);
            if (result != "") {
                return ResponseEntity.ok(result.toString());
            } else {
                return ResponseEntity.notFound().build();
            }

        } catch (JSONException e) {
            return ResponseEntity.ok("");
        }
    }

    @GetMapping("all")
    public ResponseEntity<String> getAll() {
        log.info("Got request for all paths");
        var result = commonService.getAll();
        if (result != "") {
            return ResponseEntity.ok(result.toString());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<String> post(
            @RequestParam(value = "path") String path,
            @RequestBody String body
    ) {
        log.info("Got request for path: {}", path);
        Object result;
        try {
            result = commonService.post(path, body);
            if (result != "") {
                return ResponseEntity.ok(result.toString());
            } else {
                return ResponseEntity.badRequest().build();
            }
        } catch (JSONException e) {
            return ResponseEntity.badRequest().build();
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatusCode.valueOf(HTTP_BAD_REQUEST));
        }
    }

    @PutMapping
    public ResponseEntity<String> put(
            @RequestParam(value = "path") String path,
            @RequestBody String body
    ) {
        log.info("Got request for path: {}", path);
        try {
            return ResponseEntity.ok(commonService.put(path, body));
        } catch (JSONException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping
    public ResponseEntity<String> delete(
            @RequestParam(value = "path") String path
    ) {
        log.info("Got request for path: {}", path);
        return ResponseEntity.ok(commonService.delete(path));
    }

    @DeleteMapping("all")
    public ResponseEntity<String> deleteAll() {
        log.info("Got request for all paths");
        return ResponseEntity.ok(commonService.deleteAll());
    }
}
