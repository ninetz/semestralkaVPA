package com.semestralka.semestralkaVPA.controllers;

import com.google.gson.Gson;
import com.semestralka.semestralkaVPA.entities.FilesModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@RestController
public class FileController {
    @Autowired
    FileService fileService;
    private final Gson gson = new Gson();

    @RequestMapping(method = RequestMethod.GET, value = "/file")
    public String getFile(@RequestParam("id") long id) {
        Optional<FilesModel> optionalFilesModel = fileService.getFile(id);
        if (optionalFilesModel.isEmpty()) {
            throw new RuntimeException();
        }
        return gson.toJson(optionalFilesModel.get());
    }

    @RequestMapping(method = RequestMethod.POST, value = "/file/upload")
    public ResponseEntity<Object> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("filename") String filename) {
        try {
            if (fileService.uploadFile(file.getBytes(), filename)) {
                return ResponseEntity.ok().build();
            }
        } catch (IOException e) {
            ResponseEntity.badRequest().body("Failed to upload file.");
        }
        return ResponseEntity.badRequest().body("Failed to upload file.");

    }
}
