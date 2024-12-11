package com.example.app.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ImageController {

    @GetMapping("/images/{filename}")
    @ResponseBody
    public ResponseEntity<byte[]> getImage(@PathVariable String filename) throws IOException {
    File file = new File("C:/Users/zd2Q15/pleiades/workspace/GameCommunity/src/main/resources/static/images/" + filename);
    	 //File file = new File("/Applications/Eclipse_2023-12.app/Contents/workspace/GameCommunity2/src/main/resources/static/images/" + filename);
    	byte[] imageBytes = Files.readAllBytes(file.toPath());

        return ResponseEntity.ok()
            .header(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, must-revalidate")
            .header(HttpHeaders.PRAGMA, "no-cache")
            .header(HttpHeaders.EXPIRES, "0")
            .body(imageBytes);
    }
}

